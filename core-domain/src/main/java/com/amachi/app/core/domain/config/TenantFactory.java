package com.amachi.app.core.domain.config;

import com.amachi.app.core.domain.tenant.enums.TenantType;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class TenantFactory {

    private final AppBootstrapProperties props;

    @Getter
    private TenantInfo globalTenant;

    @Getter
    private TenantInfo localTenant;

    private final Map<String, TenantInfo> tenantMap = new HashMap<>();

    public TenantFactory(AppBootstrapProperties props) {
        this.props = props;
    }

    @PostConstruct
    public void init() {
        // -----------------------------
        // 1️⃣ GLOBAL TENANT
        // -----------------------------
        var g = props.getTenant().getTenantGlobal();
        globalTenant = new TenantInfo(
                g.getCode(),
                g.getName(),
                TenantType.valueOf(g.getType()),
                g.getDescription(),
                null
        );
        tenantMap.put(g.getCode(), globalTenant);

        // -----------------------------
        // 2️⃣ LOCAL TENANT
        // -----------------------------
        var l = props.getTenant().getTenantLocal();
        localTenant = new TenantInfo(
                l.getCode(),
                l.getName(),
                TenantType.valueOf(l.getType()),
                null,
                l.getFallbackHeader()
        );
        tenantMap.put(l.getCode(), localTenant);

        log.info("📦 TenantFactory inicializado con tenants: {}", tenantMap.keySet());
    }

    // -----------------------------
    // Búsqueda y validación
    // -----------------------------

    /**
     * Devuelve el tenant por código, lanza excepción si no existe.
     */
    public TenantInfo getTenant(String code) {
        TenantInfo tenant = tenantMap.get(code);
        if (tenant == null) {
            throw new RuntimeException("Tenant no encontrado: " + code);
        }
        return tenant;
    }

    /**
     * Devuelve true si el tenant existe.
     */
    public boolean exists(String code) {
        return tenantMap.containsKey(code);
    }

    /**
     * Devuelve un mapa inmutable de todos los tenants.
     */
    public Map<String, TenantInfo> getAll() {
        return Collections.unmodifiableMap(tenantMap);
    }

    /**
     * Record para encapsular la información de cada tenant.
     */
    public record TenantInfo(
            String code,
            String name,
            TenantType type,
            String description,
            String fallbackHeader
    ) {}
}

