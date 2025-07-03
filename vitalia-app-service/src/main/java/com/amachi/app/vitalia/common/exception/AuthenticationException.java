package com.amachi.app.vitalia.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Excepción base para errores de autenticación y autorización.
 * Establece el código HTTP 401 UNAUTHORIZED como default.
 */
@Getter
public class AuthenticationException extends RuntimeException {
    private final String errorCode;
    private final HttpStatus httpStatus;

    public AuthenticationException(String message, String errorCode) {
        this(message, errorCode, HttpStatus.UNAUTHORIZED);
    }

    public AuthenticationException(String message, String errorCode, HttpStatus httpStatus) {
        super(message);
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
    }
}
