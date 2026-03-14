package com.amachi.app.core.management.config.bootstrap;

import com.amachi.app.core.auth.entity.Role;
import com.amachi.app.core.auth.entity.User;
import com.amachi.app.core.auth.entity.UserAccount;
import com.amachi.app.core.auth.repository.RoleRepository;
import com.amachi.app.core.auth.repository.UserRepository;
import com.amachi.app.core.auth.service.UserTenantRoleService;
import com.amachi.app.core.common.exception.ResourceNotFoundException;
import com.amachi.app.core.domain.config.AppBootstrapProperties;
import com.amachi.app.core.domain.tenant.entity.Tenant;
import com.amachi.app.core.common.enums.*;
import com.amachi.app.core.common.utils.AppConstants;
import com.amachi.app.core.domain.entity.PersonTenant;
import com.amachi.app.core.domain.repository.PersonRepository;
import com.amachi.app.core.domain.theme.entity.Theme;
import com.amachi.app.core.geography.country.entity.Country;
import com.amachi.app.core.management.superadmin.entity.SuperAdmin;
import com.amachi.app.core.management.tenantadmin.entity.TenantAdmin;
import com.amachi.app.core.domain.tenant.repository.TenantRepository;
import com.amachi.app.core.management.tenant.service.impl.TenantDomainServiceImpl; // 🟢 [JPA/Domain Adaptation]
import com.amachi.app.core.domain.tenant.dto.TenantDto; // 🟢 [JPA/Domain Adaptation]
import com.amachi.app.core.geography.address.dto.AddressDto; // 🟢 [JPA/Domain Adaptation]
import com.amachi.app.core.management.theme.repository.ThemeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class BootstrapService {
        private final TenantRepository tenantRepository;
        private final UserRepository userRepository;
        private final RoleRepository roleRepository;
        private final PasswordEncoder passwordEncoder;
        private final AppBootstrapProperties appBootstrapProperties;
        private final PersonRepository personRepository;
        private final UserTenantRoleService userTenantRoleService;
        private final ThemeRepository themeRepository;

        // 🟢 [JPA/Domain Adaptation] Injected to orchestrate Tenant+Address creation
        // respecting module structure
        private final TenantDomainServiceImpl tenantDomainService;

        @Transactional
        public void runBootstrap() {

                // 1️⃣ Cargar Theme por defecto desde BD
                Theme defaultTheme = themeRepository.findByCode(AppConstants.Themes.DEFAULT_THEME_CODE)
                                .orElseThrow(() -> new ResourceNotFoundException(Theme.class.getName(),
                                                "error.resource.not.found", AppConstants.Themes.DEFAULT_THEME_CODE));

                // 2️⃣ Crear Tenant GLOBAL
                Tenant globalTenant = createOrUpdateTenant(appBootstrapProperties.getTenant().getTenantGlobal(),
                                defaultTheme);

                // 3️⃣ Crear SUPER_ADMIN (Cascade Strategy)
                createSuperAdminWithCascade(appBootstrapProperties.getSuperAdmin(), globalTenant);

                // 4️⃣ Crear Tenant LOCAL (hospital-san-borja)
                Tenant localTenant = createOrUpdateTenant(appBootstrapProperties.getTenant().getTenantLocal(),
                                defaultTheme);

                // 5️⃣ Crear TENANT_ADMIN (Cascade Strategy)
                createTenantAdminWithCascade(appBootstrapProperties.getTenantAdmin(), localTenant);

                log.info("🚀 Bootstrap finalizado con éxito");
        }

        // -------------------------------------------------------
        // T E N A N T S
        // -------------------------------------------------------

        private Tenant createOrUpdateTenant(AppBootstrapProperties.BootstrapTenantConfig config, Theme defaultTheme) {
                String code = config.getCode();

                Tenant tenant = tenantRepository.findByCode(code).orElse(null);

                if (tenant == null) {
                        log.info("🏢 Creando Tenant [{}]..", code);
                        tenant = Tenant.builder()
                                        .code(code)
                                        .name(config.getName())
                                        .description(config.getDescription())
                                        .type(config.getTenantType())
                                        .isActive(true)
                                        .build();
                } else {
                        log.info("🏢 Tenant [{}] encontrado. Verificando actualizaciones..", code);
                }

                // 🟢 [JPA/Domain Adaptation] Orchestration for Address (External Module)
                // Polymorphic access: config.getAddress() handles global vs local logic
                // internally
                if (config.getAddress() != null) {

                        // Universal check: Does tenant lack address?
                        if (tenant.getAddressId() == null) {
                                log.info("📍 Agregando dirección al Tenant [{}]..", code);
                                AppBootstrapProperties.AddressProperties addrProps = config.getAddress();
                                AddressDto addressDto = AddressDto.builder()
                                                .direccion(addrProps.getDireccion())
                                                .ciudad(addrProps.getCiudad())
                                                .numero(addrProps.getNumero())
                                                .departamentoId(addrProps.getDepartamentoId())
                                                .provinciaId(addrProps.getProvinciaId())
                                                .municipioId(addrProps.getMunicipioId())
                                                .countryId(addrProps.getPaisId())
                                                .build();

                                TenantDto tempDto = TenantDto.builder()
                                                .address(addressDto)
                                                .build();

                                // Delegate to Domain Service (creates Address INSERT)
                                tenantDomainService.handleTenantAddress(tenant, tempDto);
                        }
                }

                // 🔹 Asignar Theme por defecto si no tiene Theme
                if (tenant.getTheme() == null) {
                        log.info("🎨 Asignando Theme maestro [{}] al Tenant [{}]..", defaultTheme.getName(), code);
                        tenant.setTheme(defaultTheme);
                }

                return tenantRepository.save(tenant);
        }

        // -------------------------------------------------------
        // U S E R S
        // -------------------------------------------------------

        // -------------------------------------------------------
        // U S E R S & P E R S O N S (Cascade Strategy)
        // -------------------------------------------------------

        private void createSuperAdminWithCascade(AppBootstrapProperties.AdminProperties config, Tenant tenant) {
                User existingUser = userRepository.findByEmail(config.getEmail()).orElse(null);
                if (existingUser != null) {
                        log.info("👤 SuperAdmin [{}] ya existe. Sincronizando credenciales y cuenta de tenant..", config.getEmail());
                        existingUser.setPassword(passwordEncoder.encode(config.getPassword()));
                        userRepository.save(existingUser);

                        // 🟢 Asegurar que tiene cuenta en el tenant GLOBAL
                        boolean hasAccount = existingUser.getUserAccounts().stream()
                                        .anyMatch(acc -> acc.getTenant().getCode().equals(tenant.getCode()));

                        if (!hasAccount) {
                                log.info("👤 Agregando cuenta faltante en tenant [{}] para SuperAdmin existente..", tenant.getCode());
                                UserAccount account = UserAccount.builder()
                                                .user(existingUser)
                                                .tenant(tenant)
                                                .person(existingUser.getPerson())
                                                .build();
                                existingUser.getUserAccounts().add(account);
                                userRepository.save(existingUser);
                        }

                        Set<Role> roles = resolveRoles(RoleContext.SUPER_ADMIN);
                        userTenantRoleService.assignRolesToUserAndTenant(existingUser, tenant, roles);
                        return;
                }

                log.info("👤 Creando SUPER_ADMIN con Cascade JPA..");

                // 1. Build User (Transient)
                User user = User.builder()
                                .email(config.getEmail())
                                .password(passwordEncoder.encode(config.getPassword()))
                                .enabled(true)
                                .accountLocked(false)
                                .build();

                // 2. Build SuperAdmin (Person)
                SuperAdmin superAdmin = SuperAdmin.builder()
                                .nombre(config.getFirstName())
                                .apellidoPaterno(config.getLastName())
                                .level(SuperAdminLevel.LEVEL_1)
                                .globalAccess(true)
                                .user(user) // 🟢 Inlined User
                                .personType(PersonType.SUPER_ADMIN)
                                .build();

                // 2.1 Set bidirectional relationship
                user.setPerson(superAdmin);

                // 5. Build PersonTenant (Cascade from Person)
                PersonTenant pt = PersonTenant.builder()
                                .person(superAdmin)
                                .tenant(tenant)
                                .roleContext(RoleContext.SUPER_ADMIN)
                                .relationStatus(RelationStatus.ACTIVE)
                                .dateRegistered(LocalDateTime.now())
                                .build();
                superAdmin.getPersonTenants().add(pt);

                // 6. SAVE ROOT (Atomic Operation)
                // JPA persists SuperAdmin -> User -> UserAccount (resolving associations
                // automatically)
                superAdmin = personRepository.save(superAdmin);

                // 6. Build UserAccount
                user = superAdmin.getUser();
                UserAccount account = UserAccount.builder()
                                .user(user)
                                .tenant(tenant)
                                .person(superAdmin) // 🟢 Set Person entity
                                .build();
                user.getUserAccounts().add(account);

                // 7. Final Save (Updates User, Inserts UserAccount via Cascade)
                userRepository.save(user);

                Set<Role> roles = resolveRoles(RoleContext.SUPER_ADMIN);
                userTenantRoleService.assignRolesToUserAndTenant(user, tenant, roles);

                log.info("✔ SuperAdmin creado exitosamente (ID: {}).", superAdmin.getId());
        }

        private void createTenantAdminWithCascade(AppBootstrapProperties.AdminProperties config, Tenant tenant) {
                User existingUser = userRepository.findByEmail(config.getEmail()).orElse(null);
                if (existingUser != null) {
                        log.info("👤 TenantAdmin [{}] ya existe. Sincronizando credenciales y cuenta de tenant..", config.getEmail());
                        existingUser.setPassword(passwordEncoder.encode(config.getPassword()));
                        userRepository.save(existingUser);

                        // 🟢 Asegurar que tiene cuenta en el tenant LOCAL
                        boolean hasAccount = existingUser.getUserAccounts().stream()
                                        .anyMatch(acc -> acc.getTenant().getCode().equals(tenant.getCode()));

                        if (!hasAccount) {
                                log.info("👤 Agregando cuenta faltante en tenant [{}] para TenantAdmin existente..", tenant.getCode());
                                UserAccount account = UserAccount.builder()
                                                .user(existingUser)
                                                .tenant(tenant)
                                                .person(existingUser.getPerson())
                                                .build();
                                existingUser.getUserAccounts().add(account);
                                userRepository.save(existingUser);
                        }

                        Set<Role> roles = resolveRoles(RoleContext.ADMIN);
                        userTenantRoleService.assignRolesToUserAndTenant(existingUser, tenant, roles);
                        return;
                }

                log.info("👤 Creando TENANT_ADMIN con Cascade JPA..");

                // 1. Build User
                User user = User.builder()
                                .email(config.getEmail())
                                .password(passwordEncoder.encode(config.getPassword()))
                                .enabled(true)
                                .accountLocked(false)
                                .build();

                // 2. Build TenantAdmin
                TenantAdmin tenantAdmin = TenantAdmin.builder()
                                .nombre(config.getFirstName())
                                .apellidoPaterno(config.getLastName())
                                .adminLevel(TenantAdminLevel.LEVEL_1)
                                .tenant(tenant) // TenantAdmin specific
                                .user(user) // 🟢 Inlined User
                                .personType(PersonType.ADMIN)
                                .build();

                // 2.1 Set bidirectional relationship
                user.setPerson(tenantAdmin);

                // 4. PersonTenant
                PersonTenant pt = PersonTenant.builder()
                                .person(tenantAdmin)
                                .tenant(tenant)
                                .roleContext(RoleContext.ADMIN)
                                .relationStatus(RelationStatus.ACTIVE)
                                .dateRegistered(LocalDateTime.now())
                                .build();
                tenantAdmin.getPersonTenants().add(pt);

                // 5. SAVE ROOT (TenantAdmin -> User, PersonTenant) - Generates associations
                tenantAdmin = personRepository.save(tenantAdmin);

                // 6. Build UserAccount
                user = tenantAdmin.getUser();
                UserAccount account = UserAccount.builder()
                                .user(user)
                                .tenant(tenant)
                                .person(tenantAdmin) // 🟢 Set Person entity
                                .build();
                user.getUserAccounts().add(account);

                // 7. Final Save (Updates User, Inserts UserAccount via Cascade)
                userRepository.save(user);

                Set<Role> roles = resolveRoles(RoleContext.ADMIN);
                userTenantRoleService.assignRolesToUserAndTenant(user, tenant, roles);

                log.info("✔ TenantAdmin creado exitosamente (ID: {}).", tenantAdmin.getId());
        }

        private Set<Role> resolveRoles(RoleContext roleContext) {
                Set<Role> roles = new HashSet<>();
                String roleName = roleContext == RoleContext.SUPER_ADMIN ? AppConstants.Roles.ROLE_SUPER_ADMIN
                                : AppConstants.Roles.ROLE_ADMIN;
                roleRepository.findByName(roleName).ifPresent(roles::add);
                return roles;
        }
}
