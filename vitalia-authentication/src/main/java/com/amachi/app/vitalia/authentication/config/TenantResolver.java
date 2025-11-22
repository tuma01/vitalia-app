package com.amachi.app.vitalia.authentication.config;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TenantResolver {

    private final AppBootstrapProperties appBootstrapProperties;

    public String resolveTenant(HttpServletRequest request) {
        // 1️⃣ Intentar obtener tenantCode del header fallback
        String tenantCode = request.getHeader(appBootstrapProperties.getTenant().getTenantLocal().getFallbackHeader());

        // 2️⃣ Si no existe, intentar parámetro
        if (tenantCode == null || tenantCode.isBlank()) {
            tenantCode = request.getParameter("tenantCode");
        }

        // 3️⃣ Si sigue sin existir, intentar subdominio
        if (tenantCode == null || tenantCode.isBlank()) {
            String host = request.getServerName();
            if (host != null && host.contains(".")) {
                tenantCode = host.split("\\.")[0]; // subdominio como tenantCode
            }
        }

        // 4️⃣ Finalmente, usar tenant por defecto si está permitido
        if ((tenantCode == null || tenantCode.isBlank()) && appBootstrapProperties.getTenant().getTenantLocal().isAllowLocal()) {
            tenantCode = appBootstrapProperties.getTenant().getTenantLocal().getCode();
        }
        return tenantCode;
    }
}
