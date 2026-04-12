package com.amachi.app.core.management.tenantadmin.event;

import com.amachi.app.core.common.event.DomainEvent;
import com.amachi.app.core.common.enums.TenantAdminLevel;
import lombok.Getter;

/**
 * Event published when a new Tenant Admin is created.
 */
@Getter
public class TenantAdminCreatedEvent extends DomainEvent {

    private final Long adminId;
    private final String email;
    private final String tenantCode;
    private final TenantAdminLevel adminLevel;

    public TenantAdminCreatedEvent(Long adminId, String email, String tenantCode, TenantAdminLevel adminLevel) {
        super();
        this.adminId = adminId;
        this.email = email;
        this.tenantCode = tenantCode;
        this.adminLevel = adminLevel;
    }
}
