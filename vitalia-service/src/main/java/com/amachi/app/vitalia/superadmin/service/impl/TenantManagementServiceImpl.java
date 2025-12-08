package com.amachi.app.vitalia.superadmin.service.impl;


import com.amachi.app.vitalia.authentication.dto.UserRegisterRequest;
import com.amachi.app.vitalia.authentication.entity.Role;
import com.amachi.app.vitalia.authentication.entity.User;
import com.amachi.app.vitalia.authentication.entity.UserAccount;
import com.amachi.app.vitalia.authentication.entity.UserTenantRole;
import com.amachi.app.vitalia.authentication.exception.AppSecurityException;
import com.amachi.app.vitalia.authentication.repository.*;
import com.amachi.app.vitalia.common.entity.Tenant;
import com.amachi.app.vitalia.common.enums.PersonType;
import com.amachi.app.vitalia.common.enums.RelationStatus;
import com.amachi.app.vitalia.common.enums.RoleContext;
import com.amachi.app.vitalia.common.enums.TenantType;
import com.amachi.app.vitalia.common.error.ErrorCode;
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
    private final UserTenantRoleRepository userTenantRoleRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public TenantResponse createTenantWithAdmin(TenantCreateRequest req) {

        // ==========================
        // 0) Validaciones básicas
        // ==========================
        if (tenantRepository.existsByCode(req.getCode())) {
            throw new AppSecurityException(
                    ErrorCode.SEC_INVALID_OPERATION,
                    "tenant.code.duplicate",
                    req.getCode()
            );
        }

        if (userRepository.existsByEmail(req.getAdminEmail())) {
            throw new IllegalArgumentException("admin.email.exists");
        }

        // ==========================
        // 1) Crear Tenant
        // ==========================
        Tenant tenant = Tenant.builder()
                .code(req.getCode())
                .name(req.getName())
                .type(Optional.ofNullable(req.getType()).orElse(TenantType.HOSPITAL))
                .description(req.getDescription())
                .isActive(true)
                .build();
        tenant = tenantRepository.save(tenant);

        // ==========================
        // 2) Crear TenantConfig
        // ==========================
        TenantConfig cfg = TenantConfig.builder()
                .tenant(tenant)
                .fallbackHeader(req.getFallbackHeader())
                .allowLocal(Optional.ofNullable(req.getAllowLocal()).orElse(true))
                .defaultDomain(req.getDefaultDomain())
                .locale("es_PE")
                .timezone("America/Lima")
                .maxUsers(100)
                .storageQuotaBytes(10L * 1024 * 1024 * 1024)
                .requireEmailVerification(true)
                .passwordPolicyJson("{\"minLength\":8, \"requireUpper\":true}")
                .build();
        tenantConfigRepository.save(cfg);

        // ==========================
        // 3) Crear Persona para el Admin
        // ==========================
        UserRegisterRequest dto = UserRegisterRequest.builder()
                .nombre(req.getAdminFirstName())
                .apellidoPaterno(req.getAdminLastName())
                .personType(PersonType.valueOf(
                        Optional.ofNullable(req.getAdminPersonType()).orElse("TENANT_ADMIN")
                ))
                .tenantCode(tenant.getCode())
                .email(req.getAdminEmail())
                .build();

        Person person = personFactory.create(dto.getPersonType(), dto);
        person = personRepository.save(person);

        // ==========================
        // 4) Crear PersonTenant
        // ==========================
        PersonTenant pt = PersonTenant.builder()
                .person(person)
                .tenant(tenant)
                .roleContext(RoleContext.TENANT_ADMIN)
                .relationStatus(RelationStatus.ACTIVE)
                .dateRegistered(LocalDateTime.now())
                .build();
        personTenantRepository.save(pt);

        // ==========================
        // # 5) Crear User (credenciales)
        // ==========================
        String pwd = Optional.ofNullable(req.getAdminPassword())
        .filter(s -> !s.isBlank())
        .orElse(generateRandomPassword());

        User user = User.builder()
                .email(req.getAdminEmail())
                .password(passwordEncoder.encode(pwd))
                .enabled(true)
                .accountLocked(false)
                .build();
        user = userRepository.save(user);

        // ==========================
        // 6) Crear UserAccount (sin roles)
        // ==========================
        UserAccount ua = UserAccount.builder()
                .user(user)
                .personId(person.getId())
                .tenant(tenant)
                .build();

        ua = userAccountRepository.save(ua);

        // ==========================
        // 7) Asignar Role TENANT ADMIN via UserTenantRole
        // ==========================
        Role adminRole = roleRepository.findByName(AppConstants.Roles.ROLE_ADMIN)
                .orElseThrow(() -> new RuntimeException("Rol ADMIN no encontrado en la BD"));

        UserTenantRole utr = UserTenantRole.builder()
                .user(user)
                .tenant(tenant)
                .role(adminRole)
                .active(true)
                .build();

        userTenantRoleRepository.save(utr);

        // ==========================
        // 8) Construir respuesta
        // ==========================
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


    // =====================================================================
    // LISTAR TENANTS
    // =====================================================================
    @Override
    public Page<TenantResponse> listTenants(Pageable pageable) {
        Page<Tenant> page = tenantRepository.findAll(pageable);

        List<TenantResponse> content = page.stream()
                .map(t -> TenantResponse.builder()
                        .tenantId(t.getId())
                        .code(t.getCode())
                        .name(t.getName())
                        .type(t.getType())
                        .description(t.getDescription())
                        .isActive(t.getIsActive())
                        .createdAt(
                                Optional.ofNullable(t.getCreatedDate())
                                        .orElse(LocalDateTime.now())
                        )
                        .build())
                .toList();

        return new PageImpl<>(content, pageable, page.getTotalElements());
    }


    // =====================================================================
    // OBTENER TENANT
    // =====================================================================
    @Override
    public TenantResponse getTenant(Long id) {
        Tenant t = tenantRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("tenant.notfound"));

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
                .createdAt(
                        Optional.ofNullable(t.getCreatedDate())
                                .orElse(LocalDateTime.now())
                )
                .build();
    }


    // =====================================================================
    // UPDATE TENANT
    // =====================================================================
    @Override
    @Transactional
    public TenantResponse updateTenant(Long id, TenantUpdateRequest update) {

        Tenant t = tenantRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("tenant.notfound"));

        if (update.getName() != null) t.setName(update.getName());
        if (update.getDescription() != null) t.setDescription(update.getDescription());
        if (update.getType() != null) t.setType(update.getType());

        tenantRepository.save(t);

        TenantConfig cfg = tenantConfigRepository.findByTenant_Id(id)
                .orElseGet(() -> tenantConfigRepository.save(
                        TenantConfig.builder().tenant(t).build()
                ));

        if (update.getFallbackHeader() != null) cfg.setFallbackHeader(update.getFallbackHeader());
        if (update.getAllowLocal() != null) cfg.setAllowLocal(update.getAllowLocal());
        if (update.getDefaultDomain() != null) cfg.setDefaultDomain(update.getDefaultDomain());

        tenantConfigRepository.save(cfg);

        return getTenant(id);
    }


    // =====================================================================
    // ACTIVAR / DESACTIVAR TENANT
    // =====================================================================
    @Override
    @Transactional
    public void enableTenant(Long id) {
        Tenant t = tenantRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("tenant.notfound"));
        t.setIsActive(true);
        tenantRepository.save(t);
    }

    @Override
    @Transactional
    public void disableTenant(Long id) {
        Tenant t = tenantRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("tenant.notfound"));
        t.setIsActive(false);
        tenantRepository.save(t);
    }


    // =====================================================================
    // RESET PASSWORD DE ADMIN DEL TENANT
    // =====================================================================
    @Override
    @Transactional
    public void resetAdminPassword(Long tenantId, String newPassword) {

        // Buscar el rol administrador
        Role adminRole = roleRepository.findByName(AppConstants.Roles.ROLE_ADMIN)
                .orElseThrow(() -> new RuntimeException("Rol ADMIN no encontrado"));

        // Buscar en UserTenantRole
        UserTenantRole utr = userTenantRoleRepository
                .findFirstByTenantIdAndRoleId(tenantId, adminRole.getId())
                .orElseThrow(() ->
                        new NoSuchElementException("tenant.admin.notfound")
                );

        User user = utr.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }


    @Override
    @Transactional
    public void softDeleteTenant(Long id) {
        Tenant t = tenantRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("tenant.notfound"));
        t.setIsActive(false);
        tenantRepository.save(t);
    }

    private String generateRandomPassword() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 12);
    }
}

