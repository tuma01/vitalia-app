package com.amachi.app.vitalia.medical.common.enums;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Clasificación del tipo de ingreso hospitalario.
 */
@Schema(description = "Tipos de admisión hospitalaria")
public enum AdmissionType {
    @Schema(description = "Ingreso por área de urgencias")
    EMERGENCY,
    @Schema(description = "Ingreso programado por cirugía o tratamiento")
    PLANNED,
    @Schema(description = "Ingreso para observación corta")
    OBSERVATION,
    @Schema(description = "Ingreso por consulta externa")
    OUTPATIENT
}
