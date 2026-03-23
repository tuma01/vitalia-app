package com.amachi.app.core.auth.config.security;

import com.amachi.app.core.auth.config.multiTenant.TenantCache;
import com.amachi.app.core.auth.service.JwtService;
import com.amachi.app.core.auth.service.TokenService;
import com.amachi.app.core.auth.service.impl.JwtServiceImpl;
import com.amachi.app.core.common.context.TenantContext;
import com.amachi.app.core.common.api.ApiResponse;
import com.amachi.app.core.domain.tenant.entity.Tenant;
import com.amachi.app.core.common.error.ErrorCode;
import com.amachi.app.core.common.error.ErrorDetail;
import com.amachi.app.core.common.http.HttpStatusCode;
import com.amachi.app.core.common.i18n.Translator;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

        private final JwtService jwtService;
        private final TenantCache tenantCache;
        private final UserDetailsService userDetailsService;
        private final TokenService tokenService;

        // Endpoints que no requieren validación de token (Públicos)
        // Deben coincidir con SecurityConfig.java
        private static final List<String> GLOBAL_ENDPOINTS = List.of(
                        "/auth/",
                        "/account/activate",
                        "/account/request-reset-password",
                        "/account/reset-password",
                        "/public/",
                        "/v3/api-docs",
                        "/swagger-ui",
                        "/tenants",
                        "/themes/tenant/");

        @Override
        protected void doFilterInternal(HttpServletRequest request,
                        HttpServletResponse response,
                        FilterChain filterChain) throws ServletException, IOException {

                String servletPath = request.getServletPath();
                String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

                boolean isGlobalEndpoint = GLOBAL_ENDPOINTS.stream().anyMatch(servletPath::startsWith);

                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                        String token = authHeader.substring(7);

                        try {
                                // 🔹 Validar token (firma y expiración)
                                jwtService.validateToken(token);

                                // 🔹 Validar blacklist
                                if (tokenService.isTokenBlacklisted(token)) {
                                        log.warn("⛔ Token blacklisted detected: {}",
                                                        token.substring(0, Math.min(10, token.length())) + "...");
                                        throw new com.amachi.app.core.auth.exception.AppSecurityException(
                                                        ErrorCode.SEC_INVALID_TOKEN,
                                                        "security.token.blacklisted");
                                }

                                // 🔹 Extraer datos del JWT
                                String username = jwtService.extractUsername(token);
                                String tenantCode = jwtService.extractTenantCode(token);
                                List<String> roles = jwtService.extractRoles(token);

                                // 🔹 Validación de tenant
                                boolean isSuperAdmin = roles.contains("ROLE_SUPER_ADMIN");
                                if (!isGlobalEndpoint && !isSuperAdmin) {
                                        // Anti-Spoofing: El tenant del Token debe coincidir con el de la request
                                        String requestTenantCode = (String) request.getAttribute("tenantCode");
                                        if (requestTenantCode != null && !requestTenantCode.equals(tenantCode)
                                                        && !"GLOBAL".equalsIgnoreCase(requestTenantCode)) {
                                                log.error("🚨 POTENTIAL SPOOFING ATTEMPT: Token Tenant '{}' vs Request Tenant '{}' on path '{}'",
                                                                tenantCode, requestTenantCode, servletPath);
                                                throw new RuntimeException("Security Error: Token tenant mismatch");
                                        }

                                        Tenant tenant = tenantCache.getTenant(tenantCode);
                                        if (tenant == null) {
                                                throw new RuntimeException("Tenant not found for code: " + tenantCode);
                                        }
                                }

                                // 🔹 Cargar usuario y setear contexto
                                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                                List<GrantedAuthority> authorities = roles.stream()
                                                .map(SimpleGrantedAuthority::new)
                                                .collect(Collectors.toUnmodifiableList());

                                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                                                userDetails, null, authorities);
                                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                                SecurityContextHolder.getContext().setAuthentication(authToken);

                                // 🔹 ESTABLECER CONTEXTO DE TENANT (THREAD-LOCAL)
                                try {
                                        TenantContext.setTenantCode(tenantCode);
                                        Tenant tenant = tenantCache.getTenant(tenantCode);
                                        if (tenant != null) {
                                                TenantContext.setTenantId(tenant.getId());
                                                log.trace("🏗️ TenantContext initialized with ID: {} and Code: {}", 
                                                        tenant.getId(), tenantCode);
                                        }
                                } catch (Exception e) {
                                        log.warn("⚠️ Could not set TenantContext for code {}: {}", tenantCode, e.getMessage());
                                }

                                log.debug("✅ JWT valid for user '{}' on path '{}', tenant: {}",
                                                username, servletPath, tenantCode);

                        } catch (JwtServiceImpl.TokenException ex) {
                                log.warn("❌ Invalid JWT for path {}: {}", servletPath, ex.getMessage());
                                writeErrorResponse(response, ErrorCode.SEC_INVALID_TOKEN,
                                                Translator.toLocale("security.invalid.token", new Object[] { ex.getMessage() }),
                                                servletPath);
                                return;
                        } catch (com.amachi.app.core.auth.exception.AppSecurityException ex) {
                                log.warn("⛔ Security Exception for path {}: {}", servletPath, ex.getMessage());
                                writeErrorResponse(response, ex.getErrorCode(), Translator.toLocale(ex.getKey(), null), servletPath);
                                return;
                        } catch (Exception ex) {
                                log.error("🚫 JWT authentication error for path {}: {}", servletPath, ex.getMessage());
                                writeErrorResponse(response, ErrorCode.SEC_AUTHENTICATION_ERROR,
                                                Translator.toLocale("security.auth.failed", new Object[] { ex.getMessage() }),
                                                servletPath);
                                return;
                        }

                } else if (!isGlobalEndpoint) {
                        // 🔹 Endpoint protegido sin token
                        writeErrorResponse(response, ErrorCode.SEC_AUTHENTICATION_ERROR,
                                        Translator.toLocale("security.auth.missing.token", null), servletPath);
                        return;
                }

                try {
                        filterChain.doFilter(request, response);
                } finally {
                        TenantContext.clear();
                }
        }

        private void writeErrorResponse(HttpServletResponse response,
                        ErrorCode errorCode,
                        String userMessage,
                        String path) throws IOException {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");

                ErrorDetail errorDetail = ErrorDetail.from(
                                errorCode,
                                userMessage,
                                null,
                                Map.of("path", path));

                ApiResponse<Object> apiResponse = ApiResponse.error(
                                HttpStatusCode.UNAUTHORIZED,
                                errorDetail,
                                path);

                new ObjectMapper().findAndRegisterModules().writeValue(response.getWriter(), apiResponse);
        }
}
