package com.amachi.app.vitalia.management.tenantadmin.service.impl;

import com.amachi.app.core.auth.entity.Role;
import com.amachi.app.core.auth.entity.User;
import com.amachi.app.core.auth.repository.RoleRepository;
import com.amachi.app.core.auth.repository.UserRepository;
import com.amachi.app.core.auth.service.UserTenantRoleService;
import com.amachi.app.core.common.test.util.AbstractTestSupport;
import com.amachi.app.core.geography.address.dto.AddressDto;
import com.amachi.app.core.geography.address.entity.Address;
import com.amachi.app.core.geography.address.mapper.AddressMapper;
import com.amachi.app.core.geography.address.service.impl.AddressServiceImpl;
import com.amachi.app.vitalia.management.tenantadmin.dto.TenantAdminDto;
import com.amachi.app.vitalia.management.tenantadmin.entity.TenantAdmin;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Select.field;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TenantAdminDomainServiceImplTest extends AbstractTestSupport {

    @Mock
    private AddressServiceImpl addressService;
    @Mock
    private AddressMapper addressMapper;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserTenantRoleService userTenantRoleService;
    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private TenantAdminDomainServiceImpl domainService;

    // --- Address Logic Tests ---

    @Test
    void handleTenantAddress_WhenCreate_ThenCreateAddress() {
        // Arrange
        TenantAdmin entity = Instancio.create(TenantAdmin.class);
        entity.getTenant().setAddressId(null); // No existing address

        AddressDto addressDto = Instancio.of(AddressDto.class)
                .set(field(AddressDto::getId), null)
                .create();

        TenantAdminDto dto = Instancio.of(TenantAdminDto.class)
                .set(field(TenantAdminDto::getTenant), Instancio.of(com.amachi.app.core.domain.tenant.dto.TenantDto.class)
                        .set(field(com.amachi.app.core.domain.tenant.dto.TenantDto::getAddress), addressDto)
                        .create())
                .create();

        Address createdAddress = Instancio.create(Address.class);

        when(addressMapper.toEntity(any())).thenReturn(createdAddress);
        when(addressService.create(any())).thenReturn(createdAddress);

        // Act
        domainService.handleTenantAddress(entity, dto);

        // Assert
        assertEquals(createdAddress.getId(), entity.getTenant().getAddressId());
    }

    @Test
    void handleTenantAddress_WhenUpdateWithIdRequest_ThenVerifySecurityAndCallUpdate() {
        // Arrange
        Long addressId = 100L;
        TenantAdmin entity = Instancio.create(TenantAdmin.class);
        entity.getTenant().setAddressId(addressId);

        AddressDto addressDto = Instancio.of(AddressDto.class)
                .set(field(AddressDto::getId), addressId)
                .create();

        TenantAdminDto dto = Instancio.of(TenantAdminDto.class)
                .set(field(TenantAdminDto::getTenant), Instancio.of(com.amachi.app.core.domain.tenant.dto.TenantDto.class)
                        .set(field(com.amachi.app.core.domain.tenant.dto.TenantDto::getAddress), addressDto)
                        .create())
                .create();

        Address address = Instancio.create(Address.class);
        when(addressService.getById(addressId)).thenReturn(address);

        // Act
        domainService.handleTenantAddress(entity, dto);

        // Assert
        verify(addressService).update(eq(addressId), any());
    }

    @Test
    void handleTenantAddress_WhenSecurityViolation_ThenThrowException() {
        // Arrange
        Long originalId = 100L;
        Long hackingId = 200L;

        TenantAdmin entity = Instancio.create(TenantAdmin.class);
        entity.getTenant().setAddressId(originalId);

        AddressDto addressDto = Instancio.of(AddressDto.class)
                .set(field(AddressDto::getId), hackingId)
                .create();

        TenantAdminDto dto = Instancio.of(TenantAdminDto.class)
                .set(field(TenantAdminDto::getTenant), Instancio.of(com.amachi.app.core.domain.tenant.dto.TenantDto.class)
                        .set(field(com.amachi.app.core.domain.tenant.dto.TenantDto::getAddress), addressDto)
                        .create())
                .create();

        // Act & Assert
        assertThrows(IllegalStateException.class, () -> domainService.handleTenantAddress(entity, dto));
        verify(addressService, never()).update(any(), any());
    }

    // --- Account Setup Logic Tests ---

    @Test
    void completeAccountSetup_WhenValid_ThenSetupRolesAndUser() {
        // Arrange
        TenantAdmin savedEntity = Instancio.create(TenantAdmin.class);
        // Ensure collections are mutable/empty for logic
        savedEntity.getUser().setUserAccounts(new HashSet<>());
        savedEntity.setPersonTenants(new HashSet<>());

        User user = savedEntity.getUser();
        Role adminRole = new Role();
        adminRole.setId(1L);

        when(roleRepository.findByName(anyString())).thenReturn(Optional.of(adminRole));

        // Act
        domainService.completeAccountSetup(savedEntity);

        // Assert
        assertEquals(savedEntity.getId(), user.getPersonId());
        assertFalse(user.getUserAccounts().isEmpty());
        assertFalse(savedEntity.getPersonTenants().isEmpty());
        verify(userRepository).save(user);
        verify(userTenantRoleService).assignRolesToUserAndTenant(eq(user), eq(savedEntity.getTenant()), anySet());
    }
}
