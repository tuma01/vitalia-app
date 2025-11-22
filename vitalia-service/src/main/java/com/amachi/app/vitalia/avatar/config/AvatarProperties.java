package com.amachi.app.vitalia.avatar.config;

import com.amachi.app.vitalia.common.i18n.Translator;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "app.avatar")
@Getter
@Setter
public class AvatarProperties {

    /**
     * Tamaño máximo en bytes. Por defecto 1MB.
     */
    private long maxSize = 1_048_576;

    /**
     * Tipos MIME permitidos (e.g. image/jpeg, image/png).
     */
    private List<String> allowedTypes = List.of("image/jpeg", "image/png", "image/gif");

    /**
     * Ruta por defecto del avatar si no existe (puede ser classpath o URL).
     */
    private String defaultPath = "classpath:templates/avatars/default-avatar.jpg";

    @PostConstruct
    public void validate() {
        if (maxSize <= 0) throw new IllegalStateException(Translator.toLocale("validation.avatar.maxSize.mustBePositive", null));
        if (allowedTypes == null || allowedTypes.isEmpty()) throw new IllegalStateException(Translator.toLocale("validation.avatar.allowedTypes.mustNotBeEmpty", null));
        if (defaultPath == null || defaultPath.isBlank()) throw new IllegalStateException(Translator.toLocale("validation.avatar.defaultPath.mustBeConfigured", null));
    }
}
