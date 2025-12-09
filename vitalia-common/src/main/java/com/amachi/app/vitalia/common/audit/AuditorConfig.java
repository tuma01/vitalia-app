package com.amachi.app.vitalia.common.audit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

@Configuration
// @EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class AuditorConfig {

    /**
     * Bean AuditorAware genérico para todos los módulos.
     * Devuelve "system" mientras no haya usuario autenticado.
     */
    @Bean
    public AuditorAware<String> auditorAware() {
        return () -> Optional.of("system");
        // return new ApplicationAuditorAwareImpl();
    }
}
