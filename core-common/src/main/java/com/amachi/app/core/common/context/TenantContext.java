package com.amachi.app.core.common.context;

import java.util.Optional;

/**
 * Módulo: vitalia-security
 *
 * Implementa el contexto de ThreadLocal para el ID del Tenant (DSQ Model).
 * Permite acceder al Tenant ID desde cualquier parte de la capa de servicio/persistencia
 * sin pasarlo explícitamente como argumento.
 */
public class TenantContext {

    private static final ThreadLocal<Long> TENANT_ID_CONTEXT = new ThreadLocal<>();

    public static void setTenantId(Long tenantId) {
        TENANT_ID_CONTEXT.set(tenantId);
    }

    public static Optional<Long> getTenantId() {
        return Optional.ofNullable(TENANT_ID_CONTEXT.get());
    }

    public static void clear() {
        TENANT_ID_CONTEXT.remove();
    }
}
