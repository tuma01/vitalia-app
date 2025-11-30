package com.amachi.app.vitalia.config.bootstrap;

import com.amachi.app.vitalia.authentication.dto.UserRegisterRequest;
import com.amachi.app.vitalia.authentication.entity.Role;
import com.amachi.app.vitalia.authentication.entity.User;
import com.amachi.app.vitalia.authentication.entity.UserAccount;
import com.amachi.app.vitalia.authentication.repository.RoleRepository;
import com.amachi.app.vitalia.authentication.repository.TenantRepository;
import com.amachi.app.vitalia.authentication.repository.UserAccountRepository;
import com.amachi.app.vitalia.authentication.repository.UserRepository;
import com.amachi.app.vitalia.authentication.service.UserTenantRoleService;
import com.amachi.app.vitalia.common.config.AppBootstrapProperties;
import com.amachi.app.vitalia.common.entity.Tenant;
import com.amachi.app.vitalia.common.enums.PersonType;
import com.amachi.app.vitalia.common.enums.RelationStatus;
import com.amachi.app.vitalia.common.enums.RoleContext;
import com.amachi.app.vitalia.common.enums.TenantType;
import com.amachi.app.vitalia.common.utils.AppConstants;
import com.amachi.app.vitalia.person.entity.Person;
import com.amachi.app.vitalia.person.entity.PersonTenant;
import com.amachi.app.vitalia.person.factory.PersonFactory;
import com.amachi.app.vitalia.person.repository.PersonRepository;
import com.amachi.app.vitalia.person.repository.PersonTenantRepository;
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
    private final UserAccountRepository userAccountRepository;
    private final PersonTenantRepository personTenantRepository;
    private final RoleRepository roleRepository;
    private final PersonFactory personFactory;
    private final PasswordEncoder passwordEncoder;
    private final AppBootstrapProperties appBootstrapProperties;
    private final PersonRepository personRepository;
    private final UserTenantRoleService userTenantRoleService;

    @Transactional
    public void runBootstrap() {

        // 1️⃣ Crear Tenant GLOBAL
        Tenant globalTenant = createTenantIfAbsent(appBootstrapProperties.getTenant().getTenantGlobal());

        // 2️⃣ Crear SUPER_ADMIN y persistir Person + PersonTenant + UserAccount
        User superAdmin = createUserIfAbsent(appBootstrapProperties.getSuperAdmin());
        createUserAccountAndPersonTenant(superAdmin, globalTenant, appBootstrapProperties.getSuperAdmin(), RoleContext.SUPER_ADMIN);

        // 3️⃣ Crear Tenant LOCAL (HOSP_A)
        Tenant localTenant = createTenantIfAbsent(appBootstrapProperties.getTenant().getTenantLocal());

        // 4️⃣ Crear TENANT_ADMIN y persistir Person + PersonTenant + UserAccount
        User tenantAdmin = createUserIfAbsent(appBootstrapProperties.getTenantAdmin());
        createUserAccountAndPersonTenant(tenantAdmin, localTenant, appBootstrapProperties.getTenantAdmin(), RoleContext.TENANT_ADMIN);

        log.info("🚀 Bootstrap finalizado con éxito");
    }

    // -------------------------------------------------------
    //  T E N A N T S
    // -------------------------------------------------------

    private Tenant createTenantIfAbsent(Object cfg) {
        String code = cfg instanceof AppBootstrapProperties.TenantGlobal ?
                ((AppBootstrapProperties.TenantGlobal) cfg).getCode() :
                ((AppBootstrapProperties.TenantLocal) cfg).getCode();

        return tenantRepository.findByCode(code).orElseGet(() -> {
            String name = cfg instanceof AppBootstrapProperties.TenantGlobal ?
                    ((AppBootstrapProperties.TenantGlobal) cfg).getName() :
                    ((AppBootstrapProperties.TenantLocal) cfg).getName();

            String description = cfg instanceof AppBootstrapProperties.TenantGlobal ?
                    ((AppBootstrapProperties.TenantGlobal) cfg).getDescription() : null;

            TenantType type = cfg instanceof AppBootstrapProperties.TenantGlobal ? TenantType.GLOBAL : TenantType.HOSPITAL;

            log.info("🏢 Creando Tenant [{}]...", code);
            Tenant t = Tenant.builder()
                    .code(code)
                    .name(name)
                    .description(description)
                    .type(type)
                    .isActive(true)
                    .build();
            return tenantRepository.save(t);
        });
    }

    // -------------------------------------------------------
    //  U S E R S
    // -------------------------------------------------------

    private User createUserIfAbsent(AppBootstrapProperties.AdminProperties config) {
        return userRepository.findByEmail(config.getEmail())
                .orElseGet(() -> {
                    log.info("👤 Creando usuario {}", config.getEmail());
                    User user = User.builder()
                            .email(config.getEmail())
                            .password(passwordEncoder.encode(config.getPassword()))
                            .enabled(true)
                            .accountLocked(false)
                            .build();
                    return userRepository.save(user);
                });
    }

    // -------------------------------------------------------
    //  U S E R   A C C O U N T + P E R S O N + P E R S O N_TENANT
    // -------------------------------------------------------

    private void createUserAccountAndPersonTenant(User user, Tenant tenant, AppBootstrapProperties.AdminProperties config, RoleContext roleContext) {

        if (userAccountRepository.existsByUserAndTenant(user, tenant)) {
            log.info("✔ UserAccount ya existe para {} en tenant {}", user.getEmail(), tenant.getCode());
            return;
        }

        // Construir DTO
        UserRegisterRequest dto = UserRegisterRequest.builder()
                .nombre(config.getFirstName())
                .apellidoPaterno(config.getLastName())
                .personType(PersonType.valueOf(config.getPersonType()))
                .tenantCode(tenant.getCode())
                .build();

        // 1️⃣ Crear la persona concreta usando PersonFactory
        Person person = personFactory.create(dto.getPersonType(), dto);
        person = personRepository.save(person); // Persistir Person

        // 2️⃣ Crear PersonTenant (relación Person + Tenant)
        PersonTenant pt = PersonTenant.builder()
                .person(person)
                .tenant(tenant)
                .roleContext(roleContext)
                .relationStatus(RelationStatus.ACTIVE)
                .dateRegistered(LocalDateTime.now())
                .build();
        personTenantRepository.save(pt); // Persistir PersonTenant

        // 3️⃣ Crear UserAccount
        Set<Role> roles = resolveRoles(roleContext);
        UserAccount account = UserAccount.builder()
                .user(user)
                .personId(person.getId())
                .tenant(tenant)
                .roles(roles)
                .build();
        userAccountRepository.save(account); // Persistir UserAccount

        // <-- persist user_tenant_role aquí: asigna las roles correspondientes
        userTenantRoleService.assignRolesToUserAndTenant(user, tenant, roles);

        log.info("🛠 Creado UserAccount → user={}, tenant={}, roles={}",
                user.getEmail(), tenant.getCode(), roles.stream().map(Role::getName).toList());
    }

    private Set<Role> resolveRoles(RoleContext roleContext) {
        Set<Role> roles = new HashSet<>();
        String roleName = roleContext == RoleContext.SUPER_ADMIN ? AppConstants.Roles.ROLE_SUPER_ADMIN : AppConstants.Roles.ROLE_ADMIN;
        roleRepository.findByName(roleName).ifPresent(roles::add);
        return roles;
    }
}

