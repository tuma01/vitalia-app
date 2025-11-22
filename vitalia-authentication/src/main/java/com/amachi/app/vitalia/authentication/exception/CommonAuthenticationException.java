package com.amachi.app.vitalia.authentication.exception;

/**
 * Excepción base para errores de autenticación en el dominio común.
 */
public class CommonAuthenticationException extends RuntimeException {
    public CommonAuthenticationException(String message) {
        super(message);
    }

    public CommonAuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}

