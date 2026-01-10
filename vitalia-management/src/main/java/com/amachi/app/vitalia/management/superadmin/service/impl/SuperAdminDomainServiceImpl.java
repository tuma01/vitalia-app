package com.amachi.app.vitalia.management.superadmin.service.impl;

import com.amachi.app.core.auth.entity.Role;
import com.amachi.app.core.auth.entity.User;
import com.amachi.app.core.auth.entity.UserAccount;
import com.amachi.app.core.auth.repository.RoleRepository;
import com.amachi.app.core.auth.repository.UserRepository;
import com.amachi.app.core.auth.service.UserTenantRoleService;
import com.amachi.app.core.domain.config.AppBootstrapProperties;
import com.amachi.app.core.domain.tenant.entity.Tenant;
import com.amachi.app.core.common.utils.AppConstants;
import com.amachi.app.vitalia.management.superadmin.dto.SuperAdminDto;
import com.amachi.app.vitalia.management.superadmin.entity.SuperAdmin;
import com.amachi.app.core.domain.tenant.repository.TenantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class SuperAdminDomainServiceImpl {

    private final UserRepository userRepository;
    private final UserTenantRoleService userTenantRoleService;
    private final RoleRepository roleRepository;
    private final TenantRepository tenantRepository;
    private final PasswordEncoder passwordEncoder;
    private final AppBootstrapProperties appBootstrapProperties;

    /**
     * Professional setup of the UserAccount and Roles after the SuperAdmin is
     * persisted.
     * Use "Split Persistence" strategy to handle the personId constraint.
     */
    public void completeAccountSetup(SuperAdmin savedEntity) {
        // 1. Validation
        if (savedEntity.getId() == null) {
            throw new IllegalStateException("SuperAdmin must be saved before setting up account.");
        }

        // 2. Fix User person (JPA relation)
        savedEntity.getUser().setPerson(savedEntity);

        // 3. Find Global Tenant
        String globalTenantCode = appBootstrapProperties.getTenant().getTenantGlobal().getCode();
        Tenant globalTenant = tenantRepository.findByCode(globalTenantCode)
                .orElseThrow(() -> new IllegalStateException("Global Tenant not found with code: " + globalTenantCode));

        // 4. Create UserAccount (if not exists)
        UserAccount account = UserAccount.builder()
                .user(savedEntity.getUser())
                .tenant(globalTenant)
                .person(savedEntity) // 🟢 Valid person object
                .build();
        savedEntity.getUser().getUserAccounts().add(account);

        // 5. Update User with new Account and personId
        userRepository.save(savedEntity.getUser());

        // 6. Assign Roles (SuperAdmin Role)
        assignSuperAdminRole(savedEntity.getUser(), globalTenant);

        log.info("✔ Account setup completed for SuperAdmin ID: {}", savedEntity.getId());
    }

    // Overloaded to match controller usage directly if passing entity
    public void encodePasswordIfNeeded(SuperAdmin entity) {
        User user = entity.getUser();
        if (user == null) {
            return;
        }
        if (user.getId() == null && user.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
    }

    private void assignSuperAdminRole(User user, Tenant tenant) {
        Set<Role> roles = new HashSet<>();
        roleRepository.findByName(AppConstants.Roles.ROLE_SUPER_ADMIN).ifPresent(roles::add);
        userTenantRoleService.assignRolesToUserAndTenant(user, tenant, roles);
    }
}
