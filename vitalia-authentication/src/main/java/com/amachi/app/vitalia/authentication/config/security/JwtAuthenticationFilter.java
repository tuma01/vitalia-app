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
import jakarta.validation.constraints.NotNull;
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

        // Endpoints que no requieren validación de tenant
        private static final List<String> GLOBAL_ENDPOINTS = List.of(
                        "/api/v1/auth/",
                        "/api/v1/public/",
                        "/api/v1/v3/api-docs",
                        "/api/v1/swagger-ui",
                        "/api/v1/super-admin/tenants");

        @Override
        protected void doFilterInternal(HttpServletRequest request,
                        HttpServletResponse response,
                        FilterChain filterChain) throws ServletException, IOException {

                String path = request.getRequestURI();
                String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

                boolean isGlobalEndpoint = GLOBAL_ENDPOINTS.stream().anyMatch(path::startsWith);

                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                        String token = authHeader.substring(7);

                        try {
                                // 🔹 Validar token (firma y expiración)
                                jwtService.validateToken(token);

                                // 🔹 Extraer datos del JWT
                                String username = jwtService.extractUsername(token); // "sub" en tu JWT
                                String tenantCode = jwtService.extractTenantCode(token); // "tenantCode" en tu JWT
                                List<String> roles = jwtService.extractRoles(token); // ["ROLE_SUPER_ADMIN"]

                                // 🔹 Validación de tenant solo para endpoints normales
                                if (!isGlobalEndpoint) {

                                        // 🚨 CRITICAL FIX: Anti-Spoofing
                                        // El tenant del Token debe coincidir con el tenant resuelto de la request
                                        // (header)
                                        String requestTenantCode = (String) request.getAttribute("tenantCode");
                                        if (requestTenantCode != null && !requestTenantCode.equals(tenantCode)) {
                                                log.error("🚨 POTENTIAL SPOOFING ATTEMPT: Token Tenant '{}' vs Request Tenant '{}' on path '{}'",
                                                                tenantCode, requestTenantCode, path);
                                                throw new RuntimeException("Security Error: Token tenant mismatch");
                                        }

                                        Tenant tenant = tenantCache.getTenant(tenantCode);
                                        if (tenant == null) {
                                                throw new RuntimeException("Tenant not found for code: " + tenantCode);
                                        }
                                }

                                // 🔹 Cargar usuario desde UserDetailsService
                                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                                log.debug("Authorities: {}", userDetails.getAuthorities());
                                // 🔹 Construir autenticación y setear en SecurityContext
                                List<GrantedAuthority> authorities = roles.stream()
                                                .map(SimpleGrantedAuthority::new)
                                                .collect(Collectors.toUnmodifiableList());
                                // .toList();

                                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                                                userDetails, null, authorities);
                                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                                SecurityContextHolder.getContext().setAuthentication(authToken);

                                log.debug("✅ JWT valid for user '{}' on path '{}', roles: {}, tenant: {}",
                                                username, path, roles, tenantCode);

                        } catch (JwtServiceImpl.TokenException ex) {
                                log.warn("❌ Invalid JWT for path {}: {}", path, ex.getMessage());
                                writeErrorResponse(response, ErrorCode.SEC_INVALID_TOKEN,
                                                Translator.toLocale("security.invalid.token",
                                                                new Object[] { ex.getMessage() }),
                                                path);
                                return;

                        } catch (Exception ex) {
                                log.error("🚫 JWT authentication error for path {}: {}", path, ex.getMessage());
                                writeErrorResponse(response, ErrorCode.SEC_AUTHENTICATION_ERROR,
                                                Translator.toLocale("security.auth.failed",
                                                                new Object[] { ex.getMessage() }),
                                                path);
                                return;
                        }

                } else if (!isGlobalEndpoint) {
                        // 🔹 Endpoint protegido sin token
                        writeErrorResponse(response, ErrorCode.SEC_AUTHENTICATION_ERROR,
                                        Translator.toLocale("security.auth.missing.token", null), path);
                        return;
                }

                filterChain.doFilter(request, response);
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
