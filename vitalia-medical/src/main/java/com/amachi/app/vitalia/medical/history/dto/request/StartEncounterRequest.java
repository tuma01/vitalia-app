package com.amachi.app.vitalia.medical.history.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * ≡ƒôô Solicitud de inicio de encuentro cl├¡nico derivado de una cita previa.
 */
@NoArgsConstructor @AllArgsConstructor @Builder
@Schema(description = "Solicitud para iniciar el acto médico")
public class StartEncounterRequest {

    @NotNull(message = "El ID de la cita es obligatorio")
    @Schema(description = "ID de la cita m├⌐dica (Appointment)", example = "105")
    private Long appointmentId;

    @Schema(description = "Motivo de inicio (opcional)", example = "Paciente presente en consultorio")
    private String startNote;

    // Manual Accessors
    public Long getAppointmentId() { return appointmentId; }
    public void setAppointmentId(Long appointmentId) { this.appointmentId = appointmentId; }
    public String getStartNote() { return startNote; }
    public void setStartNote(String startNote) { this.startNote = startNote; }
}
