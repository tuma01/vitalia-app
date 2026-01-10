package com.amachi.app.core.auth.exception;

import org.springframework.http.HttpStatus;

/**
 * Excepción para fallos en activación de cuenta (HTTP 400 BAD REQUEST)
 */
public class AccountActivationException extends AuthenticationException {
    public AccountActivationException(String message) {
        super(message, "ACCOUNT_ACTIVATION_FAILED", HttpStatus.BAD_REQUEST);
    }
}
