package com.amachi.app.vitalia.medical.common.enums;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Clasificación clínica del tipo de cita para la gestión de agendas hospitalarias.
 */
@Schema(description = "Categorización funcional de la cita médica")
public enum AppointmentType {
    
    @Schema(description = "Consulta médica inicial o de primer contacto")
    CONSULTATION,
    
    @Schema(description = "Cita de seguimiento o control posterior")
    FOLLOW_UP,
    
    @Schema(description = "Atención por cuadro de urgencia no vital")
    EMERGENCY,
    
    @Schema(description = "Control rutinario de patologías crónicas")
    CONTROL,
    
    @Schema(description = "Cita para revisión de resultados de laboratorio/imagen")
    REVIEW,
    
    @Schema(description = "Procedimiento médico-quirúrgico menor")
    PROCEDURE
}
