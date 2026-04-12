package com.amachi.app.vitalia.medical.consultation.service;

import com.amachi.app.core.common.service.GenericService;
import com.amachi.app.vitalia.medical.consultation.dto.search.ConsultationSearchDto;
import com.amachi.app.vitalia.medical.consultation.entity.Consultation;

import java.util.List;

/**
 * Interface ConsultationService (SaaS Elite Tier).
 */
public interface ConsultationService extends GenericService<Consultation, ConsultationSearchDto> {

    /**
     * Retrieves consultations by medical history ID.
     * @param medicalHistoryId The ID of the medical history.
     * @return List of consultations.
     */
    List<Consultation> getByMedicalHistoryId(Long medicalHistoryId);
}
