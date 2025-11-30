package com.amachi.app.vitalia.authentication.config.auditing;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ApplicationAuditorAwareImpl implements AuditorAware<String> {

    //TODO decomentar esto cuando tengamos usuarios autenticados y
    // borrar o comentar el otro getCurrentAuditor() cuando tengamos usuarios autenticados
//    @Override
//    public Optional<String> getCurrentAuditor() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication == null || !authentication.isAuthenticated()
//        || authentication instanceof AnonymousAuthenticationToken) {
//
//            return Optional.of("system");
//        }
//        User userPrincipal = (User) authentication.getPrincipal();
//        return Optional.ofNullable(userPrincipal.getName());
//    }

    @Override
    public Optional<String> getCurrentAuditor() {
        // Retorna un valor fijo mientras no tengas usuarios autenticados
        return Optional.of("system");
    }
}
