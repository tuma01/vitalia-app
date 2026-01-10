package com.amachi.app.core.common.enums;

import lombok.Getter;

@Getter
public enum PatientStatus {
    ADMITTED("Paciente admitido"),
    DISCHARGED("Paciente dado de alta"),
    IN_TREATMENT("Paciente en tratamiento"),
    WAITING("Paciente en espera"),
    CHECKED_OUT("Paciente dado de baja"),
    EMERGENCY("Paciente en emergencia"),
    RECOVERY("Paciente en recuperación"),
    DECEASED("Paciente fallecido");

    public final String label;

    PatientStatus(String label) {
        this.label = label;
    }
}
