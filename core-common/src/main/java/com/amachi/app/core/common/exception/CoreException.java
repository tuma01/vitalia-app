package com.amachi.app.core.common.exception;

/**
 * Base core exception (Clean Architecture - No Framework dependencies).
 */
public class CoreException extends RuntimeException {
    public CoreException(String message) {
        super(message);
    }
}
