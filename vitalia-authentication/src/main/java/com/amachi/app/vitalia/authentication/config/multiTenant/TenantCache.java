package com.amachi.app.vitalia.authentication.config.multiTenant;

import com.amachi.app.vitalia.authentication.bridge.TenantBridge;
import com.amachi.app.vitalia.authentication.exception.AppSecurityException;
import com.amachi.app.vitalia.common.entity.Tenant;
import com.amachi.app.vitalia.common.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
@Slf4j
public class TenantCache {

    private final TenantBridge tenantBridge;
    private final Map<String, Tenant> tenantMap = new ConcurrentHashMap<>();

    public Tenant getTenant(String tenantCode) {
        return tenantMap.computeIfAbsent(tenantCode, this::loadTenantFromDb);
    }

    /**
     * Carga un Tenant desde la base de datos.
     *
     * @param tenantCode Código del tenant.
     * @return Tenant encontrado.
     */
    private Tenant loadTenantFromDb(String tenantCode) {
        log.info("🔍 Cargando Tenant [{}] desde DB", tenantCode);
        return tenantBridge.findByCode(tenantCode);
    }


    public void clearCache() {
        log.info("🗑 Limpiando cache de Tenants");
        tenantMap.clear();
    }

    public void evictTenant(String tenantCode) {
        log.info("🗑 Eliminando tenant [{}] del cache", tenantCode);
        tenantMap.remove(tenantCode);
    }
}
