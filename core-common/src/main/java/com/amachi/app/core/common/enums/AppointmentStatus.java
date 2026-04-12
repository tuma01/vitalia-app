package com.amachi.app.core.common.enums;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Estados operativos de una cita medica (Workflow DSQ).
 */
@Schema(description = "Ciclo de vida de una cita medica")
public enum AppointmentStatus {
    @Schema(description = "Cita programada pendiente de confirmacion")
    PENDING,
    @Schema(description = "Cita confirmada por el paciente")
    CONFIRMED,
    @Schema(description = "Paciente en consultorio")
    IN_PROGRESS,
    @Schema(description = "Consulta finalizada")
    COMPLETED,
    @Schema(description = "Cita anulada por el paciente o clinica")
    CANCELLED,
    @Schema(description = "El paciente no se presento a la cita")
    NO_SHOW
}
