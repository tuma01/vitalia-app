package com.amachi.app.vitalia.config.bootstrap;

import com.amachi.app.vitalia.authentication.entity.Role;
import com.amachi.app.vitalia.authentication.entity.User;
import com.amachi.app.vitalia.authentication.entity.UserAccount;
import com.amachi.app.vitalia.authentication.repository.RoleRepository;
import com.amachi.app.vitalia.authentication.repository.UserRepository;
import com.amachi.app.vitalia.authentication.service.UserTenantRoleService;
import com.amachi.app.vitalia.common.config.AppBootstrapProperties;
import com.amachi.app.vitalia.common.entity.Tenant;
import com.amachi.app.vitalia.common.enums.*;
import com.amachi.app.vitalia.common.utils.AppConstants;
import com.amachi.app.vitalia.person.entity.PersonTenant;
import com.amachi.app.vitalia.person.repository.PersonRepository;
import com.amachi.app.vitalia.superadmin.entity.SuperAdmin;
import com.amachi.app.vitalia.tenantadmin.entity.TenantAdmin;
import com.amachi.app.vitalia.tenant.repository.TenantRepository;
import com.amachi.app.vitalia.tenant.service.impl.TenantDomainServiceImpl; // 🟢 [JPA/Domain Adaptation]
import com.amachi.app.vitalia.tenant.dto.TenantDto; // 🟢 [JPA/Domain Adaptation]
import com.amachi.app.vitalia.geography.address.dto.AddressDto; // 🟢 [JPA/Domain Adaptation]
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

        // 🟢 [JPA/Domain Adaptation] Injected to orchestrate Tenant+Address creation
        // respecting module structure
        private final TenantDomainServiceImpl tenantDomainService;

        @Transactional
        public void runBootstrap() {

                // 1️⃣ Crear Tenant GLOBAL
                Tenant globalTenant = createOrUpdateTenant(appBootstrapProperties.getTenant().getTenantGlobal());

                // 2️⃣ Crear SUPER_ADMIN (Cascade Strategy)
                createSuperAdminWithCascade(appBootstrapProperties.getSuperAdmin(), globalTenant);

                // 3️⃣ Crear Tenant LOCAL (HOSP_A)
                Tenant localTenant = createOrUpdateTenant(appBootstrapProperties.getTenant().getTenantLocal());

                // 4️⃣ Crear TENANT_ADMIN (Cascade Strategy)
                createTenantAdminWithCascade(appBootstrapProperties.getTenantAdmin(), localTenant);

                log.info("🚀 Bootstrap finalizado con éxito");
        }

        // -------------------------------------------------------
        // T E N A N T S
        // -------------------------------------------------------

        private Tenant createOrUpdateTenant(AppBootstrapProperties.BootstrapTenantConfig config) {
                String code = config.getCode();

                Tenant tenant = tenantRepository.findByCode(code).orElse(null);

                if (tenant == null) {
                        log.info("🏢 Creando Tenant [{}]...", code);
                        tenant = Tenant.builder()
                                        .code(code)
                                        .name(config.getName())
                                        .description(config.getDescription())
                                        .type(config.getTenantType())
                                        .isActive(true)
                                        .build();
                } else {
                        log.info("🏢 Tenant [{}] encontrado. Verificando actualizaciones...", code);
                }

                // 🟢 [JPA/Domain Adaptation] Orchestration for Address (External Module)
                // Polymorphic access: config.getAddress() handles global vs local logic
                // internally
                if (config.getAddress() != null) {

                        // Universal check: Does tenant lack address?
                        if (tenant.getAddressId() == null) {
                                log.info("📍 Agregando dirección al Tenant [{}]...", code);
                                AppBootstrapProperties.AddressProperties addrProps = config.getAddress();
                                AddressDto addressDto = AddressDto.builder()
                                                .direccion(addrProps.getDireccion())
                                                .ciudad(addrProps.getCiudad())
                                                .numero(addrProps.getNumero())
                                                .departamentoId(addrProps.getDepartamentoId())
                                                .countryId(addrProps.getPaisId())
                                                .build();

                                TenantDto tempDto = TenantDto.builder()
                                                .address(addressDto)
                                                .build();

                                // Delegate to Domain Service (creates Address INSERT)
                                tenantDomainService.handleTenantAddress(tenant, tempDto);
                        }
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
                if (userRepository.existsByEmail(config.getEmail())) {
                        log.info("👤 SuperAdmin [{}] ya existe. Omitiendo...", config.getEmail());
                        return;
                }

                log.info("👤 Creando SUPER_ADMIN con Cascade JPA...");

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
                // JPA persists SuperAdmin -> User -> UserAccount (resolving FKs automatically)
                superAdmin = personRepository.save(superAdmin);
                Long personId = superAdmin.getId(); // ID generated!

                // 6. Fix IDs & Build UserAccount
                // ⚠️ REQUIRED: User.personId is a raw Long, not a JPA relation.
                // We MUST set it manually after ID generation because JPA won't auto-propagate
                // it to a non-relation field.
                user = superAdmin.getUser();
                user.setPersonId(personId);

                UserAccount account = UserAccount.builder()
                                .user(user)
                                .tenant(tenant)
                                .personId(personId) // 🟢 Valid personId
                                .build();
                user.getUserAccounts().add(account);

                // 7. Final Save (Updates User, Inserts UserAccount via Cascade)
                userRepository.save(user);

                Set<Role> roles = resolveRoles(RoleContext.SUPER_ADMIN);
                userTenantRoleService.assignRolesToUserAndTenant(user, tenant, roles);

                log.info("✔ SuperAdmin creado exitosamente (ID: {}).", superAdmin.getId());
        }

        private void createTenantAdminWithCascade(AppBootstrapProperties.AdminProperties config, Tenant tenant) {
                if (userRepository.existsByEmail(config.getEmail())) {
                        log.info("👤 TenantAdmin [{}] ya existe. Omitiendo...", config.getEmail());
                        return;
                }

                log.info("👤 Creando TENANT_ADMIN con Cascade JPA...");

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

                // 4. PersonTenant
                PersonTenant pt = PersonTenant.builder()
                                .person(tenantAdmin)
                                .tenant(tenant)
                                .roleContext(RoleContext.ADMIN)
                                .relationStatus(RelationStatus.ACTIVE)
                                .dateRegistered(LocalDateTime.now())
                                .build();
                tenantAdmin.getPersonTenants().add(pt);

                // 5. SAVE ROOT (TenantAdmin -> User, PersonTenant) - Generates IDs
                tenantAdmin = personRepository.save(tenantAdmin);
                Long personId = tenantAdmin.getId(); // ID generated!

                // 6. Fix IDs & Build UserAccount
                // ⚠️ REQUIRED: User.personId is a raw Long. Must set manually.
                user = tenantAdmin.getUser();
                user.setPersonId(personId);

                UserAccount account = UserAccount.builder()
                                .user(user)
                                .tenant(tenant)
                                .personId(personId) // 🟢 Valid personId
                                .build();
                user.getUserAccounts().add(account);

                // 7. Final Save (Updates User, Inserts UserAccount via Cascade)
                userRepository.save(user);

                Set<Role> roles = resolveRoles(RoleContext.ADMIN);
                userTenantRoleService.assignRolesToUserAndTenant(user, tenant, roles);

                log.info("✔ TenantAdmin creado exitosamente (ID: {}).", personId);
        }

        private Set<Role> resolveRoles(RoleContext roleContext) {
                Set<Role> roles = new HashSet<>();
                String roleName = roleContext == RoleContext.SUPER_ADMIN ? AppConstants.Roles.ROLE_SUPER_ADMIN
                                : AppConstants.Roles.ROLE_ADMIN;
                roleRepository.findByName(roleName).ifPresent(roles::add);
                return roles;
        }
}
