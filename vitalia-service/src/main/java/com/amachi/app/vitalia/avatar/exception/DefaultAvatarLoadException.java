package com.amachi.app.vitalia.avatar.exception;

/**
 * Excepción lanzada cuando falla la carga del avatar por defecto
 */
public class DefaultAvatarLoadException extends RuntimeException {
    private static final String DEFAULT_MSG = "Error al cargar el avatar por defecto";

    public DefaultAvatarLoadException(String message, Throwable cause) {
        super(message != null ? message : DEFAULT_MSG, cause);
    }

    public DefaultAvatarLoadException(Throwable cause) {
        this(DEFAULT_MSG, cause);
    }
}
