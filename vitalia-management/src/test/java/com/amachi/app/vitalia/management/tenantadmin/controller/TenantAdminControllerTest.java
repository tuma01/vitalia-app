package com.amachi.app.vitalia.management.tenantadmin.controller;

import com.amachi.app.core.common.test.util.AbstractTestSupport;
import com.amachi.app.core.geography.address.mapper.AddressMapper;
import com.amachi.app.core.geography.address.service.impl.AddressServiceImpl;
import com.amachi.app.vitalia.management.tenantadmin.dto.TenantAdminDto;
import com.amachi.app.vitalia.management.tenantadmin.entity.TenantAdmin;
import com.amachi.app.vitalia.management.tenantadmin.mapper.TenantAdminMapper;
import com.amachi.app.vitalia.management.tenantadmin.service.impl.TenantAdminDomainServiceImpl;
import com.amachi.app.vitalia.management.tenantadmin.service.impl.TenantAdminServiceImpl;
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
class TenantAdminControllerTest extends AbstractTestSupport {

    private MockMvc mockMvc;

    @Mock
    private TenantAdminServiceImpl service;

    @Mock
    private TenantAdminMapper mapper;

    @Mock
    private AddressServiceImpl addressService;

    @Mock
    private AddressMapper addressMapper;

    @Mock
    private TenantAdminDomainServiceImpl tenantAdminDomainService;

    @InjectMocks
    private TenantAdminController controller;

    private static final String DATA_PATH = "data/tenantadmin/";

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void createTenantAdmin_WhenValidJson_ThenReturn201() throws Exception {
        // Arrange
        TenantAdminDto requestDto = loadJson(DATA_PATH + "tenantadmin-request-create.json", TenantAdminDto.class);

        TenantAdminDto responseDto = loadJson(DATA_PATH + "tenantadmin-response.json", TenantAdminDto.class);

        TenantAdmin entity = loadJson(DATA_PATH + "tenantadmin-entity.json", TenantAdmin.class);

        when(mapper.toEntity(any())).thenReturn(entity);
        when(service.create(any())).thenReturn(entity);
        when(mapper.toDto(any())).thenReturn(responseDto);
        when(tenantAdminDomainService.enrichTenantAdminDto(any(), any()))
                .thenReturn(responseDto);

        // Act & Assert
        mockMvc.perform(post("/super-admin/tenant-admins")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(responseDto)));

        // Verify orchestration
        verify(service).create(any());
        verify(tenantAdminDomainService).handleTenantAddress(any(), any());
        verify(tenantAdminDomainService).encodePasswordIfNeeded(any());
        verify(tenantAdminDomainService).enrichTenantAdminDto(any(), any());
    }
}
