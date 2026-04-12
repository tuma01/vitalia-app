package com.amachi.app.vitalia.medical.common.enums;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

/**
 * Estados del flujo de consulta médica.
 */
@Getter
@Schema(description = "Estados de la consulta médica")
public enum EstadoConsultaEnum {
    @Schema(description = "Cita programada con antelación")
    PROGRAMADA("Programada", "La consulta está programada pero aún no ha comenzado."),
    @Schema(description = "Paciente en sala de espera")
    EN_ESPERA("En espera", "El paciente ha llegado y está esperando para ser atendido."),
    @Schema(description = "Atención médica activa")
    EN_CURSO("En curso", "La consulta está actualmente en progreso."),
    @Schema(description = "Atención finalizada")
    COMPLETADA("Completada", "La consulta ha terminado y se ha proporcionado atención médica."),
    @Schema(description = "Cita anulada")
    CANCELADA("Cancelada", "La consulta ha sido cancelada y no se llevará a cabo."),
    @Schema(description = "Cita cambiada a otra fecha")
    REPROGRAMADA("Reprogramada", "La consulta ha sido reprogramada para una fecha y hora diferentes."),
    @Schema(description = "El paciente no se presentó")
    NO_ASISTIO("No asistió", "El paciente no asistió a la consulta programada.");

    private final String nombre;
    private final String descripcion;

    EstadoConsultaEnum(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }
}
