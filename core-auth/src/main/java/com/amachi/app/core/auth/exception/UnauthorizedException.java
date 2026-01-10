package com.amachi.app.core.auth.exception;

import lombok.Getter;

@Getter
public class UnauthorizedException extends RuntimeException {
    private final String key;
    private final Object[] args;

    public UnauthorizedException(String key, Object... args) {
        super(key);
        this.key = key;
        this.args = args;
    }
}
