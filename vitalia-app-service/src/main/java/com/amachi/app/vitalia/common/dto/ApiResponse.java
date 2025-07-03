package com.amachi.app.vitalia.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.function.Function;

/**
 * Representación estándar de todas las respuestas de la API.
 * @param <T> Tipo del payload de datos
 */
@Getter
@ToString
@EqualsAndHashCode
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiResponse<T> {

    @Schema(
            description = "Código de estado HTTP",
            example = "200",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private int status;

    @Schema(
            description = "Mensaje descriptivo de la respuesta",
            example = "Operación completada exitosamente",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String message;

    @Schema(
            description = "Datos de respuesta",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private T data;

    // ============== Factory Methods ============== //

    public static <T> ApiResponse<T> success(T data) {
        return success(data, "Operación exitosa");
    }

    public static <T> ApiResponse<T> success(T data, String message) {
        return ApiResponse.<T>builder()
                .status(HttpStatus.OK.value())
                .message(message)
                .data(data)
                .build();
    }

    public static <T> ApiResponse<T> error(HttpStatus status, String message) {
        return error(status, message, null);
    }

    public static <T> ApiResponse<T> error(HttpStatus status, String message, T errorDetails) {
        return ApiResponse.<T>builder()
                .status(status.value())
                .message(message)
                .data(errorDetails)
                .build();
    }

    // ============== Common Responses ============== //

    public static <T> ApiResponse<T> notFound(String resourceName) {
        return error(HttpStatus.NOT_FOUND, resourceName + " no encontrado");
    }

    public static <T> ApiResponse<T> badRequest(String message) {
        return error(HttpStatus.BAD_REQUEST, message);
    }

    public static <T> ApiResponse<T> unauthorized(String message) {
        return error(HttpStatus.UNAUTHORIZED, message);
    }

    public static <T> ApiResponse<T> forbidden(String message) {
        return error(HttpStatus.FORBIDDEN, message);
    }

    public static <T> ApiResponse<T> conflict(String message) {
        return error(HttpStatus.CONFLICT, message);
    }

    // ============== Transformations ============== //

    /**
     * Transforma los datos de la respuesta aplicando la función mapper,
     * manteniendo el mismo estado y mensaje.
     *
     * @param <U> Nuevo tipo de dato para la respuesta
     * @param mapper Función de transformación de T a U
     * @return Nueva instancia de ApiResponse con los datos transformados
     */
    public <U> ApiResponse<U> map(Function<? super T, ? extends U> mapper) {
        return ApiResponse.<U>builder()
                .status(this.status)
                .message(this.message)
                .data(mapper.apply(this.data))
                .build();
    }
}
