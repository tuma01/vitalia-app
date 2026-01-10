package com.amachi.app.core.auth.exception;

import lombok.Getter;

@Getter
public class ConflictException extends RuntimeException {
    private final String key;
    private final Object[] args;
    private final String field;

    public ConflictException(String key, Object... args) {
        super(key);
        this.key = key;
        this.args = args;
        this.field = null;
    }

    public ConflictException(String key, String field, Object... args) {
        super(key);
        this.key = key;
        this.field = field;
        this.args = args;
    }
}
