package com.amachi.app.vitalia.authentication.exception;

/**
 * Lanzada cuando el usuario buscado no existe.
 */
public class UserNotFoundException extends CommonAuthenticationException {
    public UserNotFoundException(String message) {
        super(message);
    }
}

