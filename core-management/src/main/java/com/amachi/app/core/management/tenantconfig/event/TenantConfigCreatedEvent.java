package com.amachi.app.core.management.tenantconfig.event;

import com.amachi.app.core.common.event.DomainEvent;
import com.amachi.app.core.common.enums.SubscriptionPlan;
import lombok.Getter;

/**
 * Event published when a new Tenant Configuration is created.
 */
@Getter
public class TenantConfigCreatedEvent extends DomainEvent {

    private final Long configId;
    private final String tenantCode;
    private final SubscriptionPlan subscriptionPlan;

    public TenantConfigCreatedEvent(Long configId, String tenantCode, SubscriptionPlan subscriptionPlan) {
        super();
        this.configId = configId;
        this.tenantCode = tenantCode;
        this.subscriptionPlan = subscriptionPlan;
    }
}
