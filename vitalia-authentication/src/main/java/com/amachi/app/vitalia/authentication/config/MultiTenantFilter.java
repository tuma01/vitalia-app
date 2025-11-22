package com.amachi.app.vitalia.authentication.config;

import com.amachi.app.vitalia.authentication.exception.AppSecurityException;
import com.amachi.app.vitalia.authentication.repository.TenantRepository;
import com.amachi.app.vitalia.common.entity.Tenant;
import com.amachi.app.vitalia.common.error.ErrorCode;
import com.amachi.app.vitalia.common.i18n.Translator;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
//import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class MultiTenantFilter extends OncePerRequestFilter {

    private final TenantRepository tenantRepository;
    private final AppBootstrapProperties tenantProperties;
    private final Translator translator;

    @Value("${spring.profiles.active:dev}")
    private String activeProfile;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // 1️⃣ Obtener tenantCode de header o parámetro
        String tenantCode = extractTenantCode(request);
        if ("dev".equalsIgnoreCase(activeProfile)) {
            handleDev(request, response, filterChain);
        } else {
            handleProd(request, response, filterChain);
        }
    }

    /**
     * Desarrollo: usa tenant por defecto o header, sin validar DB.
     */
    private void handleDev(HttpServletRequest request,
                           HttpServletResponse response,
                           FilterChain filterChain) throws ServletException, IOException {

        String tenantCode = Optional.ofNullable(request.getHeader(tenantProperties.getTenant().getTenantLocal().getFallbackHeader()))
                .orElse(request.getParameter("tenantCode"));

        if (tenantCode == null || tenantCode.isBlank()) {
            tenantCode = tenantProperties.getTenant().getTenantLocal().getCode(); // tenant por defecto
            log.info("ℹ️ No se recibió tenantCode, usando tenant por defecto [{}]", tenantCode);
        }

        request.setAttribute("tenantCode", tenantCode);
        filterChain.doFilter(request, response);
    }

    /**
     * Producción: valida tenant en DB, soporta subdominio y fallbackHeader.
     */
    private void handleProd(HttpServletRequest request,
                            HttpServletResponse response,
                            FilterChain filterChain) throws ServletException, IOException {

        String tenantCode = extractTenantCode(request);

        try {
            final String finalTenantCode = tenantCode;

            Tenant tenant = tenantRepository.findByCode(finalTenantCode)
                    .orElseThrow(() -> {
                        log.warn("🚫 Tenant no encontrado: {}", finalTenantCode);
                        return new AppSecurityException(
                                ErrorCode.SEC_TENANT_NOT_FOUND,
                                "security.tenant.not_found",
                                finalTenantCode
                        );
                    });

            request.setAttribute("tenantCode", tenant.getCode());
            filterChain.doFilter(request, response);

        } catch (AppSecurityException ex) {
            log.error("🚫 Tenant inválido en filtro: {}", ex.getArgs()[0]);
            writeErrorResponse(ex, request, response);
        }
    }

    /**
     * Extrae el tenantCode desde subdominio o fallbackHeader.
     */
    private String extractTenantCode(HttpServletRequest request) {

        // 1️⃣ Intentar extraer subdominio si no estamos en local
        String tenantCode = null;
        String serverName = request.getServerName(); // ej: hospitala.vitalia.bo

        if (!tenantProperties.getTenant().getTenantLocal().isAllowLocal()) {
            tenantCode = extractFromSubdomain(serverName);
        }

        // 2️⃣ Intentar fallbackHeader
        if (tenantCode == null || tenantCode.isBlank()) {
            tenantCode = request.getHeader(tenantProperties.getTenant().getTenantLocal().getFallbackHeader());
        }

        // 3️⃣ Usar parámetro opcional
        if (tenantCode == null || tenantCode.isBlank()) {
            tenantCode = request.getParameter("tenantCode");
        }

        // 4️⃣ Usar tenant por defecto (solo si allowLocal=true)
        if ((tenantCode == null || tenantCode.isBlank()) && tenantProperties.getTenant().getTenantLocal().isAllowLocal()) {
            tenantCode = tenantProperties.getTenant().getTenantLocal().getCode();
            log.info("ℹ️ No se recibió tenantCode, usando tenant por defecto [{}]", tenantCode);
        }

        if (tenantCode == null || tenantCode.isBlank()) {
            throw new AppSecurityException(
                    ErrorCode.SEC_TENANT_NOT_FOUND,
                    "security.tenant.not_found",
                    "NO_TENANT"
            );
        }

        return tenantCode;
    }

    /**
     * Extrae tenantCode del subdominio.
     * Ej: hospitala.vitalia.bo → hospitala
     */
    private String extractFromSubdomain(String serverName) {
        String baseDomain = tenantProperties.getTenant().getTenantLocal().getDefaultDomain(); // ej: vitalia.bo
        if (serverName != null && serverName.endsWith(baseDomain)) {
            String[] parts = serverName.split("\\.");
            if (parts.length > 2) { // subdominio existe
                return parts[0];
            }
        }
        return null;
    }

    /**
     * Construye y envía el JSON de error traducido.
     */
    private void writeErrorResponse(AppSecurityException ex,
                                    HttpServletRequest request,
                                    HttpServletResponse response) throws IOException {

        UUID traceId = UUID.randomUUID();

        String userMessage = Translator.toLocale(ex.getKey(), ex.getArgs());
        String developerMessage = Translator.toLocale(ex.getKey() + ".dev", ex.getArgs());

        String jsonResponse = String.format("""
                {
                  "success": false,
                  "status": 400,
                  "errors": [{
                    "category": "SECURITY",
                    "code": "%s",
                    "userMessage": "%s",
                    "developerMessage": "%s",
                    "details": {"tenantCode": "%s"},
                    "traceId": "%s"
                  }],
                  "path": "%s",
                  "timestamp": "%s"
                }
                """,
                ex.getErrorCode().getCode(),
                userMessage,
                developerMessage,
                ex.getArgs()[0],
                traceId,
                request.getRequestURI(),
                java.time.Instant.now()
        );

        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.setContentType("application/json");
        response.getWriter().write(jsonResponse);
        response.getWriter().flush();
    }
}
