package com.amachi.app.vitalia.management.tenant.controller;

import com.amachi.app.core.domain.tenant.entity.Tenant;
import com.amachi.app.core.common.test.util.AbstractTestSupport;
import com.amachi.app.core.geography.address.mapper.AddressMapper;
import com.amachi.app.core.geography.address.service.impl.AddressServiceImpl;
import com.amachi.app.core.domain.tenant.dto.TenantDto;
import com.amachi.app.core.domain.tenant.mapper.TenantMapper;
import com.amachi.app.vitalia.management.tenant.service.impl.TenantDomainServiceImpl;
import com.amachi.app.vitalia.management.tenant.service.impl.TenantServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class TenantControllerTest extends AbstractTestSupport {

    private MockMvc mockMvc;

    @Mock
    private TenantServiceImpl service;

    @Mock
    private TenantMapper mapper;

    @Mock
    private AddressServiceImpl addressService;

    @Mock
    private AddressMapper addressMapper;

    @Mock
    private TenantDomainServiceImpl tenantDomainService;

    @InjectMocks
    private TenantController controller;

    private static final String DATA_PATH = "data/tenant/";

    @BeforeEach
    void setUp() {
        // Initialize MockMvc or other setup if needed
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void createTenant_WhenValidJson_ThenReturn201() throws Exception {
        // Arrange
        TenantDto requestDto = loadJson(DATA_PATH + "tenant-request-create.json", TenantDto.class);
        TenantDto responseDto = loadJson(DATA_PATH + "tenant-response.json", TenantDto.class);
        Tenant entity = loadJson(DATA_PATH + "tenant-entity.json", Tenant.class);

        when(mapper.toEntity(any())).thenReturn(entity);
        when(service.create(any())).thenReturn(entity);
        when(mapper.toDto(any())).thenReturn(responseDto);

        // Act & Assert
        mockMvc.perform(post("/super-admin/tenants")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(responseDto)));

        // Verify interactions
        verify(service).create(any());
        verify(tenantDomainService).handleTenantAddress(any(), any());

    }
}