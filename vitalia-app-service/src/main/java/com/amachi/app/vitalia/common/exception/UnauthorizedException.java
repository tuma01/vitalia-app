package com.amachi.app.vitalia.common.exception;

public class UnauthorizedException extends RuntimeException {
    private final String key;
    private final Object[] args;

    public UnauthorizedException(String key, Object... args) {
        super(key);
        this.key = key;
        this.args = args;
    }

    public String getKey() {
        return key;
    }

    public Object[] getArgs() {
        return args;
    }
}
