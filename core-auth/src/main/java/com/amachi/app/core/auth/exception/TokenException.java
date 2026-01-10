package com.amachi.app.core.auth.exception;

public class TokenException extends CommonAuthenticationException {
    private String errorCode;
    public TokenException(String message) {
        super(message);
    }

    public TokenException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
