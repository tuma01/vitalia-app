package com.amachi.app.vitalia.management.tenantadmin.service.impl; // Convention: test in same package as impl or generic service package (User requested service)

import com.amachi.app.core.common.test.util.AbstractTestSupport;
import com.amachi.app.core.domain.tenant.repository.TenantRepository;
import com.amachi.app.vitalia.management.tenantadmin.entity.TenantAdmin;
import com.amachi.app.vitalia.management.tenantadmin.repository.TenantAdminRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TenantAdminServiceImplTest extends AbstractTestSupport {

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
