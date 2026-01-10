package com.amachi.app.core.common.enums;

import lombok.Getter;

/**
 * Enum que clasifica los diferentes tipos de errores relacionados con tokens.
 * Proporciona códigos de error específicos para un manejo más granular.
 */
@Getter
public enum TokenErrorType {
    // Errores de formato/estructura
    MALFORMED_TOKEN("El token no tiene un formato válido", "security.token.malformed_token"),
    INVALID_SIGNATURE("La firma del token no es válida", "security.token.invalid_signature"),
    UNSUPPORTED_TOKEN("Tipo de token no soportado", "security.token.unsupported_token"),
    INVALID_TOKEN("El token no existe o es inválido", "security.token.invalid_token"),

    // Errores de tiempo/validez
    EXPIRED_TOKEN("El token ha expirado", "security.token.expired"),
    NOT_YET_VALID("El token no es válido aún (fecha de inicio en el futuro)", "security.token.not_yet_valid"),
    ISSUED_AT_MISMATCH("El token fue emitido después de lo esperado", "security.token.issued_at_mismatch"),

    // Errores de claims/datos
    MISSING_CLAIM("Falta un claim requerido en el token", "security.token.missing_claim"),
    INVALID_CLAIM("Claim contiene un valor inválido", "security.token.invalid_claim"),
    AUDIENCE_MISMATCH("El token no está destinado a esta audiencia", "security.token.audience_mismatch"),
    ISSUER_MISMATCH("El emisor del token no es reconocido", "security.token.issuer_mismatch"),

    // Errores de uso/contexto
    WRONG_TOKEN_TYPE("El tipo de token no coincide con el esperado", "security.token.wrong_token_type"),
    REVOKED_TOKEN("El token ha sido revocado", "security.token.revoked_token"),
    REPLAYED_TOKEN("Intento de reutilización de token detectado", "security.token.replayed_token");

    private final String description; // mensaje por defecto o fallback
    private final String messageKey;  // clave exacta en properties

    TokenErrorType(String description, String messageKey) {
        this.description = description;
        this.messageKey = messageKey;
    }
}
