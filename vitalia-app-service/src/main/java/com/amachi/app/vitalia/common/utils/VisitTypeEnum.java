package com.amachi.app.vitalia.common.utils;

import lombok.Getter;

@Getter
//@AllArgsConstructor
public enum VisitTypeEnum {
    // Define los diferentes tipos de visitas de pacientes a un centro de atención médica.

    /**
     * Representa una visita ambulatoria donde el paciente recibe atención sin ser ingresado al hospital.
     * Ejemplos: chequeos de rutina, consultas con especialistas, citas de seguimiento.
     */
    CONSULTA_EXTERNA, // OUTPATIENT_VISIT

    /**
     * Representa una visita al Departamento de Emergencias (Urgencias) para atención médica urgente.
     * Los pacientes pueden ser ingresados al hospital después de una visita de emergencia.
     */
    VISITA_EMERGENCIA, // EMERGENCY_VISIT

    /**
     * Representa una visita de paciente hospitalizado donde el paciente es formalmente ingresado al hospital
     * para tratamiento, monitoreo o cirugía, que típicamente implica una estadía de una noche o más.
     * Aquí es donde se clasificarían los registros de 'Hospitalización'.
     */
    INGRESO_HOSPITALARIO, // INPATIENT_VISIT

    /**
     * Representa una visita específicamente para un procedimiento quirúrgico programado que puede requerir
     * o no una estadía de una noche, pero es distinta de un ingreso hospitalario general.
     * También podría incluir procedimientos menores realizados en un entorno ambulatorio.
     */
    VISITA_QUIRURGICA, // SURGICAL_VISIT

    /**
     * Representa una visita con fines de diagnóstico, como estudios de imagen (Rayos X, Resonancia Magnética)
     * o pruebas de laboratorio, donde el propósito principal no es la consulta o el tratamiento.
     */
    VISITA_DIAGNOSTICA, // DIAGNOSTIC_VISIT

    /**
     * Representa una visita para atención de seguimiento después de un tratamiento previo, cirugía u hospitalización.
     * Es típicamente menos extensa que una consulta inicial.
     */
    VISITA_SEGUIMIENTO, // FOLLOW_UP_VISIT

    /**
     * Representa el traslado de un paciente desde otro centro de atención médica (por ejemplo, otro hospital,
     * residencia de ancianos) a la instalación actual.
     */
    TRASLADO_ENTRADA, // TRANSFER_IN_VISIT

    /**
     * Representa una consulta virtual o remota realizada a través de tecnología de telecomunicaciones.
     */
    TELECONSULTA, // TELEHEALTH_VISIT

    /**
     * Representa una visita con fines puramente administrativos, como trámites, consultas de facturación
     * o recogida de expedientes médicos, sin atención clínica directa.
     */
    VISITA_ADMINISTRATIVA, // ADMINISTRATIVE_VISIT

    /**
     * Un tipo de visita general o no especificado, utilizado cuando una categoría más específica no es aplicable
     * o aún no se ha determinado. Usar con moderación si es posible.
     */
    OTRO // OTHER
}