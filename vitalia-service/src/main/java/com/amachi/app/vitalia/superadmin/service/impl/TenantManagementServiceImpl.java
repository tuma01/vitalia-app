package com.amachi.app.vitalia.superadmin.service.impl;


import com.amachi.app.vitalia.authentication.dto.UserRegisterRequest;
import com.amachi.app.vitalia.authentication.entity.Role;
import com.amachi.app.vitalia.authentication.entity.User;
import com.amachi.app.vitalia.authentication.entity.UserAccount;
import com.amachi.app.vitalia.authentication.repository.RoleRepository;
import com.amachi.app.vitalia.authentication.repository.TenantRepository;
import com.amachi.app.vitalia.authentication.repository.UserAccountRepository;
import com.amachi.app.vitalia.authentication.repository.UserRepository;
import com.amachi.app.vitalia.common.entity.Tenant;
import com.amachi.app.vitalia.common.enums.PersonType;
import com.amachi.app.vitalia.common.enums.RelationStatus;
import com.amachi.app.vitalia.common.enums.RoleContext;
import com.amachi.app.vitalia.common.enums.TenantType;
import com.amachi.app.vitalia.common.utils.AppConstants;
import com.amachi.app.vitalia.person.entity.Person;
import com.amachi.app.vitalia.person.entity.PersonTenant;
import com.amachi.app.vitalia.person.repository.PersonRepository;
import com.amachi.app.vitalia.person.repository.PersonTenantRepository;
import com.amachi.app.vitalia.superadmin.dto.*;
import com.amachi.app.vitalia.superadmin.entity.TenantConfig;
import com.amachi.app.vitalia.superadmin.repository.TenantConfigRepository;
import com.amachi.app.vitalia.superadmin.service.TenantManagementService;
import com.amachi.app.vitalia.person.factory.PersonFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class TenantManagementServiceImpl implements TenantManagementService {

    private final TenantRepository tenantRepository;
    private final TenantConfigRepository tenantConfigRepository;
    private final UserRepository userRepository;
    private final PersonFactory personFactory;
    private final PersonRepository personRepository;
    private final PersonTenantRepository personTenantRepository;
    private final UserAccountRepository userAccountRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public TenantResponse createTenantWithAdmin(TenantCreateRequest req) {
        // VALIDACIONES básicas
        if (tenantRepository.existsByCode(req.getCode())) {
            throw new IllegalArgumentException("tenant.code.exists");
        }
        if (userRepository.existsByEmail(req.getAdminEmail())) {
            throw new IllegalArgumentException("admin.email.exists");
        }

        // 1) Crear tenant
        Tenant tenant = Tenant.builder()
                .code(req.getCode())
                .name(req.getName())
                .type(req.getType() != null ? req.getType() : TenantType.HOSPITAL)
                .description(req.getDescription())
                .isActive(true)
                .build();
        tenant = tenantRepository.save(tenant);

        // 2) Crear TenantConfig (profesional, más campos)
        TenantConfig cfg = TenantConfig.builder()
                .tenant(tenant)
                .fallbackHeader(req.getFallbackHeader())
                .allowLocal(Optional.ofNullable(req.getAllowLocal()).orElse(true))
                .defaultDomain(req.getDefaultDomain())
                .locale("es_PE")
                .timezone("America/Lima")
                .maxUsers(100)
                .storageQuotaBytes(10L * 1024 * 1024 * 1024) // 10GB por defecto
                .requireEmailVerification(true)
                .passwordPolicyJson("{\"minLength\":8, \"requireUpper\":true}")
                .build();
        tenantConfigRepository.save(cfg);

        // 3) Crear persona TenantAdmin via PersonFactory
        UserRegisterRequest dto = UserRegisterRequest.builder()
                .nombre(req.getAdminFirstName())
                .apellidoPaterno(req.getAdminLastName())
                .personType(PersonType.valueOf(Optional.ofNullable(req.getAdminPersonType()).orElse("ADMIN")))
                .tenantCode(tenant.getCode())
                .email(req.getAdminEmail())
                .build();

        Person person = personFactory.create(dto.getPersonType(), dto);
        person = personRepository.save(person); // <-- Persistir Person

        // 4) Crear PersonTenant (relacion Person <-> Tenant)
        PersonTenant pt = PersonTenant.builder()
                .person(person)
                .tenant(tenant)
                .roleContext(RoleContext.TENANT_ADMIN)
                .relationStatus(RelationStatus.ACTIVE)
                .dateRegistered(LocalDateTime.now())
                .build();
        personTenantRepository.save(pt); // <-- Persistir PersonTenant

        // 5) Crear User (credenciales)
        String pwd = req.getAdminPassword();
        if (pwd == null || pwd.isBlank()) {
            pwd = generateRandomPassword();
        }
        User user = User.builder()
                .email(req.getAdminEmail())
                .password(passwordEncoder.encode(pwd))
                .enabled(true)
                .accountLocked(false)
                .build();
        user = userRepository.save(user);

        // 6) Crear UserAccount y asignar rol Tenant Admin
        Set<Role> roles = new HashSet<>();
        roleRepository.findByName(AppConstants.Roles.ROLE_ADMIN).ifPresent(roles::add);

        UserAccount ua = UserAccount.builder()
                .user(user)
                .personId(person.getId())
                .tenant(tenant)
                .roles(roles)
                .build();
        userAccountRepository.save(ua); // <-- Persistir UserAccount

        // NOTA: Envío de email de onboarding puede implementarse aquí

        return TenantResponse.builder()
                .tenantId(tenant.getId())
                .code(tenant.getCode())
                .name(tenant.getName())
                .type(tenant.getType())
                .description(tenant.getDescription())
                .isActive(tenant.getIsActive())
                .fallbackHeader(cfg.getFallbackHeader())
                .allowLocal(cfg.getAllowLocal())
                .defaultDomain(cfg.getDefaultDomain())
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Override
    public Page<TenantResponse> listTenants(Pageable pageable) {
        Page<Tenant> page = tenantRepository.findAll(pageable);
        List<TenantResponse> content = page.stream().map(t -> TenantResponse.builder()
                .tenantId(t.getId())
                .code(t.getCode())
                .name(t.getName())
                .type(t.getType())
                .description(t.getDescription())
                .isActive(t.getIsActive())
                .createdAt(t.getCreatedDate() == null ? LocalDateTime.now() : t.getCreatedDate())
                .build()).toList();
        return new PageImpl<>(content, pageable, page.getTotalElements());
    }

    @Override
    public TenantResponse getTenant(Long id) {
        Tenant t = tenantRepository.findById(id).orElseThrow(() -> new NoSuchElementException("tenant.notfound"));
        TenantConfig cfg = tenantConfigRepository.findByTenant_Id(id).orElse(null);
        return TenantResponse.builder()
                .tenantId(t.getId())
                .code(t.getCode())
                .name(t.getName())
                .type(t.getType())
                .description(t.getDescription())
                .isActive(t.getIsActive())
                .fallbackHeader(cfg == null ? null : cfg.getFallbackHeader())
                .allowLocal(cfg == null ? null : cfg.getAllowLocal())
                .defaultDomain(cfg == null ? null : cfg.getDefaultDomain())
                .createdAt(t.getCreatedDate() == null ? LocalDateTime.now() : t.getCreatedDate())
                .build();
    }

    @Override
    @Transactional
    public TenantResponse updateTenant(Long id, com.amachi.app.vitalia.superadmin.dto.TenantUpdateRequest update) {
        Tenant t = tenantRepository.findById(id).orElseThrow(() -> new NoSuchElementException("tenant.notfound"));
        if (update.getName() != null) t.setName(update.getName());
        if (update.getDescription() != null) t.setDescription(update.getDescription());
        if (update.getType() != null) t.setType(update.getType());
        tenantRepository.save(t);

        TenantConfig cfg = tenantConfigRepository.findByTenant_Id(id).orElseGet(() -> {
            TenantConfig nc = TenantConfig.builder().tenant(t).build();
            return tenantConfigRepository.save(nc);
        });

        if (update.getFallbackHeader() != null) cfg.setFallbackHeader(update.getFallbackHeader());
        if (update.getAllowLocal() != null) cfg.setAllowLocal(update.getAllowLocal());
        if (update.getDefaultDomain() != null) cfg.setDefaultDomain(update.getDefaultDomain());
        tenantConfigRepository.save(cfg);

        return getTenant(id);
    }

    @Override
    @Transactional
    public void enableTenant(Long id) {
        Tenant t = tenantRepository.findById(id).orElseThrow(() -> new NoSuchElementException("tenant.notfound"));
        t.setIsActive(true);
        tenantRepository.save(t);
    }

    @Override
    @Transactional
    public void disableTenant(Long id) {
        Tenant t = tenantRepository.findById(id).orElseThrow(() -> new NoSuchElementException("tenant.notfound"));
        t.setIsActive(false);
        tenantRepository.save(t);
    }

    @Override
    @Transactional
    public void resetAdminPassword(Long tenantId, String newPassword) {
        // busca el UserAccount con rol ADMIN para ese tenant (simple heuristic)
        List<UserAccount> accounts = userAccountRepository.findByTenantId(tenantId);
        if (accounts.isEmpty()) {
            throw new IllegalStateException("No se encontró ningún administrador para el tenant ID " + tenantId);
        }
        for (UserAccount ua : accounts) {
            if (ua.getRoles().stream().anyMatch(r -> r.getName().equals(AppConstants.Roles.ROLE_ADMIN))) {
                Optional<User> ou = userRepository.findById(ua.getUser().getId());
                if (ou.isPresent()) {
                    User user = ou.get();
                    user.setPassword(passwordEncoder.encode(newPassword));
                    userRepository.save(user);
                    // TODO optional: send email
                    return;
                }
            }
        }
        throw new NoSuchElementException("tenant.admin.notfound");
    }

    @Override
    @Transactional
    public void softDeleteTenant(Long id) {
        Tenant t = tenantRepository.findById(id).orElseThrow(() -> new NoSuchElementException("tenant.notfound"));
        // Soft delete: desactivar, y opcionalmente marcar en config
        t.setIsActive(false);
        tenantRepository.save(t);
        // NOTA: No realiza borrado en cascada por seguridad
    }

    private String generateRandomPassword() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 12);
    }
}

