package com.amachi.app.vitalia.authentication.exception;

import lombok.Getter;

@Getter
public class AuthenticationException extends RuntimeException {

    private final String key;      // clave de mensaje para Translatorcc
    private final Object[] args;   // parámetros dinámicos para el mensaje

    public AuthenticationException(String key, Object... args) {
        super(key);
        this.key = key;
        this.args = args;
    }
}
