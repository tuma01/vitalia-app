package com.amachi.app.core.common.exception;

import lombok.Getter;

@Getter
public class BadRequestException extends RuntimeException {
    private final String key;
    private final Object[] args;
    private final String field;

    public BadRequestException(String key, Object... args) {
        super(key);
        this.key = key;
        this.args = args;
        this.field = null;
    }

    public BadRequestException(String key, String field, Object... args) {
        super(key);
        this.key = key;
        this.field = field;
        this.args = args;
    }
}