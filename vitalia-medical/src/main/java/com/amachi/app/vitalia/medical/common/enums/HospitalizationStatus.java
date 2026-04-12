package com.amachi.app.vitalia.medical.common.enums;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Estados integrales del ciclo de vida de una hospitalización (SaaS Elite Tier).
 * Alineado con estándares clínicos FHIR y flujos operativos de Vitalia.
 */
@Schema(description = "Ciclo de vida de la hospitalización")
public enum HospitalizationStatus {
    @Schema(description = "Paciente con ingreso programado o en proceso")
    PLANNED,

    @Schema(description = "Paciente formalmente ingresado (Check-in clínico)")
    ADMITTED,

    @Schema(description = "Estancia activa en unidad de internamiento")
    ACTIVE,

    @Schema(description = "Paciente trasladado a otro servicio, unidad o centro")
    TRANSFERRED,

    @Schema(description = "Paciente con alta médica y administrativa realizada")
    DISCHARGED,

    @Schema(description = "Paciente se retiró del centro sin autorización (Fuga)")
    ABSCONDED,

    @Schema(description = "Fallecimiento durante la estancia hospitalaria")
    DECEASED,

    @Schema(description = "Registro de hospitalización anulado por error administrativo")
    CANCELLED
}
