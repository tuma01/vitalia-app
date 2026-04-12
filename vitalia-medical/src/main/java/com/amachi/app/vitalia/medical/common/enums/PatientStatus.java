package com.amachi.app.vitalia.medical.common.enums;

import lombok.Getter;

/**
 * Redirección/Espejo del Enum centralizado de PatientStatus.
 * Se mantiene por compatibilidad de paquetes locales pero consume el estándar central.
 */
@Getter
public enum PatientStatus {
    ACTIVE("Paciente activo"),
    INACTIVE("Paciente inactivo"),
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
