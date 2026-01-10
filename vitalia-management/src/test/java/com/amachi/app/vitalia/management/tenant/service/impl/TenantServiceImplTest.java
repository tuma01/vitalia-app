package com.amachi.app.vitalia.management.tenant.service.impl;

import com.amachi.app.core.domain.tenant.entity.Tenant;
import com.amachi.app.core.common.exception.ResourceNotFoundException;
import com.amachi.app.core.common.test.util.AbstractTestSupport;
import com.amachi.app.core.domain.tenant.repository.TenantRepository;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.instancio.Select.field;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TenantServiceImplTest extends AbstractTestSupport {

    @Mock
    private TenantRepository tenantRepository;

    @InjectMocks
    private TenantServiceImpl service;

    @Test
    void create_WhenValidEntity_ThenReturnSavedEntity() {
        // Arrange
        Tenant entity = Instancio.of(Tenant.class)
                .set(field(Tenant::getAddressId), 1L) // Ensure addressId is present
                .create();

        when(tenantRepository.save(any(Tenant.class))).thenReturn(entity);

        // Act
        Tenant result = service.create(entity);

        // Assert
        assertNotNull(result);
        assertEquals(entity.getCode(), result.getCode());
        verify(tenantRepository).save(entity);
    }

    @Test
    void create_WhenEntityIsNull_ThenThrowException() {
        // Act & Assert
        assertThrows(NullPointerException.class, () -> service.create(null));
        verify(tenantRepository, never()).save(any());
    }

    @Test
    void getById_WhenExists_ThenReturnEntity() {
        // Arrange
        Long id = 1L;
        Tenant entity = Instancio.of(Tenant.class)
                .set(field(Tenant::getId), id)
                .create();

        when(tenantRepository.findById(id)).thenReturn(Optional.of(entity));

        // Act
        Tenant result = service.getById(id);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
    }

    @Test
    void getById_WhenNotExists_ThenThrowResourceNotFoundException() {
        // Arrange
        Long id = 999L;
        when(tenantRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> service.getById(id));
    }

    @Test
    void update_WhenExists_ThenReturnUpdatedEntity() {
        // Arrange
        Long id = 1L;
        Tenant entity = Instancio.of(Tenant.class)
                .set(field(Tenant::getId), id)
                .set(field(Tenant::getAddressId), 5L)
                .create();

        when(tenantRepository.findById(id)).thenReturn(Optional.of(entity));
        when(tenantRepository.save(any(Tenant.class))).thenReturn(entity);

        // Act
        Tenant result = service.update(id, entity);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(tenantRepository).save(entity);
    }

    @Test
    void update_WhenNotExists_ThenThrowResourceNotFoundException() {
        // Arrange
        Long id = 999L;
        Tenant entity = Instancio.create(Tenant.class);
        entity.setAddressId(1L); // Satisfy requireNonNull check

        when(tenantRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> service.update(id, entity));
    }

    @Test
    void delete_WhenExists_ThenDisableTenant() {
        // Arrange
        Long id = 1L;
        Tenant entity = Instancio.of(Tenant.class)
                .set(field(Tenant::getId), id)
                .set(field(Tenant::getIsActive), true)
                .create();

        when(tenantRepository.findById(id)).thenReturn(Optional.of(entity));

        // Act
        service.delete(id);

        // Assert
        assertFalse(entity.getIsActive()); // Verify soft delete logic
        verify(tenantRepository).save(entity);
    }

    @Test
    void enableTenant_WhenExists_ThenSetActiveTrue() {
        // Arrange
        Long id = 1L;
        Tenant entity = Instancio.of(Tenant.class)
                .set(field(Tenant::getId), id)
                .set(field(Tenant::getIsActive), false)
                .create();

        when(tenantRepository.findById(id)).thenReturn(Optional.of(entity));

        // Act
        service.enableTenant(id);

        // Assert
        assertTrue(entity.getIsActive());
        verify(tenantRepository).save(entity);
    }

    @Test
    void disableTenant_WhenExists_ThenSetActiveFalse() {
        // Arrange
        Long id = 1L;
        Tenant entity = Instancio.of(Tenant.class)
                .set(field(Tenant::getId), id)
                .set(field(Tenant::getIsActive), true)
                .create();

        when(tenantRepository.findById(id)).thenReturn(Optional.of(entity));

        // Act
        service.disableTenant(id);

        // Assert
        assertFalse(entity.getIsActive());
        verify(tenantRepository).save(entity);
    }
}
