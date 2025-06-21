package com.amachi.app.vitalia.common.exception;

import com.amachi.app.vitalia.common.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.dao.DataIntegrityViolationException;


@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse<?>> handleDataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest request) {
        String userMessage; // Mensaje final para el usuario
        String defaultGenericMessage = "Error de integridad de datos. Un registro con datos duplicados ya existe."; // Mensaje genérico de fallback

        Throwable rootCause = ex.getRootCause();
        String rootCauseMessage = (rootCause != null) ? rootCause.getMessage() : ex.getMessage();

        // Loguear el error técnico completo
        log.error("DataIntegrityViolationException caught. Root cause message: {}", rootCauseMessage, ex);

        // --- Intentar parsear el valor duplicado del mensaje ---
        String duplicatedValue = null;
        String duplicateEntryPrefix = "Duplicate entry '"; // Prefijo común en mensajes de MySQL

        if (rootCauseMessage != null && rootCauseMessage.contains(duplicateEntryPrefix)) {
            try {
                int startIndex = rootCauseMessage.indexOf(duplicateEntryPrefix) + duplicateEntryPrefix.length();
                int endIndex = rootCauseMessage.indexOf("'", startIndex); // Buscar la siguiente comilla simple

                if (startIndex > duplicateEntryPrefix.length() -1 && endIndex != -1) { // Asegurarse de que se encontraron ambas comillas
                    duplicatedValue = rootCauseMessage.substring(startIndex, endIndex);
                    log.debug("Extracted duplicated value: {}", duplicatedValue);
                }
            } catch (Exception parseException) {
                // Si algo sale mal durante el parsing (ej. formato inesperado)
                log.warn("Failed to parse duplicated value from DB error message: {}", rootCauseMessage, parseException);
                duplicatedValue = null; // Asegurarse de que duplicatedValue sea null para usar el mensaje genérico
            }
        }
        // --- Fin Lógica de Parsing ---

        // Construir el mensaje para el usuario
        if (duplicatedValue != null) {
            // Usar una clave de mensaje que espera un argumento (el valor duplicado)
            String messageKey = "error.data.integrity.violation.value"; // Ej: "El valor '{0}' ya existe."
            // Obtener el mensaje localizado, pasando el valor como argumento
            userMessage = messageSource.getMessage(messageKey, new Object[]{duplicatedValue}, defaultGenericMessage, request.getLocale());
            log.debug("Mensaje construido con valor: {}", userMessage);
        } else {
            // Si no se pudo extraer el valor (parsing falló o formato diferente), usar el mensaje genérico
            String messageKey = "error.data.integrity.violation.generic"; // Clave para el mensaje genérico
            userMessage = messageSource.getMessage(messageKey, null, defaultGenericMessage, request.getLocale());
        }
        ApiResponse<?> response = new ApiResponse<>(HttpStatus.CONFLICT.value(), userMessage, null);
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        String message = messageSource.getMessage(ex.getKey(), ex.getArgs(), request.getLocale());
        ApiResponse<?> response = new ApiResponse<>(HttpStatus.NOT_FOUND.value(), message, null);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponse<?>> handleBadRequestException(BadRequestException ex, WebRequest request) {
        String message = messageSource.getMessage(ex.getKey(), ex.getArgs(), request.getLocale());
        ApiResponse<?> response = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), message, null);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleGlobalException(Exception ex, WebRequest request) {
        String message = messageSource.getMessage("error.internal.server", null, request.getLocale());
        ApiResponse<?> response = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), message, null);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ApiResponse<?>> handleUnauthorizedException(UnauthorizedException ex, WebRequest request) {
        String message = messageSource.getMessage(ex.getKey(), ex.getArgs(), request.getLocale());
        ApiResponse<?> response = new ApiResponse<>(HttpStatus.UNAUTHORIZED.value(), message, null);
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }
}

//    @ExceptionHandler(ResourceNotFoundException.class)
//    public ResponseEntity<String> resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
////        ErrorMessage errorMessage = errorService.generateErrorMessage(HttpStatus.NOT_FOUND.value(), ex.getMessage(), request.getDescription(false));
//        return new ResponseEntity<>("errorMessage", HttpStatus.NOT_FOUND);
//    }

//    @ExceptionHandler(ResourceNotFoundException.class)
//    public ResponseEntity<ErrorMessage> resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
//        ErrorMessage errorMessage = errorService.generateErrorMessage(HttpStatus.NOT_FOUND.value(), ex.getMessage(), request.getDescription(false));
//        return new ResponseEntity<ErrorMessage>(errorMessage, HttpStatus.NOT_FOUND);
//    }

