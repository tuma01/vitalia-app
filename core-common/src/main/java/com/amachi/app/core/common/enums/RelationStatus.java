package com.amachi.app.core.common.enums;

public enum RelationStatus {
    ACTIVE,        // Relación vigente y en uso
    INACTIVE,      // Relación finalizada (pero mantenida en histórico)
    SUSPENDED,     // Pausada temporalmente (ej: licencia médica, contrato congelado)
    PENDING,       // Relación aún no confirmada (ej: proceso de registro/admisión)
    TERMINATED,    // Relación cerrada definitivamente (ej: despido, alta médica definitiva)
    EXPIRED,       // Relación caducada automáticamente (ej: contrato por tiempo limitado)
    ARCHIVED,       // Relación guardada solo para histórico (no se usa en procesos activos)
    LOCKED,       // Bloqueada por múltiples intentos fallidos
    DEACTIVATED   // Desactivada permanentemente
}
