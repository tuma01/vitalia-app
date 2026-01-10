package com.amachi.app.core.auth.exception;

/**
 * Lanzada cuando el usuario buscado no existe.
 */
public class UserNotFoundException extends CommonAuthenticationException {
    public UserNotFoundException(String message) {
        super(message);
    }
}

