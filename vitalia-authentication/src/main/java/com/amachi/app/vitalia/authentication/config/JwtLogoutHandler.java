package com.amachi.app.vitalia.authentication.config;

import com.amachi.app.vitalia.authentication.service.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtLogoutHandler implements LogoutHandler {

    private final TokenService tokenService; // tu servicio de JWT y refresh token

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

        // 1️⃣ Extraer token del header Authorization
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String jwt = authHeader.substring(7);

            // 2️⃣ Invalidar el token en tu sistema (ej: Redis, DB)
            tokenService.invalidateToken(jwt);

            // 🔹 Si quieres, loguear información del usuario y tenant
            if (authentication != null && authentication.getPrincipal() instanceof org.springframework.security.core.userdetails.User) {
                String username = ((org.springframework.security.core.userdetails.User) authentication.getPrincipal()).getUsername();
                String tenantCode = request.getHeader("X-Tenant-Code");
                System.out.println("Logout: usuario=" + username + ", tenant=" + tenantCode);
            }
        }

        // 3️⃣ Limpiar contexto de seguridad
        if (authentication != null) {
            authentication.setAuthenticated(false);
        }
    }
}
