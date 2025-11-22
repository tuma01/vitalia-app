package com.amachi.app.vitalia.authentication.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

// TODO a borrar luego, es solo para test
//@Configuration
//@EnableWebSecurity
public class DevSecurityConfig {

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(csrf -> csrf.disable()) // Desactivar CSRF para Postman
//                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll()); // Permitir todas las rutas
//        return http.build();
//    }
}
