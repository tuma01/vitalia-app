package com.amachi.app.core.auth.service.impl;

import com.amachi.app.core.auth.bridge.PersonIdentityBridge;
import com.amachi.app.core.auth.bridge.EmailBridge;
import com.amachi.app.core.auth.bridge.TenantBridge;
import com.amachi.app.core.auth.dto.invitation.CompleteRegistrationRequest;
import com.amachi.app.core.auth.dto.invitation.InvitationRequest;
import com.amachi.app.core.auth.dto.invitation.InvitationResponse;
import com.amachi.app.core.auth.entity.*;
import com.amachi.app.core.auth.enums.InvitationStatus;
import com.amachi.app.core.auth.exception.AppSecurityException;
import com.amachi.app.core.auth.repository.*;
import com.amachi.app.core.auth.service.InvitationService;
import com.amachi.app.core.auth.service.UserTenantRoleService;
import com.amachi.app.core.common.enums.PersonType;
import java.util.HashMap;
import java.util.Map;
import com.amachi.app.core.common.error.ErrorCode;
import com.amachi.app.core.domain.entity.Person;
import com.amachi.app.core.domain.repository.PersonRepository;
import com.amachi.app.core.domain.tenant.entity.Tenant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

/**
 * Implementation of {@link InvitationService}.
 *
 * <p>Orchestrates the two-step user onboarding process:
 * <ol>
 *     <li>Admin sends invitation → token generated + email dispatched.</li>
 *     <li>User accepts invitation → accounts created + auto-activated.</li>
 * </ol>
 *
 * <p>All write operations are wrapped in {@link Transactional} to guarantee
 * atomicity. Email dispatch happens outside the transaction boundary
 * (async via {@code @Async} in {@code EmailServiceImpl}) to avoid
 * rollback caused by SMTP failures.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class InvitationServiceImpl implements InvitationService {

    // ── Repository dependencies ─────────────────────────────────────────
    private final UserInvitationRepository invitationRepository;
    private final UserRepository           userRepository;
    private final RoleRepository           roleRepository;
    private final UserAccountRepository    userAccountRepository;
    private final PersonRepository         personRepository;

    // ── Service / bridge dependencies ──────────────────────────────────
    private final TenantBridge           tenantBridge;
    private final EmailBridge            emailBridge;
    private final PasswordEncoder        passwordEncoder;
    private final PersonIdentityBridge personIdentityBridge;
    private final UserTenantRoleService  userTenantRoleService;

    // ── Configuration ───────────────────────────────────────────────────
    /** Base URL of the frontend application, e.g. http://localhost:4200 */
    @Value("${mailing.frontend.base-url}")
    private String frontendBaseUrl;

    /** Path of the self-onboarding page, e.g. /complete-registration */
    @Value("${mailing.frontend.invitation-path:/complete-registration}")
    private String invitationPath;

    /** Invitation token TTL in hours. Defaults to 48 hours. */
    @Value("${mailing.invitation.token-ttl-hours:48}")
    private int tokenTtlHours;

    // ────────────────────────────────────────────────────────────────────
    // Step 1: Admin creates invitation
    // ────────────────────────────────────────────────────────────────────

    /**
     * {@inheritDoc}
     *
     * <p>Validates the tenant, resolves the role, checks for duplicate pending invitations,
     * then persists the {@code UserInvitation} and dispatches the email asynchronously.
     */
    @Override
    @Transactional
    public InvitationResponse createInvitation(InvitationRequest request) {
        log.info("📨 Creating invitation for email='{}' role='{}' tenant='{}'",
                request.getEmail(), request.getRoleName(), request.getTenantCode());

        // 1. Resolve and validate tenant
        Tenant tenant = tenantBridge.findByCode(request.getTenantCode());
        if (Boolean.FALSE.equals(tenant.getIsActive())) {
            throw new AppSecurityException(ErrorCode.SEC_TENANT_DISABLED,
                    "security.tenant.disabled", tenant.getCode());
        }

        // 2. Resolve role
        Role role = roleRepository.findByName(request.getRoleName())
                .orElseThrow(() -> new AppSecurityException(ErrorCode.SEC_INVALID_OPERATION,
                        "security.role.not_found", request.getRoleName()));

        // 3. Guard: no duplicate pending invitation for the same email + tenant
        boolean alreadyPending = invitationRepository.existsPendingInvitation(
                request.getEmail(), request.getTenantCode(), LocalDateTime.now());
        if (alreadyPending) {
            throw new AppSecurityException(ErrorCode.SEC_INVALID_OPERATION,
                    "security.invitation.already_pending", request.getEmail());
        }

        // 3.1 Create Identity early (Hybrid Model)
        // This ensures the correct PERSON_TYPE discriminator is persisted in DMN_PERSON
        Map<String, Object> context = new HashMap<>();
        context.put("tenant", tenant);
        context.put("nombre", request.getNombre());
        context.put("apellidoPaterno", request.getApellidoPaterno());

        Person person = personIdentityBridge.createPerson(request.getPersonType(), context);
        
        person.setEmail(request.getEmail());
        person.setPersonType(request.getPersonType());
        person.setCreatedBy("INVITATION_FLOW");
        person.setCreatedDate(LocalDateTime.now());

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode("changeMe")) // Temporal password
                .enabled(false)                             // Inactive until accepted
                .accountLocked(false)
                .person(person)
                .build();

        // 3.2 Bidirectional link (essential for subclasses like TenantAdmin with mandatory USER_ID)
        personIdentityBridge.linkUser(person, user);

        // 3.3 Atomic save via CascadeType.ALL (User -> Person)
        user = userRepository.save(user);

        log.debug("🔑 Identity graph (User/Person) created atomícamente for invitation: email='{}'", 
                user.getEmail());

        // 4. Generate one-time secure UUID token
        String token = UUID.randomUUID().toString();
        LocalDateTime expiresAt = LocalDateTime.now().plusHours(tokenTtlHours);

        // 5. Persist the invitation
        UserInvitation invitation = UserInvitation.builder()
                .user(user) // Linked to the pre-created user
                .tenant(tenant)
                .role(role)
                .token(token)
                .expiresAt(expiresAt)
                .status(InvitationStatus.PENDING)
                .build();
        invitationRepository.save(invitation);
        log.debug("💾 Invitation persisted with id={} expiresAt={}", invitation.getId(), expiresAt);

        // 6. Build activation URL and send email (async — won't rollback on SMTP failure)
        String activationUrl = frontendBaseUrl + invitationPath + "?token=" + token;
        emailBridge.sendInvitationEmail(request.getEmail(), tenant.getName(), activationUrl, request.getNombre(), request.getApellidoPaterno());
        log.info("✅ Invitation email dispatched to '{}'", request.getEmail());

        return toResponse(invitation);
    }

    // ────────────────────────────────────────────────────────────────────
    // Token validation (public endpoint — no auth required)
    // ────────────────────────────────────────────────────────────────────

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public InvitationResponse validateToken(String token) {
        log.debug("🔍 Validating invitation token");

        UserInvitation invitation = findValidInvitation(token);
        return toResponse(invitation);
    }

    // ────────────────────────────────────────────────────────────────────
    // Step 2: User completes self-onboarding
    // ────────────────────────────────────────────────────────────────────

    /**
     * {@inheritDoc}
     *
     * <p>Creates Person + User + UserAccount + UserTenantRole atomically,
     * then marks the invitation as ACCEPTED. The user is activated immediately.
     */
    @Override
    @Transactional
    public void acceptInvitation(CompleteRegistrationRequest request) {
        log.info("🎉 Accepting invitation — loginEmail='{}'", request.getLoginEmail());

        // 1. Validate token
        UserInvitation invitation = findValidInvitation(request.getToken());

        // 2. Security Cross-Check: Ensure the request tenantCode matches the invitation's tenant
        if (!invitation.getTenant().getCode().equals(request.getTenantCode())) {
            log.error("🛑 Tenant mismatch: expected={}, received={}", 
                    invitation.getTenant().getCode(), request.getTenantCode());
            throw new AppSecurityException(ErrorCode.SEC_INVALID_OPERATION, 
                    "validation.invitation.tenant.mismatch");
        }

        // 3. Guard: login email must not already exist (excluding the current user)
        if (!invitation.getUser().getEmail().equals(request.getLoginEmail()) && userRepository.existsByEmail(request.getLoginEmail())) {
            throw new AppSecurityException(ErrorCode.SEC_INVALID_OPERATION,
                    "validation.email.already_in_use", request.getLoginEmail());
        }

        Tenant tenant = invitation.getTenant();
        Role   role   = invitation.getRole();

        // 3. Update existing Identity
        User   user   = invitation.getUser();
        Person person = user.getPerson();

        // Security Cross-Check: Ensure the Person type matches the invitation's intent
        if (!personIdentityBridge.validatePersonType(person, person.getPersonType())) {
            log.error("🛑 Security Violation: Person type inconsistency for invitation [{}]. Found [{}]",
                    invitation.getToken(), person.getClass().getSimpleName());
            throw new AppSecurityException(ErrorCode.SEC_INVALID_OPERATION,
                    "validation.invitation.person_type.mismatch");
        }

        person.setNombre(request.getNombre());
        person.setSegundoNombre(request.getSegundoNombre());
        person.setApellidoPaterno(request.getApellidoPaterno());
        person.setApellidoMaterno(request.getApellidoMaterno());
        person.setTelefono(request.getTelefono());
        person.setEmail(request.getPersonalEmail());
        person.setLastModifiedBy(user.getEmail());
        person.setLastModifiedDate(LocalDateTime.now());
        personRepository.save(person);

        // 4. Activate User
        user.setEmail(request.getLoginEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEnabled(true);
        userRepository.save(user);

        // 5. Create UserAccount (links user ↔ tenant)
        UserAccount userAccount = UserAccount.builder()
                .user(user)
                .tenant(tenant)
                .person(person)
                .build();
        userAccountRepository.save(userAccount);
        log.debug("🏥 UserAccount created id={}", userAccount.getId());

        // 6. Assign pre-configured role (UserTenantRole)
        userTenantRoleService.assignRolesToUserAndTenant(user, tenant, Set.of(role));
        log.debug("🛡️ Role '{}' assigned to user '{}' in tenant '{}'",
                role.getName(), user.getEmail(), tenant.getCode());

        // 7. Mark invitation as ACCEPTED
        invitation.setStatus(InvitationStatus.ACCEPTED);
        invitation.setAcceptedAt(LocalDateTime.now());
        invitationRepository.save(invitation);

        log.info("✅ Self-onboarding complete for '{}' in tenant '{}'",
                user.getEmail(), tenant.getCode());
    }

    // --------------------------------------------------------------------
    // Step 3 (Admin Management): Paginate List & Resend
    // --------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Page<InvitationResponse> getInvitations(String tenantCode, String status, String role, Integer pageIndex, Integer pageSize) {
        log.debug("📊 Fetching Page {} (Size: {}) for tenant: {}, status: {}, role: {}", 
                pageIndex, pageSize, tenantCode, status, role);
        
        Pageable pageable = PageRequest.of(pageIndex, pageSize, Sort.by(Sort.Direction.DESC, "createdDate"));

        // 🛠️ Implementation Strategy:
        // We use the existing repository methods or a dynamic query.
        // For simplicity and since these are few fields, we can use conditional repository calls 
        // or a Specification. Here we'll start with a clean approach.
        
        InvitationStatus statusEnum = null;
        if (status != null && !status.isBlank()) {
            try {
                statusEnum = InvitationStatus.valueOf(status.toUpperCase());
            } catch (IllegalArgumentException e) {
                log.warn("⚠️ Invalid status filter received: {}", status);
            }
        }

        Page<UserInvitation> invitationsPage;
        
        if (statusEnum != null && (role != null && !role.isBlank())) {
            invitationsPage = invitationRepository.findAllByTenantCodeAndStatusAndRoleName(tenantCode, statusEnum, role, pageable);
        } else if (statusEnum != null) {
            invitationsPage = invitationRepository.findAllByTenantCodeAndStatus(tenantCode, statusEnum, pageable);
        } else if (role != null && !role.isBlank()) {
            invitationsPage = invitationRepository.findAllByTenantCodeAndRoleName(tenantCode, role, pageable);
        } else {
            invitationsPage = invitationRepository.findAllByTenantCode(tenantCode, pageable);
        }

        return invitationsPage.map(this::toResponse);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public InvitationResponse resendInvitation(Long id) {
        log.info("🔄 Attempting to resend invitation id='{}'", id);

        // 1. Fetch Entity
        UserInvitation invitation = invitationRepository.findById(id)
                .orElseThrow(() -> new AppSecurityException(ErrorCode.SEC_INVALID_OPERATION,
                        "error.resource.not.found", id.toString()));

        // 2. Reject if already accepted
        if (invitation.getStatus() == InvitationStatus.ACCEPTED) {
            throw new AppSecurityException(ErrorCode.SEC_INVALID_OPERATION,
                    "security.invitation.already_accepted");
        }

        // 3. Mutate internal state (Forge new Token & push TTL bounds)
        String newToken = UUID.randomUUID().toString();
        LocalDateTime newExpiresAt = LocalDateTime.now().plusHours(tokenTtlHours);

        invitation.setToken(newToken);
        invitation.setExpiresAt(newExpiresAt);
        invitation.setStatus(InvitationStatus.PENDING); // Explicitly restore if it was EXPIRED

        // 4. Persistence Commit
        invitationRepository.save(invitation);
        log.debug("💾 Invitation id={} successfully reset tracking params TTL={}", id, newExpiresAt);

        // 5. Asynchronous Gateway Bridge Signal
        String activationUrl = frontendBaseUrl + invitationPath + "?token=" + newToken;
        emailBridge.sendInvitationEmail(
                invitation.getUser().getEmail(), 
                invitation.getTenant().getName(), 
                activationUrl,
                invitation.getUser().getPerson().getNombre(),
                invitation.getUser().getPerson().getApellidoPaterno()
        );
        log.info("✅ Resent email physically dispatched to SMTP for '{}'", invitation.getUser().getEmail());

        return toResponse(invitation);
    }

    // --------------------------------------------------------------------
    // Private helpers
    // --------------------------------------------------------------------

    /**
     * Retrieves and validates a {@code UserInvitation} by token.
     * Throws {@link AppSecurityException} if the token is not found, expired, or already used.
     */
    private UserInvitation findValidInvitation(String token) {
        UserInvitation invitation = invitationRepository.findByToken(token)
                .orElseThrow(() -> new AppSecurityException(
                        ErrorCode.SEC_INVALID_TOKEN, "security.invitation.not_found"));

        if (!invitation.isValid()) {
            String reason = invitation.getStatus() == InvitationStatus.ACCEPTED
                    ? "security.invitation.already_accepted"
                    : "security.invitation.expired";
            throw new AppSecurityException(ErrorCode.SEC_INVALID_TOKEN, reason);
        }

        return invitation;
    }

    /**
     * Maps a {@code UserInvitation} entity to the safe {@link InvitationResponse} DTO.
     * The token is intentionally NOT included in the response.
     */
    private InvitationResponse toResponse(UserInvitation invitation) {
        Person person = invitation.getUser().getPerson();
        return InvitationResponse.builder()
                .id(invitation.getId())
                .email(invitation.getUser().getEmail())
                .roleName(invitation.getRole().getName())
                .tenantCode(invitation.getTenant().getCode())
                .tenantName(invitation.getTenant().getName())
                .status(invitation.getStatus())
                .createdAt(invitation.getCreatedDate())
                .expiresAt(invitation.getExpiresAt())
                .personType(person.getPersonType())
                .nombre(person.getNombre())
                .apellidoPaterno(person.getApellidoPaterno())
                .build();
    }
}
