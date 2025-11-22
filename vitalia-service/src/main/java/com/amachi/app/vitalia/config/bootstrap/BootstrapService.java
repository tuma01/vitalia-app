package com.amachi.app.vitalia.config.bootstrap;

import com.amachi.app.vitalia.authentication.entity.Role;
import com.amachi.app.vitalia.authentication.entity.User;
import com.amachi.app.vitalia.authentication.entity.UserAccount;
import com.amachi.app.vitalia.authentication.repository.RoleRepository;
import com.amachi.app.vitalia.authentication.repository.TenantRepository;
import com.amachi.app.vitalia.authentication.repository.UserAccountRepository;
import com.amachi.app.vitalia.authentication.repository.UserRepository;
import com.amachi.app.vitalia.common.entity.Tenant;
import com.amachi.app.vitalia.common.enums.PersonType;
import com.amachi.app.vitalia.common.enums.TenantType;
import com.amachi.app.vitalia.common.utils.AppConstants;
import com.amachi.app.vitalia.person.entity.Person;
import com.amachi.app.vitalia.person.factory.PersonFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class BootstrapService {

    private final TenantRepository tenantRepository;
    private final UserRepository userRepository;
    private final UserAccountRepository userAccountRepository;
    private final RoleRepository roleRepository;
    private final PersonFactory personFactory;
    private final PasswordEncoder passwordEncoder;
    private final AppBootstrapProperties appBootstrapProperties;

    @Transactional
    public void runBootstrap() {

        // 1️⃣ Crear Tenant GLOBAL
        Tenant globalTenant = createGlobalTenantIfAbsent();

        // 2️⃣ Crear SUPER_ADMIN (vinculado al tenant GLOBAL)
        User superAdmin = createUserIfAbsent(appBootstrapProperties.getSuperAdmin());
        createUserAccountIfAbsent(superAdmin, globalTenant, appBootstrapProperties.getSuperAdmin(), AppConstants.Bootstrap.SUPER_ADMIN);

        // 3️⃣ Crear Tenant LOCAL (HOSP_A)
        Tenant localTenant = createLocalTenantIfAbsent();

        // 4️⃣ Crear TenantAdmin local (ADMIN del tenant HOSP_A)
        User tenantAdmin = createUserIfAbsent(appBootstrapProperties.getTenantAdmin());
        createUserAccountIfAbsent(tenantAdmin, localTenant, appBootstrapProperties.getTenantAdmin(), AppConstants.Bootstrap.TENANT_ADMIN);

        log.info("🚀 Bootstrap finalizado con éxito");
    }

    // -------------------------------------------------------
    //  T E N A N T S
    // -------------------------------------------------------

    private Tenant createGlobalTenantIfAbsent() {
        AppBootstrapProperties.TenantGlobal cfg = appBootstrapProperties.getTenant().getTenantGlobal();

        return tenantRepository.findByCode(cfg.getCode())
                .orElseGet(() -> {
                    log.info("🌍 Creando Tenant GLOBAL [{}]...", cfg.getCode());
                    Tenant t = Tenant.builder()
                            .code(cfg.getCode())
                            .name(cfg.getName())
                            .type(TenantType.GLOBAL)
                            .description(cfg.getDescription())
                            .isActive(true)
                            .build();
                    return tenantRepository.save(t);
                });
    }

    private Tenant createLocalTenantIfAbsent() {
        AppBootstrapProperties.TenantLocal cfg = appBootstrapProperties.getTenant().getTenantLocal();

        return tenantRepository.findByCode(cfg.getCode())
                .orElseGet(() -> {
                    log.info("🏥 Creando Tenant LOCAL [{}]...", cfg.getCode());
                    Tenant t = Tenant.builder()
                            .code(cfg.getCode())
                            .name(cfg.getName())
                            .type(TenantType.HOSPITAL)
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
    //  U S E R   A C C O U N T S   (Persona + Roles)
    // -------------------------------------------------------

    private void createUserAccountIfAbsent(User user, Tenant tenant, AppBootstrapProperties.AdminProperties config, String adminType) {

        if (userAccountRepository.existsByUserAndTenant(user, tenant)) {
            log.info("✔ UserAccount ya existe para {} en tenant {}", user.getEmail(), tenant.getCode());
            return;
        }

        // Crear la persona concreta por tipo
        PersonType type = PersonType.valueOf(config.getPersonType());
        Person person = personFactory.create(type);

        // Asignar roles de acuerdo al tipo de admin
        Set<Role> roles = resolveRoles(adminType);

        log.info("🛠 Creando UserAccount → user={}, tenant={}, roles={}",
                user.getEmail(), tenant.getCode(), roles.stream().map(Role::getName).toList());

        UserAccount account = UserAccount.builder()
                .user(user)
                .personId(person.getId())
                .tenant(tenant)
                .roles(roles)
                .build();

        userAccountRepository.save(account);
    }

    private Set<Role> resolveRoles(String adminType) {
        Set<Role> roles = new HashSet<>();

        String roleName = adminType.equalsIgnoreCase(AppConstants.Bootstrap.SUPER_ADMIN)
                ? AppConstants.Roles.ROLE_SUPER_ADMIN
                : AppConstants.Roles.ROLE_ADMIN;

        roleRepository.findByName(roleName).ifPresent(roles::add);

        return roles;
    }
}

