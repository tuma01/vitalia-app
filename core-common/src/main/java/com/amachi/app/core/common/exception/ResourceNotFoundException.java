package com.amachi.app.core.common.exception;

import lombok.Getter;

/**
 * Excepción profesional para recursos no encontrados.
 * Se puede especificar un campo (por ejemplo "id") y argumentos adicionales para el mensaje.
 */
@Getter
public class ResourceNotFoundException extends RuntimeException {

    private final String key;      // Clave para i18n o mensajes
    private final Object[] args;   // Argumentos para el mensaje
    private final String entityName;    // Campo que provocó la excepción (ej. "id")

    public ResourceNotFoundException(String entityName, String key, Object... args) {
        super(key);
        this.key = key;
        this.args = args;
        this.entityName = entityName;
    }

//    public ResourceNotFoundException(String key, String field, Object... args) {
//        super(key);
//        this.key = key;
//        this.field = field;
//        this.args = args;
//    }
}
