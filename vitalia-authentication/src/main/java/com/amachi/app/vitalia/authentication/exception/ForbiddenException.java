package com.amachi.app.vitalia.authentication.exception;

import lombok.Getter;

@Getter
public class ForbiddenException extends RuntimeException {
    private final String key;
    private final Object[] args;

    public ForbiddenException(String key, Object... args) {
        super(key);
        this.key = key;
        this.args = args;
    }
}
