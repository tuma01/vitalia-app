package com.amachi.app.vitalia.superadmin.service;

import com.amachi.app.vitalia.superadmin.dto.TenantCreateRequest;
import com.amachi.app.vitalia.superadmin.dto.TenantResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TenantManagementService {

    TenantResponse createTenantWithAdmin(TenantCreateRequest request);

    Page<TenantResponse> listTenants(Pageable pageable);

    TenantResponse getTenant(Long id);

    TenantResponse updateTenant(Long id, com.amachi.app.vitalia.superadmin.dto.TenantUpdateRequest update);

    void enableTenant(Long id);

    void disableTenant(Long id);

    void resetAdminPassword(Long tenantId, String newPassword);

    void softDeleteTenant(Long id);
}

