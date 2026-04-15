package com.amachi.app.core.auth.service.impl;

import com.amachi.app.core.auth.bridge.EmailBridge;
import com.amachi.app.core.auth.bridge.TenantBridge;
import com.amachi.app.core.auth.dto.invitation.CompleteRegistrationRequest;
import com.amachi.app.core.auth.dto.invitation.InvitationRequest;
import com.amachi.app.core.auth.dto.invitation.InvitationResponse;
import com.amachi.app.core.auth.dto.search.UserInvitationSearchDto;
import com.amachi.app.core.auth.entity.*;
import com.amachi.app.core.auth.enums.InvitationStatus;
import com.amachi.app.core.auth.exception.AppSecurityException;
import com.amachi.app.core.auth.repository.*;
import com.amachi.app.core.auth.service.InvitationService;
import com.amachi.app.core.common.enums.RoleContext;
import com.amachi.app.core.common.error.ErrorCode;
import com.amachi.app.core.common.repository.CommonRepository;
import com.amachi.app.core.common.service.BaseService;
import com.amachi.app.core.common.service.DomainEventPublisher;
import com.amachi.app.core.common.utils.ContextMapping;
import com.amachi.app.core.domain.entity.Person;
import com.amachi.app.core.domain.person.service.IdentityResolutionService;
import com.amachi.app.core.domain.person.service.PersonContextService;
import com.amachi.app.core.domain.repository.PersonRepository;
import com.amachi.app.core.domain.tenant.entity.Tenant;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class InvitationServiceImpl extends BaseService<UserInvitation, UserInvitationSearchDto>
        implements InvitationService {

    private final UserInvitationRepository invitationRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserAccountRepository userAccountRepository;
    private final PersonRepository personRepository;

    private final TenantBridge tenantBridge;
    private final EmailBridge emailBridge;
    private final PasswordEncoder passwordEncoder;
    private final UserTenantRoleRepository userTenantRoleRepository;
    private final DomainEventPublisher eventPublisher;

    private final IdentityResolutionService identityResolutionService;
    private final PersonContextService personContextService;
    private final ContextMapping contextMapping;

    @PersistenceContext
    private EntityManager em;

    @Value("${mailing.frontend.base-url}")
    private String frontendBaseUrl;

    @Value("${mailing.frontend.invitation-path:/complete-registration}")
    private String invitationPath;

    @Value("${mailing.invitation.token-ttl-hours:48}")
    private int tokenTtlHours;

    @Override
    protected CommonRepository<UserInvitation, Long> getRepository() {
        return invitationRepository;
    }

    @Override
    protected Specification<UserInvitation> buildSpecification(UserInvitationSearchDto searchDto) {
        return new UserInvitationSpecification(searchDto);
    }

    @Override
    protected DomainEventPublisher getEventPublisher() {
        return eventPublisher;
    }

    @Override
    protected void publishCreatedEvent(UserInvitation entity) {
        log.info("📢 Invitation created event for ID={}", entity.getId());
    }

    @Override
    protected void publishUpdatedEvent(UserInvitation entity) {
        log.info("📢 Invitation updated event for ID={}", entity.getId());
    }

    @Override
    protected void publishDeletedEvent(UserInvitation entity) {
        log.info("📢 Invitation deleted event for ID={}", entity.getId());
    }

    @Override
    @Transactional
    public InvitationResponse createInvitation(InvitationRequest request) {
        log.info("📨 Creating invitation for email='{}' role='{}' tenant='{}'",
                request.getEmail(), request.getRoleName(), request.getTenantCode());

        Tenant tenant = tenantBridge.findByCode(request.getTenantCode());
        if (Boolean.FALSE.equals(tenant.getIsActive())) {
            throw new AppSecurityException(ErrorCode.SEC_TENANT_DISABLED, "security.tenant.disabled", tenant.getCode());
        }

        Role role = roleRepository.findByName(request.getRoleName())
                .orElseThrow(() -> new AppSecurityException(ErrorCode.SEC_INVALID_OPERATION, "security.role.not_found", request.getRoleName()));

        if (invitationRepository.existsPendingInvitation(request.getEmail(), request.getTenantCode(), LocalDateTime.now())) {
            throw new AppSecurityException(ErrorCode.SEC_INVALID_OPERATION, "security.invitation.already_pending", request.getEmail());
        }

        String token = UUID.randomUUID().toString();
        LocalDateTime expiresAt = LocalDateTime.now().plusHours(tokenTtlHours);

        UserInvitation invitation = UserInvitation.builder()
                .email(request.getEmail())
                .nationalId(request.getNationalId())
                .roleContext(request.getRoleContext())
                .tenant(tenant)
                .tenantId(tenant.getCode())
                .role(role)
                .token(token)
                .expiresAt(expiresAt)
                .status(InvitationStatus.PENDING)
                .build();

        invitationRepository.save(invitation);
        
        String activationUrl = frontendBaseUrl + invitationPath + "?token=" + token;
        emailBridge.sendInvitationEmail(request.getEmail(), tenant.getName(), activationUrl, 
                request.getFirstName(), request.getLastName());

        return toResponse(invitation);
    }

    @Override
    @Transactional(readOnly = true)
    public InvitationResponse validateToken(String token) {
        return toResponse(findValidInvitation(token));
    }

    @Override
    @Transactional
    public void acceptInvitation(CompleteRegistrationRequest request) {
        log.info("🎉 Accepting invitation phase 1 — loginEmail='{}'", request.getLoginEmail());

        UserInvitation invitation = findValidInvitation(request.getToken());

        if (!invitation.getTenant().getCode().equals(request.getTenantCode())) {
            throw new AppSecurityException(ErrorCode.SEC_INVALID_OPERATION, "validation.invitation.tenant.mismatch");
        }

        if (userRepository.existsByEmail(request.getLoginEmail())) {
            throw new AppSecurityException(ErrorCode.SEC_INVALID_OPERATION, "validation.email.already_in_use", request.getLoginEmail());
        }

        Tenant tenant = invitation.getTenant();
        Role role = invitation.getRole();
        DomainContext domainContext = contextMapping.toDomain(invitation.getRoleContext());

        // 1. Resolve Global Identity
        Person personData = Person.builder()
                .firstName(request.getFirstName())
                .middleName(request.getMiddleName())
                .lastName(request.getLastName())
                .secondLastName(request.getSecondLastName())
                .phoneNumber(request.getPhoneNumber())
                .email(request.getPersonalEmail())
                .build();

        Person person = identityResolutionService.resolveOrCreateIdentity(
                invitation.getNationalId(), 
                invitation.getEmail(), 
                personData
        );

        // 2. Hardening: Validation of operational identity existence (Elite Rule)
        if (domainContext != null && personContextService.existsActiveContext(person, tenant, domainContext)) {
            log.warn("⚠️ Operational identity collision: Person {} already has active context {} in Tenant {}", 
                    person.getId(), domainContext, tenant.getCode());
            throw new AppSecurityException(ErrorCode.SEC_INVALID_OPERATION, "security.identity.collision", domainContext.name());
        }

        // 3. Create Global Security User linked to Person
        User user = User.builder()
                .email(request.getLoginEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .enabled(true)
                .accountLocked(false)
                .person(person)
                .tenantId("SYSTEM") // Global users are in SYSTEM context
                .build();
        
        user = userRepository.save(user);

        // 4. Create Context (PersonTenant + Domain Entity) - Atomic Operation
        personContextService.createContext(
                person,
                tenant,
                invitation.getRoleContext(),
                domainContext
        );

        // 5. Link User to Tenant (Operational Account)
        UserAccount userAccount = UserAccount.builder()
                .user(user)
                .tenant(tenant)
                .tenantId(tenant.getCode())
                .person(person)
                .build();
        userAccountRepository.save(userAccount);

        // 6. Assign Security Roles (UX/Navigation)
        userTenantRoleRepository.assignRolesToUserAndTenant(user.getId(), tenant.getCode(), Set.of(role.getName()));

        // 7. Close Invitation
        invitation.setStatus(InvitationStatus.ACCEPTED);
        invitation.setAcceptedAt(LocalDateTime.now());
        invitationRepository.save(invitation);

        log.info("✅ Invitation accepted successfully for User: {} in Tenant: {}", user.getEmail(), tenant.getCode());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<InvitationResponse> getInvitations(String tenantCode, String status, String role, Integer pageIndex, Integer pageSize) {
        UserInvitationSearchDto searchDto = new UserInvitationSearchDto();
        searchDto.setTenantCode(tenantCode);
        searchDto.setRoleName(role);
        if (status != null && !status.isBlank()) {
            try { searchDto.setStatus(InvitationStatus.valueOf(status.toUpperCase())); } 
            catch (IllegalArgumentException ignored) {}
        }

        Pageable pageable = PageRequest.of(pageIndex, pageSize, Sort.by(Sort.Direction.DESC, "createdDate"));
        return invitationRepository.findAll(new UserInvitationSpecification(searchDto), pageable).map(this::toResponse);
    }

    @Override
    @Transactional
    public InvitationResponse resendInvitation(Long id) {
        UserInvitation invitation = invitationRepository.findById(id)
                .orElseThrow(() -> new AppSecurityException(ErrorCode.SEC_INVALID_OPERATION, "error.resource.not.found", id.toString()));

        if (invitation.getStatus() == InvitationStatus.ACCEPTED) {
            throw new AppSecurityException(ErrorCode.SEC_INVALID_OPERATION, "security.invitation.already_accepted");
        }

        String newToken = UUID.randomUUID().toString();
        invitation.setToken(newToken);
        invitation.setExpiresAt(LocalDateTime.now().plusHours(tokenTtlHours));
        invitation.setStatus(InvitationStatus.PENDING);
        invitationRepository.save(invitation);

        String activationUrl = frontendBaseUrl + invitationPath + "?token=" + newToken;
        emailBridge.sendInvitationEmail(invitation.getEmail(), invitation.getTenant().getName(), activationUrl, 
                "Invitado", "");

        return toResponse(invitation);
    }

    private UserInvitation findValidInvitation(String token) {
        UserInvitation invitation = invitationRepository.findByToken(token)
                .orElseThrow(() -> new AppSecurityException(ErrorCode.SEC_INVALID_TOKEN, "security.invitation.not_found"));

        if (!invitation.isValid()) {
            String reason = invitation.getStatus() == InvitationStatus.ACCEPTED ? "security.invitation.already_accepted" : "security.invitation.expired";
            throw new AppSecurityException(ErrorCode.SEC_INVALID_TOKEN, reason);
        }
        return invitation;
    }

    private InvitationResponse toResponse(UserInvitation invitation) {
        return InvitationResponse.builder()
                .id(invitation.getId())
                .email(invitation.getEmail())
                .roleName(invitation.getRole().getName())
                .tenantCode(invitation.getTenant().getCode())
                .tenantName(invitation.getTenant().getName())
                .status(invitation.getStatus())
                .createdAt(invitation.getCreatedDate())
                .expiresAt(invitation.getExpiresAt())
                .roleContext(invitation.getRoleContext())
                .build();
    }
}
