package com.amachi.app.vitalia.authentication.bridge;

import com.amachi.app.vitalia.common.enums.PersonType;

public interface PersonTenantBridge {
    Long create(Long personId, String tenantCode, PersonType personType);
}
