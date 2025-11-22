package com.amachi.app.vitalia.authentication.exception;

import com.amachi.app.vitalia.common.error.ErrorCode;
import lombok.Getter;

@Getter
public class AppSecurityException extends RuntimeException {
    private final ErrorCode errorCode;
    private final String key;
    private final Object[] args;

    public AppSecurityException(ErrorCode errorCode, String key, Object... args) {
        super(key);
        this.errorCode = errorCode;
        this.key = key;
        this.args = args;
    }
}
