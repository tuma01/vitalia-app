package com.amachi.app.core.management.config.bootstrap;

import com.amachi.app.core.auth.entity.Role;
import com.amachi.app.core.auth.entity.User;
import com.amachi.app.core.auth.entity.UserAccount;
import com.amachi.app.core.auth.repository.RoleRepository;
import com.amachi.app.core.auth.repository.UserRepository;
import com.amachi.app.core.auth.service.UserTenantRoleService;
import com.amachi.app.core.common.enums.*;
import com.amachi.app.core.common.exception.ResourceNotFoundException;
import com.amachi.app.core.common.utils.AppConstants;
import com.amachi.app.core.domain.config.AppBootstrapProperties;
import com.amachi.app.core.domain.entity.Person;
import com.amachi.app.core.domain.person.service.IdentityResolutionService;
import com.amachi.app.core.domain.person.service.PersonContextService;
import com.amachi.app.core.domain.tenant.dto.TenantDto;
import com.amachi.app.core.domain.tenant.entity.Tenant;
import com.amachi.app.core.domain.tenant.repository.TenantRepository;
import com.amachi.app.core.domain.theme.entity.Theme;
import com.amachi.app.core.geography.address.dto.AddressDto;
import com.amachi.app.core.management.superadmin.entity.SuperAdmin;
import com.amachi.app.core.management.superadmin.repository.SuperAdminRepository;
import com.amachi.app.core.management.tenant.service.impl.TenantDomainServiceImpl;
import com.amachi.app.core.management.tenantadmin.entity.TenantAdmin;
import com.amachi.app.core.management.tenantadmin.repository.TenantAdminRepository;
import com.amachi.app.core.management.theme.repository.ThemeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

