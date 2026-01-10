package com.amachi.app.core.auth.exception;

import lombok.Getter;

/**
 * Excepción lanzada cuando un token de autenticación ha expirado.
 */
@Getter
public class TokenExpiredException extends RuntimeException {

    private final String key;
    private final Object[] args;

    public TokenExpiredException(String key, Object... args) {
        super(key);
        this.key = key;
        this.args = args;
    }
}

