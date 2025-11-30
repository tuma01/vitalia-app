package com.amachi.app.vitalia.authentication.config.security;

import com.amachi.app.vitalia.authentication.config.multiTenant.TenantCache;
import com.amachi.app.vitalia.authentication.service.JwtService;
import com.amachi.app.vitalia.authentication.service.impl.JwtServiceImpl;
import com.amachi.app.vitalia.common.api.ApiResponse;
import com.amachi.app.vitalia.common.entity.Tenant;
import com.amachi.app.vitalia.common.error.ErrorCode;
import com.amachi.app.vitalia.common.error.ErrorDetail;
import com.amachi.app.vitalia.common.http.HttpStatusCode;
import com.amachi.app.vitalia.common.i18n.Translator;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final TenantCache tenantCache;
    private final UserDetailsService userDetailsService;
    private final Translator translator;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            try {
                String token = authHeader.substring(7);

                // 🔹 Validar token (firma y expiración)
                jwtService.validateToken(token);

                // 🔹 Extraer datos del token
                String username = jwtService.extractUsername(token);
                String tenantCode = jwtService.extractTenantCode(token);

                // 🔹 Validar tenant desde cache
                Tenant tenant = tenantCache.getTenant(tenantCode);
                if (tenant == null) {
                    throw new RuntimeException("Tenant not found for code: " + tenantCode);
                }

                // 🔹 Cargar usuario
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                // 🔹 Crear autenticación
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);

            } catch (JwtServiceImpl.TokenException ex) {
                log.warn("❌ Invalid JWT: {}", ex.getMessage());
                writeErrorResponse(response, ErrorCode.SEC_INVALID_TOKEN,
                        Translator.toLocale("security.invalid.token", new Object[]{ex.getMessage()}), request.getRequestURI());
                return;

            } catch (Exception ex) {
                log.error("🚫 Error en JWT authentication: {}", ex.getMessage());
                writeErrorResponse(response, ErrorCode.SEC_AUTHENTICATION_ERROR,
                        Translator.toLocale("security.auth.failed", new Object[]{ex.getMessage()}), request.getRequestURI());
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private void writeErrorResponse(HttpServletResponse response, ErrorCode errorCode, String userMessage, String path) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        // Construir el detalle del error con tu helper estándar
        ErrorDetail errorDetail = ErrorDetail.from(
                errorCode,
                userMessage,
                null,               // campo afectado (no aplica en este caso)
                Map.of("path", path)
        );
        // Crear la respuesta de error usando tu método ApiResponse.error(...)
        ApiResponse<Object> apiResponse = ApiResponse.error(
                HttpStatusCode.UNAUTHORIZED,
                errorDetail,
                path
        );
        // Serializar a JSON
        new ObjectMapper().findAndRegisterModules().writeValue(response.getWriter(), apiResponse);
    }
}
