package com.amachi.app.vitalia.avatar.exception;

// AvatarProcessingException.java
public class AvatarProcessingException extends RuntimeException {
    public AvatarProcessingException(String message) {
        super(message);
    }
    public AvatarProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
