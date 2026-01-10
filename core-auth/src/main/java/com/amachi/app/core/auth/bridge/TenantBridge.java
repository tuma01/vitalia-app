package com.amachi.app.core.auth.bridge;

import com.amachi.app.core.domain.tenant.entity.Tenant;

public interface TenantBridge {
    Tenant findByCode(String tenantCode);
}
