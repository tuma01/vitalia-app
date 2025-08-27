package com.amachi.app.vitalia.config.security;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfiguration {
    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    /*
    PATHS:
    1. Autenticación y Autorización

        /auth/login – Inicio de sesión
        /auth/register – Registro de nuevos usuarios
        /auth/refresh-token – Refresco de token JWT
        /auth/password-reset – Solicitud de reinicio de contraseña
        /auth/password-change – Cambio de contraseña autenticada

    2. Recursos públicos

            /public/** – Archivos públicos (assets, imágenes, etc.)
            /docs/** – Documentación de API pública
            /swagger-ui/** – Interfaz de usuario de Swagger
            /v3/api-docs/** – Documentación de API en formato JSON

    3. Panel de administración

            /admin/** – Panel de administración (solo accesible para usuarios con rol ADMIN)

    4. Panel de Usuario Autenticado

            /user/** – Panel de usuario autenticado
            /profile/** – Perfil de usuario autenticado
            /dashboard/** – Panel de usuario autenticado

    5. APIs Internas y Externas

            /api/internal/** – APIs internas (restringidas por roles o tokens específicos)
            /api/external/** – APIs externas (disponibles para usuarios y administradores)

    6. Cualquier Otra Solicitud
            La regla anyRequest().authenticated()
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(withDefaults())
                .csrf(AbstractHttpConfigurer::disable)  // Deshabilitar CSRF -> OU CON REF AL METODO .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth

                                .requestMatchers("/auth/**").permitAll()  // Todas las rutas bajo /auth/ son públicas
                                .requestMatchers("/public/**").permitAll()  // Recursos públicos
                                .requestMatchers("/docs/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll()  // Documentación pública

                                // Panel de administración (solo accesible para usuarios con rol ADMIN)
                        //TODO cambiar a ADMIN
//                                .requestMatchers("/admin/**").hasRole(RoleEnum.ADMIN.name())
                        .requestMatchers("/admin/**").hasRole("ADMIN")

                                // Panel de usuario autenticado (accesible por cualquier usuario autenticado)
                                .requestMatchers("/user/**", "/profile/**", "/dashboard/**").authenticated()

                                // APIs internas (pueden estar restringidas por roles o tokens específicos)
                                .requestMatchers("/api/internal/**").hasRole("ADMIN")  // APIs internas solo para administradores
                                .requestMatchers("/api/external/**").hasAnyRole("USER", "ADMIN")  // APIs externas disponibles para usuarios y administradores

                                // Cualquier otra solicitud debe estar autenticada
                                .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)  // Política de sesiones sin estado
                )
                .authenticationProvider(authenticationProvider)  // Proveedor de autenticación personalizado
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);  // Filtro JWT

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:4200"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}