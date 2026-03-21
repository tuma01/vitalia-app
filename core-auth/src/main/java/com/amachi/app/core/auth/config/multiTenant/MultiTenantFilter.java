package com.amachi.app.core.auth.config.multiTenant;

import com.amachi.app.core.auth.exception.AppSecurityException;
import com.amachi.app.core.common.context.TenantContext;
import com.amachi.app.core.common.i18n.Translator;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
@Slf4j
public class MultiTenantFilter extends OncePerRequestFilter {

    private final TenantResolver tenantResolver;
    private final TenantCache tenantCache;
    private final Translator translator;

    public MultiTenantFilter(TenantResolver tenantResolver, 
                              @Lazy TenantCache tenantCache, 
                              Translator translator) {
        this.tenantResolver = tenantResolver;
        this.tenantCache = tenantCache;
        this.translator = translator;
    }

    @Value("${spring.profiles.active:dev}")
    private String activeProfile;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        try {
            // 1️⃣ Resolver tenantCode (fuente única)
            String tenantCode = tenantResolver.resolveTenant(request);

            // 2️⃣ Establecer Contexto (Crucial para evitar bucles en TenantFilterAspect)
            TenantContext.setTenantCode(tenantCode);
            try {
                com.amachi.app.core.domain.tenant.entity.Tenant tenant = tenantCache.getTenant(tenantCode);
                if (tenant != null) {
                    TenantContext.setTenantId(tenant.getId());
                }
            } catch (Exception e) {
                log.warn("⚠️ No se pudo cargar el ID del tenant [{}] en el contexto inicial", tenantCode);
            }

            // 3️⃣ Propagar a request
            request.setAttribute("tenantCode", tenantCode);

            filterChain.doFilter(request, response);

        } catch (AppSecurityException ex) {
            log.error("❌ Error de tenant: {}", ex.getArgs()[0]);
            writeErrorResponse(ex, request, response);

        } catch (Exception ex) {
            log.error("❌ Error inesperado resolviendo tenant", ex);
            writeGenericError(request, response);
        } finally {
            // 🧹 LIMPIEZA ABSOLUTA: Evita fugas entre hilos del pool
            TenantContext.clear();
            log.trace("🧹 TenantContext cleared");
        }
    }

    // ------------------------------------------------------
    // JSON de error estándar
    // ------------------------------------------------------
    private void writeErrorResponse(AppSecurityException ex,
                                    HttpServletRequest request,
                                    HttpServletResponse response)
            throws IOException {

        UUID traceId = UUID.randomUUID();

        String userMessage = Translator.toLocale(ex.getKey(), ex.getArgs());
        String devMessage = Translator.toLocale(ex.getKey() + ".dev", ex.getArgs());

        String json = String.format("""
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
                devMessage,
                ex.getArgs()[0],
                traceId,
                request.getRequestURI(),
                java.time.Instant.now()
        );

        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.setContentType("application/json");
        response.getWriter().write(json);
    }

    private void writeGenericError(HttpServletRequest request,
                                   HttpServletResponse response) throws IOException {

        String json = """
        {
          "success": false,
          "status": 500,
          "errors": [{
            "category": "GENERAL",
            "code": "INTERNAL_ERROR",
            "userMessage": "Ocurrió un error inesperado",
            "developerMessage": "Unexpected error processing tenant resolution"
          }],
          "path": "%s",
          "timestamp": "%s"
        }
        """.formatted(request.getRequestURI(), java.time.Instant.now());

        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        response.setContentType("application/json");
        response.getWriter().write(json);
    }
}

