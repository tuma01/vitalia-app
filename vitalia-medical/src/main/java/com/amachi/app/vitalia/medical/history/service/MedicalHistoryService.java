package com.amachi.app.vitalia.medical.history.service;

import com.amachi.app.core.common.service.GenericService;
import com.amachi.app.vitalia.medical.history.dto.search.MedicalHistorySearchDto;
import com.amachi.app.vitalia.medical.history.entity.MedicalHistory;

import java.util.Optional;

/**
 * Contrato de servicio para la gestión longitudinal del Expediente Clínico (EHR).
 */
public interface MedicalHistoryService extends GenericService<MedicalHistory, MedicalHistorySearchDto> {

    /**
     * Obtiene la historia clínica oficial de un paciente.
     * @param patientId ID del paciente.
     * @return Historia clínica.
     */
    Optional<MedicalHistory> getByPatientId(Long patientId);
}
