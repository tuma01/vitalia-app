package com.amachi.app.vitalia.common.exception;

import com.amachi.app.vitalia.common.utils.TokenErrorType;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Excepción para tokens inválidos (HTTP 400 BAD REQUEST)
 */
@Getter
public class InvalidTokenException extends AuthenticationException {
    private final TokenErrorType errorType;

    public InvalidTokenException(TokenErrorType errorType) {
        super(errorType.getDescription(),
                "TOKEN_" + errorType.name(),
                HttpStatus.BAD_REQUEST);
        this.errorType = errorType;
    }

    public InvalidTokenException(TokenErrorType errorType, String additionalInfo) {
        super(String.format("%s: %s", errorType.getDescription(), additionalInfo),
                "TOKEN_" + errorType.name(),
                HttpStatus.BAD_REQUEST);
        this.errorType = errorType;
    }

}
