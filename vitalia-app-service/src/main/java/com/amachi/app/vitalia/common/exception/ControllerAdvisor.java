package com.amachi.app.vitalia.common.exception;

import com.amachi.app.vitalia.avatar.exception.AvatarProcessingException;
import com.amachi.app.vitalia.avatar.exception.AvatarStorageException;
import com.amachi.app.vitalia.avatar.exception.DefaultAvatarLoadException;
import com.amachi.app.vitalia.common.dto.ApiResponse;
import com.amachi.app.vitalia.utils.AppConstants;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import static com.amachi.app.vitalia.utils.AppConstants.Patterns.DUPLICATE_ENTRY_PREFIX;

/**
 * Manejador centralizado de excepciones para la API.
 * Transforma excepciones en respuestas estandarizadas.
 */
@Hidden
@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class ControllerAdvisor {

    private final MessageSource messageSource;

    // ================== Manejo de excepciones de validación ================== //
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationExceptions(
            MethodArgumentNotValidException ex, WebRequest request) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), resolveMessage(error, request.getLocale()))
        );

        return ResponseEntity.badRequest()
                .body(ApiResponse.error(HttpStatus.BAD_REQUEST,
                        messageSource.getMessage(
                                AppConstants.ErrorMessages.PARAM_FORMAT_ERROR,
                                null,
                                "Validation errors",
                                request.getLocale()),
                        errors));
    }

    private String resolveMessage(FieldError error, Locale locale) {
        return Optional.ofNullable(error.getDefaultMessage())
                .map(msg -> messageSource.getMessage(msg, error.getArguments(), msg, locale))
                .orElse("Invalid field value");
    }

    // ================== Manejo de integridad de datos ================== //
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse<Void>> handleDataIntegrityViolation(
            DataIntegrityViolationException ex, WebRequest request) {

        String rootCause = Optional.ofNullable(ex.getRootCause())
                .map(Throwable::getMessage)
                .orElse(ex.getMessage());

        log.error("Data integrity violation: {}", rootCause);

        String message = extractDuplicatedValue(rootCause)
                .map(value -> messageSource.getMessage(
                        AppConstants.ErrorMessages.DATA_INTEGRITY_VALUE,
                        new Object[]{value},
                        "Duplicate value: " + value,
                        request.getLocale()))
                .orElseGet(() -> messageSource.getMessage(
                        AppConstants.ErrorMessages.DATA_INTEGRITY_GENERIC,
                        null,
                        "Data integrity violation",
                        request.getLocale()));

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ApiResponse.error(HttpStatus.CONFLICT, message));
    }

    private Optional<String> extractDuplicatedValue(String message) {
        if (message == null || !message.contains(DUPLICATE_ENTRY_PREFIX)) {
            return Optional.empty();
        }

        try {
            int start = message.indexOf(DUPLICATE_ENTRY_PREFIX) + DUPLICATE_ENTRY_PREFIX.length();
            int end = message.indexOf("'", start);
            return Optional.of(message.substring(start, end));
        } catch (Exception e) {
            log.warn("Error parsing duplicate value", e);
            return Optional.empty();
        }
    }

    // ================== Manejo de excepciones personalizadas ================== //
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleResourceNotFound(
            ResourceNotFoundException ex, WebRequest request) {

        String message = messageSource.getMessage(
                ex.getKey(),
                ex.getArgs(),
                ex.getMessage(),
                request.getLocale());

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(HttpStatus.NOT_FOUND, message));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponse<Void>> handleBadRequest(
            BadRequestException ex, WebRequest request) {

        String message = messageSource.getMessage(
                ex.getKey(),
                ex.getArgs(),
                ex.getMessage(),
                request.getLocale());

        return ResponseEntity.badRequest()
                .body(ApiResponse.error(HttpStatus.BAD_REQUEST, message));
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ApiResponse<Void>> handleUnauthorized(
            UnauthorizedException ex, WebRequest request) {

        String message = messageSource.getMessage(
                ex.getKey(),
                ex.getArgs(),
                ex.getMessage(),
                request.getLocale());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ApiResponse.error(HttpStatus.UNAUTHORIZED, message));
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiResponse<Void>> handleAuthenticationException(
            AuthenticationException ex, WebRequest request) {

        String userMessage = messageSource.getMessage(
                ex.getErrorCode(),
                null,
                ex.getMessage(),
                request.getLocale());

        return ResponseEntity.status(ex.getHttpStatus())
                .body(ApiResponse.error(ex.getHttpStatus(), userMessage));
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleInvalidTokenException(
            InvalidTokenException ex, WebRequest request) {

        String userMessage = messageSource.getMessage(
                ex.getErrorCode(),
                null,
                ex.getMessage(),
                request.getLocale());

        Map<String, String> details = new HashMap<>();
        details.put("error_type", ex.getErrorType().getErrorCode());
        details.put("description", ex.getErrorType().getDescription());

        return ResponseEntity.status(ex.getHttpStatus())
                .body(ApiResponse.error(ex.getHttpStatus(), userMessage, details));
    }

    @ExceptionHandler(DefaultAvatarLoadException.class)
    public ResponseEntity<ApiResponse<Void>> handleDefaultAvatarLoadException(
            DefaultAvatarLoadException ex, WebRequest request) {

        String message = messageSource.getMessage(
                "error.avatar.default.load",
                null,
                "No se pudo cargar la imagen de perfil por defecto",
                request.getLocale()
        );

        log.warn("Error en carga de avatar por defecto: {}", ex.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        message
                ));
    }

    @ExceptionHandler(AvatarProcessingException.class)
    public ResponseEntity<ApiResponse<Void>> handleAvatarProcessingException(
            AvatarProcessingException ex, WebRequest request) {
        String message = messageSource.getMessage(
                "error.avatar.processing",
                new Object[]{ex.getMessage()},
                "Error al procesar el avatar: " + ex.getMessage(),
                request.getLocale()
        );
        log.error("Error al procesar avatar: {}", ex.getMessage(), ex);
        return ResponseEntity.badRequest()
                .body(ApiResponse.error(HttpStatus.BAD_REQUEST, message));
    }

    @ExceptionHandler(AvatarStorageException.class)
    public ResponseEntity<ApiResponse<Void>> handleAvatarStorageException(
            AvatarStorageException ex, WebRequest request) {
        String message = messageSource.getMessage(
                "error.avatar.storage",
                new Object[]{ex.getMessage()},
                "Error al almacenar el avatar: " + ex.getMessage(),
                request.getLocale()
        );

        log.error("Error de almacenamiento de avatar: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, message));
    }

    // ================== Manejo de excepciones técnicas ================== //
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<Void>> handleMessageNotReadable(
            HttpMessageNotReadableException ex, WebRequest request) {

        String message = messageSource.getMessage(
                AppConstants.ErrorMessages.HEADER_FORMAT_ERROR,
                null,
                "Malformed request",
                request.getLocale());

        return ResponseEntity.badRequest()
                .body(ApiResponse.error(HttpStatus.BAD_REQUEST, message));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGlobalException(
            Exception ex, WebRequest request) {

        log.error("Unhandled exception: ", ex);

        String message = messageSource.getMessage(
                AppConstants.ErrorMessages.INTERNAL_SERVER_ERROR,
                null,
                "Internal server error",
                request.getLocale());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, message));
    }
}

