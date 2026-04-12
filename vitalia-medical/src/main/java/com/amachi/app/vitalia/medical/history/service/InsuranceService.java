package com.amachi.app.vitalia.medical.history.service;

import com.amachi.app.core.common.service.GenericService;
import com.amachi.app.vitalia.medical.history.dto.search.InsuranceSearchDto;
import com.amachi.app.vitalia.medical.history.entity.Insurance;

import java.util.List;
import java.util.Optional;

/**
 * Contrato de servicio para la gestión de coberturas de seguros médicos.
 */
public interface InsuranceService extends GenericService<Insurance, InsuranceSearchDto> {

    /**
     * Obtiene todos los seguros de una historia clínica.
     * @param medicalHistoryId ID de la historia clínica.
     * @return Lista de seguros.
     */
    List<Insurance> getByMedicalHistoryId(Long medicalHistoryId);

    /**
     * Obtiene el seguro activo de un paciente.
     * @param medicalHistoryId ID de la historia clínica.
     * @return Seguro activo.
     */
    Optional<Insurance> getActiveInsurance(Long medicalHistoryId);
}
