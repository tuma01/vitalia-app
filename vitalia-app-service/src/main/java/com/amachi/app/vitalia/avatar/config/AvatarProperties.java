package com.amachi.app.vitalia.avatar.config;

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
}
