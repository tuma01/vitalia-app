package com.amachi.app.core.auth.service.impl;

import com.amachi.app.core.auth.bridge.EmailBridge;
import com.amachi.app.core.auth.bridge.PersonIdentityBridge;
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
import com.amachi.app.core.auth.service.UserTenantRoleService;
import com.amachi.app.core.auth.specification.UserInvitationSpecification;
import com.amachi.app.core.common.error.ErrorCode;
import com.amachi.app.core.common.event.DomainEventPublisher;
import com.amachi.app.core.common.repository.CommonRepository;
import com.amachi.app.core.common.service.BaseService;
import com.amachi.app.core.domain.entity.Person;
import com.amachi.app.core.domain.repository.PersonRepository;
import com.amachi.app.core.domain.tenant.entity.Tenant;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class InvitationServiceImpl extends BaseService<UserInvitation, UserInvitationSearchDto>
        implements InvitationService {

    private final UserInvitationRepository invitationRepository;
    private final UserRepository           userRepository;
    private final RoleRepository           roleRepository;
    private final UserAccountRepository    userAccountRepository;
    private final PersonRepository         personRepository;

    private final TenantBridge           tenantBridge;
    private final EmailBridge            emailBridge;
    private final PasswordEncoder        passwordEncoder;
    private final PersonIdentityBridge   personIdentityBridge;
    private final UserTenantRoleService  userTenantRoleService;
    private final DomainEventPublisher   eventPublisher;

    @PersistenceContext
    private final EntityManager em;

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
            throw new AppSecurityException(ErrorCode.SEC_TENANT_DISABLED,
                    "security.tenant.disabled", tenant.getCode());
        }

        Role role = roleRepository.findByName(request.getRoleName())
                .orElseThrow(() -> new AppSecurityException(ErrorCode.SEC_INVALID_OPERATION,
                        "security.role.not_found", request.getRoleName()));

        if (invitationRepository.existsPendingInvitation(request.getEmail(), request.getTenantCode(), LocalDateTime.now())) {
            throw new AppSecurityException(ErrorCode.SEC_INVALID_OPERATION,
                    "security.invitation.already_pending", request.getEmail());
        }

        Map<String, Object> context = new HashMap<>();
        context.put("tenant", tenant);
        context.put("firstName", request.getFirstName());
        context.put("lastName", request.getLastName());

        Person person = personIdentityBridge.createPerson(request.getPersonType(), context);
        person.setEmail(request.getEmail());
        person.setPersonType(request.getPersonType());
        person.setCreatedBy("INVITATION_FLOW");
        person.setCreatedDate(LocalDateTime.now());

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode("changeMe"))
                .enabled(false)
                .accountLocked(false)
                .person(person)
                .tenantId(tenant.getCode())
                .build();

        personIdentityBridge.linkUser(person, user);
        user = userRepository.save(user);

        String token = UUID.randomUUID().toString();
        LocalDateTime expiresAt = LocalDateTime.now().plusHours(tokenTtlHours);

        UserInvitation invitation = UserInvitation.builder()
                .user(user)
                .tenant(tenant)
                .tenantId(tenant.getCode())
                .role(role)
                .token(token)
                .expiresAt(expiresAt)
                .status(InvitationStatus.PENDING)
                .build();

        invitationRepository.save(invitation);
        
        String activationUrl = frontendBaseUrl + invitationPath + "?token=" + token;
        emailBridge.sendInvitationEmail(request.getEmail(), tenant.getName(), activationUrl, request.getFirstName(), request.getLastName());

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
        log.info("🎉 Accepting invitation — loginEmail='{}'", request.getLoginEmail());

        UserInvitation invitation = findValidInvitation(request.getToken());

        if (!invitation.getTenant().getCode().equals(request.getTenantCode())) {
            throw new AppSecurityException(ErrorCode.SEC_INVALID_OPERATION, "validation.invitation.tenant.mismatch");
        }

        if (!invitation.getUser().getEmail().equals(request.getLoginEmail()) && userRepository.existsByEmail(request.getLoginEmail())) {
            throw new AppSecurityException(ErrorCode.SEC_INVALID_OPERATION, "validation.email.already_in_use", request.getLoginEmail());
        }

        Tenant tenant = invitation.getTenant();
        Role   role   = invitation.getRole();
        User   user   = invitation.getUser();
        Person person = user.getPerson();

        person.setFirstName(request.getFirstName());
        person.setMiddleName(request.getMiddleName());
        person.setLastName(request.getLastName());
        person.setSecondLastName(request.getSecondLastName());
        person.setPhoneNumber(request.getPhoneNumber());
        person.setEmail(request.getPersonalEmail());
        person.setLastModifiedBy(user.getEmail());
        person.setLastModifiedDate(LocalDateTime.now());
        personRepository.save(person);

        user.setEmail(request.getLoginEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEnabled(true);
        userRepository.save(user);

        UserAccount userAccount = UserAccount.builder()
                .user(user)
                .tenant(tenant)
                .tenantId(tenant.getCode())
                .person(person)
                .build();
        userAccountRepository.save(userAccount);

        userTenantRoleService.assignRolesToUserAndTenant(user, tenant, Set.of(role));

        invitation.setStatus(InvitationStatus.ACCEPTED);
        invitation.setAcceptedAt(LocalDateTime.now());
        invitationRepository.save(invitation);
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
        emailBridge.sendInvitationEmail(invitation.getUser().getEmail(), invitation.getTenant().getName(), activationUrl, 
                invitation.getUser().getPerson().getFirstName(), invitation.getUser().getPerson().getLastName());

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
                .firstName(person.getFirstName())
                .lastName(person.getLastName())
                .build();
    }
}
