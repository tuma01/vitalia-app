package com.amachi.app.core.common.enums;

import lombok.Getter;

/**
 * ≡ƒö╣ Define los diferentes tipos de visitas de pacientes a un centro de atenci├│n m├⌐dica.
 */
@Getter
public enum VisitTypeEnum {
    /**
     * Representa una visita ambulatoria donde el paciente recibe atenci├│n sin ser ingresado al hospital.
     */
    CONSULTA_EXTERNA,

    /**
     * Representa una visita al Departamento de Emergencias para atenci├│n m├⌐dica urgente.
     */
    VISITA_EMERGENCIA,

    /**
     * Representa una visita de paciente hospitalizado donde el paciente es formalmente ingresado al hospital.
     */
    INGRESO_HOSPITALARIO,

    /**
     * Representa una visita espec├¡ficamente para un procedimiento quir├║rgico programado.
     */
    VISITA_QUIRURGICA,

    /**
     * Representa una visita con fines de diagn├│stico (Rayos X, Laboratorio, etc.).
     */
    VISITA_DIAGNOSTICA,

    /**
     * Representa una visita para atenci├│n de seguimiento despu├⌐s de un tratamiento previo.
     */
    VISITA_SEGUIMIENTO,

    /**
     * Representa una consulta virtual o remota.
     */
    TELECONSULTA,

    /**
     * Representa una visita con fines puramente administrativos.
     */
    VISITA_ADMINISTRATIVA,

    /**
     * Un tipo de visita general o no especificado.
     */
    OTRO
}
