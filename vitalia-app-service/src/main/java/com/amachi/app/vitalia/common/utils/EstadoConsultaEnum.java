package com.amachi.app.vitalia.common.utils;

import lombok.Getter;

@Getter
public enum EstadoConsultaEnum {
    PROGRAMADA("Programada", "La consulta está programada pero aún no ha comenzado."),
    EN_ESPERA("En espera", "El paciente ha llegado y está esperando para ser atendido."),
    EN_CURSO("En curso", "La consulta está actualmente en progreso."),
    COMPLETADA("Completada", "La consulta ha terminado y se ha proporcionado atención médica."),
    CANCELADA("Cancelada", "La consulta ha sido cancelada y no se llevará a cabo."),
    REPROGRAMADA("Reprogramada", "La consulta ha sido reprogramada para una fecha y hora diferentes."),
    NO_ASISTIO("No asistió", "El paciente no asistió a la consulta programada.");

    private final String nombre;
    private final String descripcion;

    EstadoConsultaEnum(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }
}
