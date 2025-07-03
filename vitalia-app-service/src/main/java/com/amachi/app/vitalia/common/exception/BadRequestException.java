package com.amachi.app.vitalia.common.exception;

import lombok.Getter;

@Getter
public class BadRequestException extends RuntimeException {
    private final String key;
    private final Object[] args;

    public BadRequestException(String key, Object... args) {
        this.key = key;
        this.args = args;
    }

}