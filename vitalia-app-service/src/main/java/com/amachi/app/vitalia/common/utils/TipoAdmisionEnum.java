package com.amachi.app.vitalia.common.utils;

public enum TipoAdmisionEnum {
    /**
     * Represents an admission through the Emergency Department.
     * El paciente ingresa a través del departamento de emergencias.
     */
    URGENCIA,

    /**
     * Represents a planned admission, typically for scheduled procedures or treatments.
     * Ingreso planificado, generalmente para procedimientos o tratamientos programados.
     */
    ELECTIVA,

    /**
     * Represents an admission where the patient is transferred from another healthcare facility.
     * Ingreso donde el paciente es trasladado desde otra institución de salud.
     */
    TRASLADO,

    /**
     * Represents an admission specifically for observation, usually for a short period.
     * Ingreso específico para observación, generalmente por un período corto.
     */
    OBSERVACION,

    /**
     * Other types of admission not covered by the specific categories.
     * Otros tipos de admisión no cubiertos por las categorías específicas.
     */
    OTRO
}
