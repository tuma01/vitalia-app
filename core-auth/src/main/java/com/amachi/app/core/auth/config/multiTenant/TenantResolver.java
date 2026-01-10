package com.amachi.app.core.auth.config.multiTenant;

import com.amachi.app.core.domain.config.TenantFactory;
import com.amachi.app.core.domain.tenant.entity.Tenant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;

@Component
@RequiredArgsConstructor
@Slf4j
public class TenantResolver {

    private final TenantFactory tenantFactory;

    public String resolveTenant(HttpServletRequest request) {

        TenantFactory.TenantInfo local = tenantFactory.getLocalTenant();

        String tenantCode = null;

        // 1️⃣ SUBDOMINIO
        String host = request.getServerName();
        if (host != null && host.contains(".")) {
            String sub = host.split("\\.")[0];
            if (tenantFactory.exists(sub)) {
                log.debug("Tenant resuelto desde subdominio: {}", sub);
                return sub;
            }
        }

        // 2️⃣ HEADER FALLBACK
        String header = local.fallbackHeader();
        if (header != null && !header.isBlank()) {
            tenantCode = request.getHeader(header);
            if (tenantCode != null && !tenantCode.isBlank()) {
                log.debug("Tenant resuelto desde header '{}': {}", header, tenantCode);
                return tenantCode;
            }
        }

        // 3️⃣ PARÁMETRO
        tenantCode = request.getParameter("tenantCode");
        if (tenantCode != null && !tenantCode.isBlank()) {
            log.debug("Tenant resuelto desde parámetro tenantCode: {}", tenantCode);
            return tenantCode;
        }

        // 4️⃣ LOCAL DEFAULT
        if (local != null) {
            log.debug("Tenant resuelto por fallback local: {}", local.code());
            return local.code();
        }

        throw new RuntimeException("No se pudo resolver tenantCode");
    }

    public TenantFactory.TenantInfo getTenantInfo(String tenantCode) {
        return tenantFactory.getTenant(tenantCode);
    }

    public TenantFactory.TenantInfo getGlobalTenant() {
        return tenantFactory.getGlobalTenant();
    }

    public TenantFactory.TenantInfo getLocalTenant() {
        return tenantFactory.getLocalTenant();
    }
}
