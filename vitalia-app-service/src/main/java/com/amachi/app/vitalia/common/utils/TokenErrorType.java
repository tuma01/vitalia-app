package com.amachi.app.vitalia.common.utils;

import lombok.Getter;

/**
 * Enum que clasifica los diferentes tipos de errores relacionados con tokens.
 * Proporciona códigos de error específicos para un manejo más granular.
 */
@Getter
public enum TokenErrorType {
    // Errores de formato/estructura
    MALFORMED_TOKEN("El token no tiene un formato válido"),
    INVALID_SIGNATURE("La firma del token no es válida"),
    UNSUPPORTED_TOKEN("Tipo de token no soportado"),
    INVALID_TOKEN("El token no existe o es inválido"),

    // Errores de tiempo/validez
    EXPIRED_TOKEN("El token ha expirado"),
    NOT_YET_VALID("El token no es válido aún (fecha de inicio en el futuro)"),
    ISSUED_AT_MISMATCH("El token fue emitido después de lo esperado"),

    // Errores de claims/datos
    MISSING_CLAIM("Falta un claim requerido en el token"),
    INVALID_CLAIM("Claim contiene un valor inválido"),
    AUDIENCE_MISMATCH("El token no está destinado a esta audiencia"),
    ISSUER_MISMATCH("El emisor del token no es reconocido"),

    // Errores de uso/contexto
    WRONG_TOKEN_TYPE("El tipo de token no coincide con el esperado"),
    REVOKED_TOKEN("El token ha sido revocado"),
    REPLAYED_TOKEN("Intento de reutilización de token detectado");

    private final String description;

    TokenErrorType(String description) {
        this.description = description;
    }

    /**
     * Obtiene el código de error en formato snake_case (para APIs)
     */
    public String getErrorCode() {
        return this.name().toLowerCase();
    }
}
