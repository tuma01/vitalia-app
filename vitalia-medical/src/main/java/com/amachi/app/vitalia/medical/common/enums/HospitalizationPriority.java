package com.amachi.app.vitalia.medical.common.enums;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Nivel de urgencia clínica para el ingreso hospitalario (Control de Triage).
 */
@Schema(description = "Nivel de criticidad para la internación")
public enum HospitalizationPriority {
    
    @Schema(description = "Ingreso programado sin urgencia vital inmediata")
    SELECTIVE,
    
    @Schema(description = "Ingreso requerido por cuadro agudo estable")
    URGENT,
    
    @Schema(description = "Requiere monitoreo continuo y soporte vital")
    CRITICAL,
    
    @Schema(description = "Estado vegetativo o vigilancia crónica")
    PALLIATIVE
}
