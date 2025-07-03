package com.amachi.app.vitalia.common.exception;

import org.springframework.http.HttpStatus;

/**
 * Excepción para tokens expirados (HTTP 403 FORBIDDEN)
 */
public class TokenExpiredException extends AuthenticationException {
    public TokenExpiredException(String message) {
        super(message, "TOKEN_EXPIRED", HttpStatus.FORBIDDEN);
    }
}
