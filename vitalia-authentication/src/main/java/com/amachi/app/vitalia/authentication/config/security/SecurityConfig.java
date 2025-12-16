package com.amachi.app.vitalia.authentication.config.security;

import com.amachi.app.vitalia.authentication.config.multiTenant.MultiTenantFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final MultiTenantFilter multiTenantFilter; // Injected
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final LogoutHandler logoutHandler;
    private final AuthenticationProvider authenticationProvider;

    @Value("${spring.profiles.active:dev}")
    private String activeProfile;

    /**
     * ✅ Configuración principal del filtro de seguridad
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 🔹 Desactivar CSRF para APIs JWT
                .csrf(AbstractHttpConfigurer::disable)
                // 🔹 Autorizaciones por endpoint
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/auth/**", // login, register, refresh
                                "/tenants/**", // Permitir listar tenants públicamente para login
                                "/public/**", // healthcheck o documentación
                                "/v3/api-docs/**", "/swagger-ui/**")
                        .permitAll()

                        // --- 🔐 ENDPOINTS PROTEGIDOS POR ROL ---
                        .requestMatchers("/super-admin/tenants/**").hasRole("SUPER_ADMIN")
                        .requestMatchers("/countries/**", "/departamentos/**", "/provincias/**", "/municipios/**")
                        .hasRole("SUPER_ADMIN")
                        .requestMatchers("/employee/**").hasRole("ADMIN")
                        .requestMatchers("/user/**").hasAnyRole("USER", "ADMIN", "SUPER_ADMIN")
                        .requestMatchers(HttpMethod.GET, "/doctor/**").hasAnyRole("DOCTOR", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/doctor/**").hasRole("DOCTOR")

                        .requestMatchers("/nurse/**").hasAnyRole("NURSE", "DOCTOR", "ADMIN")
                        .requestMatchers("/patient/**").hasAnyRole("PATIENT", "DOCTOR", "NURSE", "ADMIN")

                        // --- 🔒 CUALQUIER OTRO ENDPOINT REQUIERE AUTENTICACIÓN ---
                        .anyRequest().authenticated())
                // 🔹 Política de sesión sin estado (JWT)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // 🔹 Provider personalizado
                .authenticationProvider(authenticationProvider)

                // 🔹 Incluir filtro JWT antes del UsernamePasswordAuthenticationFilter
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                // 🔹 Incluir MultiTenantFilter antes del JWT Filter (para tener el contexto
                // listo)
                .addFilterBefore(multiTenantFilter, JwtAuthenticationFilter.class)

                // 🔹 Configurar logout (invalidate token, limpiar contexto)
                .logout(logout -> logout
                        .logoutUrl("/api/v1/auth/logout")
                        .addLogoutHandler(logoutHandler)
                        .logoutSuccessHandler((request, response, authentication) -> {
                            response.setStatus(200);
                        }));

        // 🔹 Configuraciones especiales para entorno dev
        if ("dev".equalsIgnoreCase(activeProfile)) {
            http.cors(cors -> cors.configurationSource(corsConfigurationSource())); // Habilitar CORS en dev
        }

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // 🔹 En local, permite subdominios y front Angular
        configuration.setAllowedOriginPatterns(List.of(
                "http://localhost:*",
                "http://*.localhost:*"));

        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type", "X-Tenant-Code"));
        configuration.setExposedHeaders(List.of("Authorization", "Content-Disposition"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
