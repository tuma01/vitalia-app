package com.amachi.app.vitalia.authentication.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
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
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final LogoutHandler logoutHandler;
    private final AuthenticationProvider authenticationProvider;

    @Value("${spring.profiles.active:dev}")
    private String activeProfile;

//    /**
//     * ✅ Definición del provider principal de autenticación
//     */
//    @Bean
//    public AuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//        provider.setUserDetailsService(userDetailsService);
//        provider.setPasswordEncoder(passwordEncoder);
//        return provider;
//    }

    /**
     * ✅ Configuración principal del filtro de seguridad
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                // 🔹 Desactivar CSRF para APIs JWT
                .csrf(AbstractHttpConfigurer::disable)

                // 🔹 Política de sesión sin estado (JWT)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // 🔹 Autorizaciones por endpoint
                .authorizeHttpRequests(auth -> auth

                        // --- 🔓 ENDPOINTS PÚBLICOS ---
                        .requestMatchers(
                                "/auth/**",          // login, register, refresh
                                "/public/**",        // healthcheck o documentación
                                "/v3/api-docs/**", "/swagger-ui/**"
                        ).permitAll()

                        // --- 🔐 ENDPOINTS PROTEGIDOS POR ROL ---
                        .requestMatchers(HttpMethod.GET, "/api/v1/doctor/**").hasAnyRole("DOCTOR", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/v1/doctor/**").hasRole("DOCTOR")

                        .requestMatchers("/api/v1/nurse/**").hasAnyRole("NURSE", "DOCTOR", "ADMIN")
                        .requestMatchers("/api/v1/patient/**").hasAnyRole("PATIENT", "DOCTOR", "NURSE", "ADMIN")
                        .requestMatchers("/api/v1/employee/**").hasRole("ADMIN")

                        // --- 🔒 CUALQUIER OTRO ENDPOINT REQUIERE AUTENTICACIÓN ---
                        .anyRequest().authenticated()
                )

                // 🔹 Provider personalizado
                .authenticationProvider(authenticationProvider)

                // 🔹 Incluir filtro JWT antes del UsernamePasswordAuthenticationFilter
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)

                // 🔹 Configurar logout (invalidate token, limpiar contexto)
                .logout(logout -> logout
                        .logoutUrl("/api/v1/auth/logout")
                        .addLogoutHandler(logoutHandler)
                        .logoutSuccessHandler((request, response, authentication) -> {
                            response.setStatus(200);
                        })
                );

        // 🔹 Configuraciones especiales para entorno dev
        if ("dev".equalsIgnoreCase(activeProfile)) {
            http.cors(AbstractHttpConfigurer::disable); // en dev, permitir CORS desde localhost frontend
        }

        return http.build();
    }

//    /**
//     * ✅ AuthenticationManager para login controller
//     */
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
//        return config.getAuthenticationManager();
//    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // 🔹 En local, permite subdominios y front Angular
        configuration.setAllowedOriginPatterns(List.of(
                "http://localhost:*",
                "http://*.localhost:*"
        ));

        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type", "X-Tenant-Code"));
        configuration.setExposedHeaders(List.of("Authorization", "Content-Disposition"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
