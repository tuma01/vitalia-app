package com.amachi.app.vitalia.exception;

import com.amachi.app.vitalia.authentication.exception.*;
import com.amachi.app.vitalia.common.api.ApiResponse;
import com.amachi.app.vitalia.common.error.ErrorCategory;
import com.amachi.app.vitalia.common.error.ErrorCode;
import com.amachi.app.vitalia.common.error.ErrorDetail;
import com.amachi.app.vitalia.common.exception.BadRequestException;
import com.amachi.app.vitalia.common.exception.ResourceNotFoundException;
import com.amachi.app.vitalia.common.http.HttpStatusCode;
import com.amachi.app.vitalia.common.i18n.Translator;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Hidden
@Slf4j
@RequiredArgsConstructor
//@RestControllerAdvice
public class GlobalExceptionHandlerOld extends ResponseEntityExceptionHandler {

    // ==============================================================
    // === VALIDATION EXCEPTIONS
    // ==============================================================
//    @Override
//    protected ResponseEntity<Object> handleMethodArgumentNotValid(
//            MethodArgumentNotValidException ex,
//            HttpHeaders headers,
//            org.springframework.http.HttpStatusCode status,
//            WebRequest request) {
//
//        Map<String, String> invalidFields = ex.getBindingResult().getFieldErrors().stream()
//                .collect(Collectors.toMap(
//                        FieldError::getField,
//                        fe -> Translator.toLocale(fe.getDefaultMessage(), fe.getArguments()),
//                        (oldVal, newVal) -> oldVal + "; " + newVal
//                ));
//
//        ErrorDetail errorDetail = ErrorDetail.from(
//                ErrorCode.VAL_REQUIRED_FIELD,
//                Translator.toLocale("validation.error.fields", null),
//                null,
//                Map.of(
//                        "errorCount", invalidFields.size(),
//                        "invalidFields", invalidFields
//                )
//        );
//
//        return buildErrorResponse(HttpStatusCode.BAD_REQUEST, errorDetail, request);
//    }
//
//    @Override
//    protected ResponseEntity<Object> handleHttpMessageNotReadable(
//            org.springframework.http.converter.HttpMessageNotReadableException ex,
//            HttpHeaders headers,
//            org.springframework.http.HttpStatusCode status,
//            WebRequest request) {
//
//        // Detecta si el error viene de un campo nulo en un @NotNull
//        String fieldName = null;
//        if (ex.getCause() != null && ex.getCause().getMessage() != null) {
//            String msg = ex.getCause().getMessage();
//            // Busca patrón típico: "departamentoId" (campo)
//            int idx = msg.indexOf("departamentoId");
//            if (idx != -1) fieldName = "departamentoId";
//        }
//
//        ErrorDetail errorDetail = ErrorDetail.from(
//                ErrorCode.VAL_REQUIRED_FIELD,
//                Translator.toLocale("err.mandatory", null),
//                fieldName,
//                Map.of("info", ex.getLocalizedMessage())
//        );
//
//        return buildErrorResponse(HttpStatusCode.BAD_REQUEST, errorDetail, request);
//    }
//
//
//    // ==============================================================
//    // === BUSINESS EXCEPTIONS
//    // ==============================================================
//    @ExceptionHandler(ResourceNotFoundException.class)
//    public ResponseEntity<ApiResponse<Void>> handleResourceNotFound(ResourceNotFoundException ex, HttpServletRequest request) {
//        Map<String, Object> details = Map.of(
//                "id", ex.getArgs()[0],
//                "entity", ex.getEntityName()
//        );
//        return buildErrorResponse(
//                HttpStatusCode.NOT_FOUND,
//                ErrorDetail.from(
//                        ErrorCode.BUS_RESOURCE_NOT_FOUND,
//                        Translator.toLocale(ex.getKey(), ex.getArgs()),
//                        null,
//                        details
//                ),
//                request
//        );
//    }
//
//    @ExceptionHandler(BadRequestException.class)
//    public ResponseEntity<ApiResponse<Void>> handleBadRequest(BadRequestException ex, HttpServletRequest request) {
//        return buildErrorResponse(
//                HttpStatusCode.BAD_REQUEST,
//                ErrorDetail.from(ErrorCode.VAL_INVALID_FORMAT,
//                        Translator.toLocale(ex.getKey(), ex.getArgs()),
//                        ex.getField(),
//                        Map.of("info", ex.getMessage())),
//                request
//        );
//    }
//
//    @ExceptionHandler(AccountActivationException.class)
//    public ResponseEntity<ApiResponse<Void>> handleAccountActivation(AccountActivationException ex, HttpServletRequest request) {
//        ErrorDetail detail = ErrorDetail.from(
//                ErrorCode.SEC_ACCOUNT_ACTIVATION_FAILED,
//                Translator.toLocale(ex.getKey(), ex.getArgs()),
//                null,
//                Map.of("info", ex.getMessage())
//        );
//        return buildErrorResponse(HttpStatusCode.BAD_REQUEST, detail, request);
//    }
//
//    @ExceptionHandler(ConflictException.class)
//    public ResponseEntity<ApiResponse<Void>> handleConflict(ConflictException ex, HttpServletRequest request) {
//        return buildErrorResponse(
//                HttpStatusCode.CONFLICT,
//                ErrorDetail.from(ErrorCode.BUS_DUPLICATE_RESOURCE,
//                        Translator.toLocale(ex.getKey(), ex.getArgs()),
//                        ex.getField(),
//                        Map.of("info", ex.getMessage())),
//                request
//        );
//    }
//
//    @ExceptionHandler(DataIntegrityViolationException.class)
//    public ResponseEntity<ApiResponse<Void>> handleDataIntegrityViolation(DataIntegrityViolationException ex, HttpServletRequest request) {
//        String messageKey = ex.getMessage() != null && ex.getMessage().contains("duplicate")
//                ? "error.data.integrity.violation.value"
//                : "error.data.integrity.violation.generic";
//
//        return buildErrorResponse(
//                HttpStatusCode.CONFLICT,
//                ErrorDetail.from(ErrorCode.BUS_DUPLICATE_RESOURCE,
//                        Translator.toLocale(messageKey, null),
//                        null,
//                        Map.of("info", ex.getMostSpecificCause().getMessage())),
//                request
//        );
//    }
//
//    // ==============================================================
//    // === SECURITY EXCEPTIONS
//    // ==============================================================
//    @ExceptionHandler(AuthenticationException.class)
//    public ResponseEntity<ApiResponse<Void>> handleAuthentication(AuthenticationException ex, HttpServletRequest request) {
//        ErrorDetail detail = ErrorDetail.from(
//                ErrorCode.SEC_INVALID_TOKEN,
//                Translator.toLocale(ex.getKey(), ex.getArgs()),
//                null,
//                Map.of("info", ex.getMessage())
//        );
//        return buildErrorResponse(HttpStatusCode.UNAUTHORIZED, detail, request);
//    }
//
//    @ExceptionHandler(TokenExpiredException.class)
//    public ResponseEntity<ApiResponse<Void>> handleTokenExpired(TokenExpiredException ex, HttpServletRequest request) {
//        return buildErrorResponse(
//                HttpStatusCode.FORBIDDEN,
//                ErrorDetail.from(ErrorCode.SEC_TOKEN_EXPIRED,
//                        Translator.toLocale(ex.getKey(), ex.getArgs()),
//                        null,
//                        Map.of("info", ex.getMessage())),
//                request
//        );
//    }
//
//    @ExceptionHandler(InvalidTokenException.class)
//    public ResponseEntity<ApiResponse<Void>> handleInvalidToken(InvalidTokenException ex, HttpServletRequest request) {
//        return buildErrorResponse(
//                HttpStatusCode.BAD_REQUEST,
//                ErrorDetail.from(ErrorCode.SEC_INVALID_TOKEN,
//                        Translator.toLocale(ex.getKey(), ex.getArgs()),
//                        null,
//                        Map.of("type", ex.getErrorType().name(), "info", ex.getMessage())),
//                request
//        );
//    }
//
//    @ExceptionHandler(UnauthorizedException.class)
//    public ResponseEntity<ApiResponse<Void>> handleUnauthorized(UnauthorizedException ex, HttpServletRequest request) {
//        return buildErrorResponse(
//                HttpStatusCode.UNAUTHORIZED,
//                ErrorDetail.from(ErrorCode.SEC_UNAUTHORIZED,
//                        Translator.toLocale(ex.getKey(), ex.getArgs()),
//                        null,
//                        Map.of("info", ex.getMessage())),
//                request
//        );
//    }
//
//    @ExceptionHandler(ForbiddenException.class)
//    public ResponseEntity<ApiResponse<Void>> handleForbidden(ForbiddenException ex, HttpServletRequest request) {
//        return buildErrorResponse(
//                HttpStatusCode.FORBIDDEN,
//                ErrorDetail.from(ErrorCode.SEC_FORBIDDEN,
//                        Translator.toLocale(ex.getKey(), ex.getArgs()),
//                        null,
//                        Map.of("info", ex.getMessage())),
//                request
//        );
//    }
//
//    @ExceptionHandler(HttpMessageNotReadableException.class)
//    public ResponseEntity<ApiResponse<Void>> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
//                                                                          HttpServletRequest request) {
//        ErrorDetail detail = ErrorDetail.from(
//                ErrorCode.VAL_REQUIRED_FIELD,
//                Translator.toLocale("validation.error.fields", null),
//                null,
//                Map.of("info", ex.getMostSpecificCause().getMessage())
//        );
//
//        return buildErrorResponse(HttpStatusCode.BAD_REQUEST, detail, request);
//    }

//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<ApiResponse<Void>> handleValidationException(MethodArgumentNotValidException ex, HttpServletRequest request) {
//        Map<String, String> invalidFields = ex.getBindingResult().getFieldErrors().stream()
//                .collect(Collectors.toMap(
//                        FieldError::getField,
//                        DefaultMessageSourceResolvable::getDefaultMessage,
//                        (existing, replacement) -> existing
//                ));
//
//        ErrorDetail errorDetail = ErrorDetail.from(
//                ErrorCode.VAL_REQUIRED_FIELD, // 👈 código estandarizado
//                Translator.toLocale("validation.error.fields", null), // mensaje general traducible
//                null,
//                Map.of(
//                        "errorCount", invalidFields.size(),
//                        "invalidFields", invalidFields
//                )
//        );
//        return buildErrorResponse(HttpStatusCode.BAD_REQUEST, errorDetail, request);
//    }

    // ==============================================================
    // === GENERIC & SYSTEM ERRORS
    // ==============================================================
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGeneric(Exception ex, HttpServletRequest request) {
        log.error("Unexpected exception: ", ex);

        ErrorDetail detail = ErrorDetail.from(
                ErrorCode.SYS_INTERNAL_ERROR,
                Translator.toLocale("error.internal.server", null),
                null,
                Map.of("exception", ex.getClass().getSimpleName(), "info", ex.getMessage())
        );

        return buildErrorResponse(HttpStatusCode.INTERNAL_SERVER_ERROR, detail, request);
    }

    // ==============================================================
    // === HELPER
    // ==============================================================
    private ResponseEntity<ApiResponse<Void>> buildErrorResponse(HttpStatusCode status, ErrorDetail detail, HttpServletRequest request) {
        return ResponseEntity.status(status.getCode())
                .body(ApiResponse.error(status, detail, request.getRequestURI()));
    }
}
