package com.amachi.app.vitalia.medical.common.enums;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Tipos de registro en la historia clínica según el contexto de la atención.
 */
@Schema(description = "Tipos de registros clínicos")
public enum MedicalRecordType {
    // Conceptos Base
    @Schema(description = "Historia clínica inicial")
    INITIAL,
    @Schema(description = "Control o seguimiento")
    FOLLOW_UP,
    @Schema(description = "Atención de emergencia")
    EMERGENCY,
    @Schema(description = "Consulta de urgencias")
    URGENT_CARE,
    @Schema(description = "Chequeo rutinario")
    ROUTINE_CHECK,

    // Especialidades Principales
    @Schema(description = "Medicina general")
    GENERAL_MEDICINE,
    @Schema(description = "Pediatría")
    PEDIATRICS,
    @Schema(description = "Cardiología")
    CARDIOLOGY,
    @Schema(description = "Ginecología")
    GYNECOLOGY,

    // Reportes Específicos
    @Schema(description = "Resultados de laboratorio")
    LABORATORY,
    @Schema(description = "Reporte de imagenología")
    IMAGING,
    @Schema(description = "Nota de alta hospitalaria")
    DISCHARGE_SUMMARY,

    @Schema(description = "Otro tipo no especificado")
    OTHER
}
