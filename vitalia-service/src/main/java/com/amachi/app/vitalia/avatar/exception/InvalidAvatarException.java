package com.amachi.app.vitalia.avatar.exception;

import lombok.Getter;

/**
 * Excepción lanzada cuando ocurre un error relacionado con el procesamiento o validación de avatares.
 * Compatible con el manejo global de i18n.
 */
@Getter
public class InvalidAvatarException extends RuntimeException {

    private final String key;       // Clave i18n (por ejemplo: "validation.avatar.invalid_type")
    private final Object[] args;    // Argumentos opcionales para el mensaje
    private final String entityName; // Entidad relacionada (por ejemplo: "avatar")

    public InvalidAvatarException(String key, Object... args) {
        this("avatar", key, args);
    }

    public InvalidAvatarException(String entityName, String key, Object... args) {
        super(key);
        this.key = key;
        this.args = args;
        this.entityName = entityName;
    }
}
