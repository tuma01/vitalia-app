package com.amachi.app.vitalia.common.exception;

public class ResourceNotFoundException extends RuntimeException {
    private final String key;
    private final Object[] args;

    public ResourceNotFoundException(String key, Object... args) {
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
