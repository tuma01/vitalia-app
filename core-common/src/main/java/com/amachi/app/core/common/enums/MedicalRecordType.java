package com.amachi.app.core.common.enums;

/**
 * ≡ƒö╣ Diferentes tipos de registros o notas cl├¡nicas en el historial del paciente.
 */
public enum MedicalRecordType {
    // 🔹 Origen o Contexto
    INITIAL,            // Historia cl├¡nica de apertura
    FOLLOW_UP,          // Control/Seguimiento
    EMERGENCY,          // Nota de emergencia
    URGENT_CARE,        // Atenci├│n en urgencia
    ROUTINE_CHECK,      // Chequeo preventivo rutinario

    // 🔹 Especialidades comunes
    GENERAL_MEDICINE,
    PEDIATRICS,
    CARDIOLOGY,
    DERMATOLOGY,
    GASTROENTEROLOGY,
    GYNECOLOGY,
    NEUROLOGY,
    ONCOLOGY,
    ORTHOPEDICS,
    PSYCHIATRY,
    PULMONOLOGY,
    UROLOGY,

    // 🔹 Procedimientos y Documentaci├│n
    SURGICAL,           // Reporte m├⌐dico quir├║rgico
    LABORATORY,         // Resultados de anal├¡tica
    IMAGING,            // Rayos X, MRI, CT
    PHYSICAL_THERAPY,   // Rehabilitaci├│n f├¡sica
    VACCINATION,        // Registro de inmunizaci├│n
    DISCHARGE_SUMMARY,  // Epicrisis / Resumen de alta

    // 🔹 Otros
    CONSULTATION,       // Interconsulta entre especialistas
    TELEMEDICINE,       // Consulta remota
    OTHER               // Nota no categorizada
}
