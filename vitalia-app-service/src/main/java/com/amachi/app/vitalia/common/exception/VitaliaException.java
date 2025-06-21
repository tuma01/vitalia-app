package com.amachi.app.vitalia.common.exception;


import com.amachi.app.vitalia.common.utils.ApiErrorEnum;
import lombok.Getter;

/**
 * Exception principale du projet SIB
 */
@Getter
public class VitaliaException extends RuntimeException {
    private final ApiErrorEnum apiErrorEnum;

    public VitaliaException() {
        this(ApiErrorEnum.UNHANDLED_ERROR, "No message for this exception");
    }

    public VitaliaException(final Exception e) {
        this(ApiErrorEnum.UNHANDLED_ERROR, e);
    }

    public VitaliaException(ApiErrorEnum apiErrorEnum, Exception e) {
        super(String.format(apiErrorEnum.message, e));
        this.apiErrorEnum = apiErrorEnum;
    }

    public VitaliaException(ApiErrorEnum apiErrorEnum, Object... args) {
        super(String.format(apiErrorEnum.message, args));
        this.apiErrorEnum = apiErrorEnum;
    }
}
