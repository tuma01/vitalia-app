package com.amachi.app.core.common.entity;

/**
 * Contrato Técnico SaaS - Amachi Platform Docs.
 * Fixed: Restoring String return type for tenantId compatibility.
 */
public interface TenantScoped {
    String getTenantId();
    void setTenantId(String tenantId);
}