/**
 * BootstrapService (SaaS Elite Hardened).
 * Orquestra la inicialización del sistema usando el nuevo modelo de composición.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class BootstrapService {

    private final TenantRepository tenantRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AppBootstrapProperties appBootstrapProperties;
    private final ThemeRepository themeRepository;
    private final TenantDomainServiceImpl tenantDomainService;

    // SaaS Elite Services
    private final IdentityResolutionService identityResolutionService;
    private final PersonContextService personContextService;
    private final SuperAdminRepository superAdminRepository;
    private final TenantAdminRepository tenantAdminRepository;

    @Transactional
    public void runBootstrap() {
        Theme defaultTheme = themeRepository.findByCode(AppConstants.Themes.DEFAULT_THEME_CODE)
                .orElseThrow(() -> new ResourceNotFoundException(Theme.class.getName(),
                        "error.resource.not.found", AppConstants.Themes.DEFAULT_THEME_CODE));

        // 1. GLOBAL Tenant (System Admin Context)
        Tenant globalTenant = createOrUpdateTenant(appBootstrapProperties.getTenant().getTenantGlobal(), defaultTheme);

        // 2. SuperAdmin (Composition)
        createSuperAdminElite(appBootstrapProperties.getSuperAdmin(), globalTenant);

        // 3. LOCAL Tenant (Standard Hospital)
        Tenant localTenant = createOrUpdateTenant(appBootstrapProperties.getTenant().getTenantLocal(), defaultTheme);

        // 4. TenantAdmin (Composition)
        createTenantAdminElite(appBootstrapProperties.getTenantAdmin(), localTenant);

        log.info("🚀 Hardened Elite Bootstrap finalizado con éxito");
    }

    private void createSuperAdminElite(AppBootstrapProperties.AdminProperties config, Tenant tenant) {
        // Resolve Identity (Global)
        Person person = identityResolutionService.resolveOrCreate(config.getEmail(), null);
        person.setFirstName(config.getFirstName());
        person.setLastName(config.getLastName());

        // Resolve User
        User user = userRepository.findByEmail(config.getEmail()).orElseGet(() -> {
            User newUser = User.builder()
                    .email(config.getEmail())
                    .password(passwordEncoder.encode(config.getPassword()))
                    .enabled(true)
                    .person(person)
                    .build();
            return userRepository.save(newUser);
        });

        // Resolve Role Context (Atomic)
        personContextService.createContext(person, tenant, RoleContext.SUPER_ADMIN, DomainContext.SUPER_ADMIN);

        // Create SuperAdmin Entity (Composition if not exists)
        if (!superAdminRepository.existsByPerson(person)) {
            SuperAdmin superAdmin = SuperAdmin.builder()
                    .person(person)
                    .user(user)
                    .level(SuperAdminLevel.LEVEL_1)
                    .globalAccess(true)
                    .tenantId("SYSTEM")
                    .build();
            superAdminRepository.save(superAdmin);
            log.info("✔ SuperAdmin Elite creado exitosamente.");
        }

        // Assign Roles
        Set<Role> roles = resolveRoles(RoleContext.SUPER_ADMIN);
        personContextService.assignRoles(user, tenant, roles);
    }

    private void createTenantAdminElite(AppBootstrapProperties.AdminProperties config, Tenant tenant) {
        // Resolve Identity
        Person person = identityResolutionService.resolveOrCreate(config.getEmail(), null);
        person.setFirstName(config.getFirstName());
        person.setLastName(config.getLastName());

        // Resolve User
        User user = userRepository.findByEmail(config.getEmail()).orElseGet(() -> {
            User newUser = User.builder()
                    .email(config.getEmail())
                    .password(passwordEncoder.encode(config.getPassword()))
                    .enabled(true)
                    .person(person)
                    .build();
            return userRepository.save(newUser);
        });

        // Resolve Context
        personContextService.createContext(person, tenant, RoleContext.ADMIN, DomainContext.ADMIN);

        // Create TenantAdmin Entity
        if (!tenantAdminRepository.existsByPersonAndTenant(person, tenant)) {
            TenantAdmin tenantAdmin = TenantAdmin.builder()
                    .person(person)
                    .user(user)
                    .tenant(tenant)
                    .tenantId(tenant.getCode())
                    .adminLevel(TenantAdminLevel.LEVEL_1)
                    .build();
            tenantAdminRepository.save(tenantAdmin);
            log.info("✔ TenantAdmin Elite creado exitosamente para: {}", tenant.getCode());
        }

        // Assign Roles
        Set<Role> roles = resolveRoles(RoleContext.ADMIN);
        personContextService.assignRoles(user, tenant, roles);
    }

    private Tenant createOrUpdateTenant(AppBootstrapProperties.BootstrapTenantConfig config, Theme defaultTheme) {
        String code = config.getCode();
        Tenant tenant = tenantRepository.findByCode(code).orElseGet(() -> {
            log.info("🏢 Creando Tenant [{}]..", code);
            Tenant.TenantBuilder<?, ?> builder;
            if (config.getTenantType() == TenantType.HOSPITAL) {
                builder = com.amachi.app.core.domain.hospital.entity.Hospital.builder()
                        .legalName(config.getLegalName())
                        .taxId(config.getTaxId())
                        .medicalLicense(config.getMedicalLicense());
            } else {
                builder = Tenant.builder();
            }
            return builder.code(code).name(config.getName()).description(config.getDescription()).isActive(true).build();
        });

        if (config.getAddress() != null && tenant.getAddressId() == null) {
            AddressDto addressDto = AddressDto.builder()
                    .direccion(config.getAddress().getDireccion())
                    .ciudad(config.getAddress().getCiudad())
                    .numero(config.getAddress().getNumero())
                    .stateId(config.getAddress().getStateId())
                    .provinceId(config.getAddress().getProvinceId())
                    .municipalityId(config.getAddress().getMunicipalityId())
                    .countryId(config.getAddress().getPaisId())
                    .build();
            tenantDomainService.handleTenantAddress(tenant, TenantDto.builder().address(addressDto).build());
        }

        if (tenant.getTheme() == null) {
            tenant.setTheme(defaultTheme);
        }

        return tenantRepository.save(tenant);
    }

    private Set<Role> resolveRoles(RoleContext roleContext) {
        Set<Role> roles = new HashSet<>();
        String roleName = roleContext == RoleContext.SUPER_ADMIN ? AppConstants.Roles.ROLE_SUPER_ADMIN : AppConstants.Roles.ROLE_ADMIN;
        roleRepository.findByName(roleName).ifPresent(roles::add);
        return roles;
    }
}
