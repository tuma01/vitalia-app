package com.amachi.app.vitalia.clinical.avatar.exception;

import com.amachi.app.core.common.exception.BadRequestException;

public class InvalidAvatarException extends BadRequestException {

    public InvalidAvatarException(String message) {
        super(message);
    }

    public InvalidAvatarException(String message, Throwable cause) {
        super(message, cause);
    }

    public String getEntityName() {
        return "Avatar";
    }
}
