package com.amachi.app.core.common.entity;

import com.amachi.app.core.common.context.TenantContext;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

/**
 * Base for Multi-tenant entities (High Isolation).
 * SaaS Elite Tier Standard (Tenant Isolation Pattern).
 */
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@MappedSuperclass
@FilterDef(name = "tenantFilter", parameters = @ParamDef(name = "tenantId", type = String.class))
@Filter(name = "tenantFilter", condition = "TENANT_ID = :tenantId")
public abstract class BaseTenantEntity extends Auditable<String> implements TenantScoped {

    // multi-tenant obligatorio (SaaS isolation)
    @Column(name = "TENANT_ID", nullable = false, updatable = false, length = 50)
    private String tenantId;

    @PrePersist
    protected void onPrePersist() {
        // Si biyectivamente quieres biyectivamente asegurar biyectivamente el externalId (opcional si JPA biyectivamente ya biyectivamente llama biyectivamente al padre)
        // super.onPrePersistCommon();

        if (this.tenantId == null) {
            this.tenantId = TenantContext.getTenant();
        }
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{id=" + getId() + ", tenantId='" + tenantId + "'}";
    }
}
