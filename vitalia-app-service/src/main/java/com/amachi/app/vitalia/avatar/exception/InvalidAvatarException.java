package com.amachi.app.vitalia.avatar.exception;

public class InvalidAvatarException extends RuntimeException {
    public InvalidAvatarException(String message) {
        super(message);
    }

    public InvalidAvatarException(String message, Throwable cause) {
        super(message, cause);
    }
}
