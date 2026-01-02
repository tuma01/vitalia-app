package com.amachi.app.vitalia.tenantadmin.service.impl;

import com.amachi.app.vitalia.authentication.entity.Role;
import com.amachi.app.vitalia.authentication.entity.User;
import com.amachi.app.vitalia.authentication.repository.RoleRepository;
import com.amachi.app.vitalia.authentication.repository.UserRepository;
import com.amachi.app.vitalia.authentication.service.UserTenantRoleService;
import com.amachi.app.vitalia.common.AbstractUnitTest;
import com.amachi.app.vitalia.geography.address.dto.AddressDto;
import com.amachi.app.vitalia.geography.address.entity.Address;
import com.amachi.app.vitalia.geography.address.mapper.AddressMapper;
import com.amachi.app.vitalia.geography.address.service.impl.AddressServiceImpl;
import com.amachi.app.vitalia.tenant.repository.TenantRepository;
import com.amachi.app.vitalia.tenantadmin.dto.TenantAdminDto;
import com.amachi.app.vitalia.tenantadmin.entity.TenantAdmin;
import com.amachi.app.vitalia.tenantadmin.repository.TenantAdminRepository;
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
class TenantAdminDomainServiceImplTest extends AbstractUnitTest {

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
                .set(field(TenantAdminDto::getTenant), Instancio.of(com.amachi.app.vitalia.tenant.dto.TenantDto.class)
                        .set(field(com.amachi.app.vitalia.tenant.dto.TenantDto::getAddress), addressDto)
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
                .set(field(TenantAdminDto::getTenant), Instancio.of(com.amachi.app.vitalia.tenant.dto.TenantDto.class)
                        .set(field(com.amachi.app.vitalia.tenant.dto.TenantDto::getAddress), addressDto)
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
                .set(field(TenantAdminDto::getTenant), Instancio.of(com.amachi.app.vitalia.tenant.dto.TenantDto.class)
                        .set(field(com.amachi.app.vitalia.tenant.dto.TenantDto::getAddress), addressDto)
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

    @ExtendWith(MockitoExtension.class)
    static
    class TenantAdminServiceImplTest extends AbstractUnitTest {

        @Mock
        private TenantAdminRepository tenantAdminRepository;

        @Mock
        private TenantRepository tenantRepository;

        @Mock
        private TenantAdminDomainServiceImpl tenantAdminDomainService;

        @InjectMocks
        private TenantAdminServiceImpl service;

        private static final String DATA_PATH = "data/tenantadmin/";

        @Test
        void createTenantAdmin_WhenValidEntity_ThenSaveAndReturn() {
            // Arrange
            // 1. Load Entity from JSON (Simulating data passed from
            // Controller->Mapper->Service)
            TenantAdmin entity = loadJson(DATA_PATH + "tenantadmin-entity.json", TenantAdmin.class);

            // 2. Mock Dependencies
            // Simulate existing tenant lookup (since entity has ID=1, service checks if it
            // exists)
            when(tenantRepository.findById(1L)).thenReturn(Optional.of(entity.getTenant()));

            // Simulate Save
            when(tenantAdminRepository.save(any(TenantAdmin.class))).thenReturn(entity);

            // Act
            TenantAdmin result = service.create(entity);

            // Assert
            assertThat(result).isNotNull();
            assertThat(result.getNombre()).isEqualTo("Felix");
            assertThat(result.getTenant().getId()).isEqualTo(1L);

            // Verify Interactions
            verify(tenantRepository).findById(1L); // Verified existing tenant attachment logic
            verify(tenantAdminRepository).save(entity);
            verify(tenantAdminDomainService).completeAccountSetup(entity); // Verified account setup call
        }
    }
}
