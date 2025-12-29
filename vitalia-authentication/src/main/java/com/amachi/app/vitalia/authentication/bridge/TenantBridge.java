package com.amachi.app.vitalia.authentication.bridge;

import com.amachi.app.vitalia.common.entity.Tenant;
import com.amachi.app.vitalia.common.enums.PersonType;

public interface TenantBridge {
    Tenant findByCode(String tenantCode);
}
