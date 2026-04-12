package com.amachi.app.vitalia.medical.hospitalization.service;

import com.amachi.app.core.common.service.GenericService;
import com.amachi.app.vitalia.medical.hospitalization.dto.search.HospitalizationSearchDto;
import com.amachi.app.vitalia.medical.hospitalization.entity.Hospitalization;

/**
 * Service interface for Hospitalization control.
 */
public interface HospitalizationService extends GenericService<Hospitalization, HospitalizationSearchDto> {

    /**
     * Officially discharge a patient.
     * @param id Hospitalization ID.
     * @param dischargeSummary Discharge summary.
     * @return The updated record.
     */
    Hospitalization dischargePatient(Long id, String dischargeSummary);
}
