package com.amachi.app.core.auth.exception;

import com.amachi.app.core.common.enums.TokenErrorType;
import lombok.Getter;

/**
 * Lanzada cuando un token es inválido o mal formado.
 */
@Getter
public class InvalidTokenException extends AuthenticationException {
    private final TokenErrorType errorType;

    /**
     * Constructor básico: solo con tipo de error
     */
    public InvalidTokenException(TokenErrorType errorType) {
        super(errorType.getMessageKey());
        this.errorType = errorType;
    }

    /**
     * Constructor con argumentos dinámicos para el mensaje
     */
    public InvalidTokenException(TokenErrorType errorType, Object... args) {
        super(errorType.getMessageKey(), args);
        this.errorType = errorType;
    }
}