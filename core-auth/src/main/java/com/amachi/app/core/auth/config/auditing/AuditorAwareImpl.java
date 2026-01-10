package com.amachi.app.core.auth.config.auditing;

import com.amachi.app.core.auth.entity.SecurityUser;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Módulo: vitalia-security
 *
 * Implementación de AuditorAware para Spring Data Auditing.
 * Extrae el ID del usuario actual (Long) desde el contexto de seguridad de Spring.
 * Esto es crucial para que los campos createdBy/lastModifiedBy en Auditable funcionen.
 */
@Component("auditorAware")
@Primary
public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) return Optional.of("SYSTEM");

        Object principal = auth.getPrincipal();
        if (principal instanceof SecurityUser su)
            return Optional.of(String.valueOf(su.getUserId()));
        if (principal instanceof String s)
            return Optional.of(s);

        return Optional.of("SYSTEM");
    }
}
