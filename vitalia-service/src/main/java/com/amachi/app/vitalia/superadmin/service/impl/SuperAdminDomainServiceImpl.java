package com.amachi.app.vitalia.superadmin.service.impl;

import com.amachi.app.vitalia.authentication.entity.Role;
import com.amachi.app.vitalia.authentication.entity.User;
import com.amachi.app.vitalia.authentication.entity.UserAccount;
import com.amachi.app.vitalia.authentication.repository.RoleRepository;
import com.amachi.app.vitalia.authentication.repository.UserRepository;
import com.amachi.app.vitalia.authentication.service.UserTenantRoleService;
import com.amachi.app.vitalia.common.config.AppBootstrapProperties;
import com.amachi.app.vitalia.common.entity.Tenant;
import com.amachi.app.vitalia.common.utils.AppConstants;
import com.amachi.app.vitalia.superadmin.dto.SuperAdminDto;
import com.amachi.app.vitalia.superadmin.entity.SuperAdmin;
import com.amachi.app.vitalia.tenant.repository.TenantRepository;
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

        Long personId = savedEntity.getId();
        User user = savedEntity.getUser();

        // 2. Fix User personId (Raw Long Requirement)
        user.setPersonId(personId);

        // 3. Find Global Tenant
        String globalTenantCode = appBootstrapProperties.getTenant().getTenantGlobal().getCode();
        Tenant globalTenant = tenantRepository.findByCode(globalTenantCode)
                .orElseThrow(() -> new IllegalStateException("Global Tenant not found with code: " + globalTenantCode));

        // 4. Create UserAccount (if not exists)
        if (user.getUserAccounts().isEmpty()) {
            UserAccount account = UserAccount.builder()
                    .user(user)
                    .tenant(globalTenant)
                    .personId(personId) // 🟢 Valid personId
                    .build();
            user.getUserAccounts().add(account);
        }

        // 5. Update User with new Account and personId
        userRepository.save(user);

        // 6. Assign Roles (SuperAdmin Role)
        assignSuperAdminRole(user, globalTenant);

        log.info("✔ Account setup completed for SuperAdmin ID: {}", personId);
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
