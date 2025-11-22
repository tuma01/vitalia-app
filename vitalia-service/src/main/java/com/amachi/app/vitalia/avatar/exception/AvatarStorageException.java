package com.amachi.app.vitalia.avatar.exception;

// AvatarStorageException.java
public class AvatarStorageException extends RuntimeException {
    public AvatarStorageException(String message) {
        super(message);
    }
    public AvatarStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
