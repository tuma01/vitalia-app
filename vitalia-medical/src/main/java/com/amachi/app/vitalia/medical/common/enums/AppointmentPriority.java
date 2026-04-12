package com.amachi.app.vitalia.medical.common.enums;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Nivel de prioridad operativa de la cita para el triaje y la gestión de flujo.
 */
@Schema(description = "Nivel de urgencia o prioridad de la cita")
public enum AppointmentPriority {
    
    @Schema(description = "Cita rutinaria sin urgencia clínica")
    NORMAL,
    
    @Schema(description = "Cita con requerimiento de atención pronta por síntomas menores")
    URGENT,
    
    @Schema(description = "Cita con prioridad administrativa o de alto nivel")
    VIP,
    
    @Schema(description = "Prioridad crítica (Asignación inmediata por equipo de triaje)")
    CRITICAL
}
