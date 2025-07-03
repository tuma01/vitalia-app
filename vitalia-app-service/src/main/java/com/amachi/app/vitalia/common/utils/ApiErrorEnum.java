package com.amachi.app.vitalia.common.utils;

import com.amachi.app.vitalia.config.Translator;
import com.amachi.app.vitalia.utils.AppConstants;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ApiErrorEnum {
    HEADER_MISSING("HEADER_MISSING", Translator.toLocale(AppConstants.ErrorMessages.HEADER_MISSING, null), HttpStatus.BAD_REQUEST, "R_HAS_001"),
    HEADER_FORMAT_ERROR("HEADER_FORMAT_ERROR", Translator.toLocale(AppConstants.ErrorMessages.HEADER_FORMAT_ERROR, null), HttpStatus.BAD_REQUEST, "R_HAS_002"),
    ACCEPT_VERSION_NOT_SUPPORTED("ACCEPT_VERSION_NOT_SUPPORTED", Translator.toLocale(AppConstants.ErrorMessages.ACCEPT_VERSION_NOT_SUPPORTED, null), HttpStatus.NOT_ACCEPTABLE, "R_HAS_003"),
    ACCEPT_NOT_SUPPORTED("ACCEPT_NOT_SUPPORTED", Translator.toLocale(AppConstants.ErrorMessages.ACCEPT_NOT_SUPPORTED, null), HttpStatus.NOT_ACCEPTABLE, "R_HAS_004"),
    PARAM_FORMAT_ERROR("PARAM_FORMAT_ERROR", Translator.toLocale(AppConstants.ErrorMessages.PARAM_FORMAT_ERROR, null), HttpStatus.BAD_REQUEST, "R_HAS_005"),
    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR", Translator.toLocale(AppConstants.ErrorMessages.INTERNAL_SERVER_ERROR, null), HttpStatus.INTERNAL_SERVER_ERROR, "R_HAS_006"),
    OBJECT_NOT_FOUND("OBJECT_NOT_FOUND", Translator.toLocale(AppConstants.ErrorMessages.OBJECT_NOT_FOUND, null), HttpStatus.NOT_FOUND, "R_HAS_007"),
    UNHANDLED_ERROR("UNHANDLED_ERROR", Translator.toLocale(AppConstants.ErrorMessages.UNHANDLED_ERROR, null), HttpStatus.NOT_IMPLEMENTED, "R_HAS_008");

    public final String code;
    public final String message;
    public final HttpStatus status;
    public final String description;

    ApiErrorEnum(String code, String message, HttpStatus status, String description) {
        this.code = code;
        this.message = message;
        this.status = status;
        this.description = description;
    }
}
