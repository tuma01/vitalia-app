package com.amachi.app.vitalia.management.superadmin.service.impl;

import com.amachi.app.core.auth.entity.Role;
import com.amachi.app.core.auth.entity.User;
import com.amachi.app.core.auth.repository.RoleRepository;
import com.amachi.app.core.auth.repository.UserRepository;
import com.amachi.app.core.auth.service.UserTenantRoleService;
import com.amachi.app.core.domain.config.AppBootstrapProperties;
import com.amachi.app.core.domain.tenant.entity.Tenant;
import com.amachi.app.core.common.test.util.AbstractTestSupport;
import com.amachi.app.vitalia.management.superadmin.entity.SuperAdmin;
import com.amachi.app.core.domain.tenant.repository.TenantRepository;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Optional;

import static org.instancio.Select.field;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SuperAdminDomainServiceImplTest extends AbstractTestSupport {

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserTenantRoleService userTenantRoleService;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private TenantRepository tenantRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private AppBootstrapProperties appBootstrapProperties;

    // Mocks for deep property access
    @Mock
    private AppBootstrapProperties.TenantProperties tenantProperties;
    @Mock
    private AppBootstrapProperties.TenantGlobal tenantGlobal;

    @InjectMocks
    private SuperAdminDomainServiceImpl domainService;

    @Test
    void completeAccountSetup_WhenGlobalTenantExists_ThenSetupAccountAndRoles() {
        // Arrange
        SuperAdmin savedEntity = Instancio.create(SuperAdmin.class);
        // Ensure collections/fields are ready for logic
        savedEntity.getUser().setUserAccounts(new HashSet<>());
        savedEntity.setPersonTenants(new HashSet<>());

        String globalCode = "GLOBAL";
        Tenant globalTenant = Instancio.of(Tenant.class)
                .set(field(Tenant::getCode), globalCode)
                .create();

        // 1. Mock Property Chain (Lenient)
        lenient().when(appBootstrapProperties.getTenant()).thenReturn(tenantProperties);
        lenient().when(tenantProperties.getTenantGlobal()).thenReturn(tenantGlobal);
        lenient().when(tenantGlobal.getCode()).thenReturn(globalCode);

        // 2. Mock Repository Lookups
        when(tenantRepository.findByCode(globalCode)).thenReturn(Optional.of(globalTenant));

        Role superAdminRole = new Role();
        superAdminRole.setId(1L);
        when(roleRepository.findByName(anyString())).thenReturn(Optional.of(superAdminRole));

        // Act
        domainService.completeAccountSetup(savedEntity);

        // Assert
        User user = savedEntity.getUser();
        assertEquals(savedEntity.getId(), user.getPersonId()); // Verify ID sync
        assertFalse(user.getUserAccounts().isEmpty());
        // Verify Account is linked to Global Tenant
        assertEquals(globalTenant.getId(), user.getUserAccounts().iterator().next().getTenant().getId());

        verify(userRepository).save(user);
        verify(userTenantRoleService).assignRolesToUserAndTenant(eq(user), eq(globalTenant), anySet());
    }

    @Test
    void completeAccountSetup_WhenGlobalTenantMissing_ThenThrowException() {
        // Arrange
        SuperAdmin savedEntity = Instancio.create(SuperAdmin.class);
        String globalCode = "GLOBAL";

        // 1. Mock Property Chain (Lenient)
        lenient().when(appBootstrapProperties.getTenant()).thenReturn(tenantProperties);
        lenient().when(tenantProperties.getTenantGlobal()).thenReturn(tenantGlobal);
        lenient().when(tenantGlobal.getCode()).thenReturn(globalCode);

        // 2. Mock Repository Lookup (MISSING TENANT)
        when(tenantRepository.findByCode(globalCode)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalStateException.class, () -> domainService.completeAccountSetup(savedEntity));
        verify(userRepository, never()).save(any());
    }

    @Test
    void encodePasswordIfNeeded_WhenNewUserStarting_ThenEncode() {
        // Arrange
        SuperAdmin entity = Instancio.create(SuperAdmin.class);
        User user = entity.getUser();
        user.setId(null); // New user
        user.setPassword("rawPassword");

        when(passwordEncoder.encode("rawPassword")).thenReturn("encodedPassword");

        // Act
        domainService.encodePasswordIfNeeded(entity);

        // Assert
        assertEquals("encodedPassword", user.getPassword());
    }
}
