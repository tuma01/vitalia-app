package com.amachi.app.core.common.error;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
@Builder
public class ErrorDetail {

    private final ErrorCategory category;
    private final String code;
    private final String userMessage;
    private final String developerMessage;
    private final String field;
    private final Map<String, Object> details;
    private final String timestamp;
    private final String traceId;

    /**
     * Construcción genérica simple usada en ApiResponse.error(...)
     */
    public static ErrorDetail of(String code, String userMessage, String developerMessage, String path) {
        return ErrorDetail.builder()
                .category(ErrorCategory.GENERAL)
                .code(code)
                .userMessage(userMessage)
                .developerMessage(developerMessage)
                .details(Map.of("path", path))
                .timestamp(Instant.now().toString())
                .traceId(UUID.randomUUID().toString())
                .build();
    }

    /**
     * Construcción avanzada con ErrorCode estándar
     */
    public static ErrorDetail from(ErrorCode errorCode, String userMessage, String field, Map<String, Object> details) {
        return ErrorDetail.builder()
                .category(determineCategory(errorCode))
                .code(errorCode.getCode())
                .userMessage(userMessage != null ? userMessage : errorCode.getDescription())
                .developerMessage(errorCode.getDescription())
                .field(field)
                .details(details != null ? details : new HashMap<>())
                .timestamp(Instant.now().toString())
                .traceId(UUID.randomUUID().toString())
                .build();
    }

    private static ErrorCategory determineCategory(ErrorCode errorCode) {
        if (errorCode == null) return ErrorCategory.GENERAL;
        String code = errorCode.getCode();
        if (code.startsWith("VAL_")) return ErrorCategory.VALIDATION;
        if (code.startsWith("BUS_")) return ErrorCategory.BUSINESS;
        if (code.startsWith("SYS_")) return ErrorCategory.SYSTEM;
        if (code.startsWith("SEC_")) return ErrorCategory.SECURITY;
        return ErrorCategory.GENERAL;
    }
}
