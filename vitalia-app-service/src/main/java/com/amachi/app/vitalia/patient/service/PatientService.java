package com.amachi.app.vitalia.patient.service;

import com.amachi.app.vitalia.common.service.GenericService;
import com.amachi.app.vitalia.patient.dto.search.PatientSearchDto;
import com.amachi.app.vitalia.patient.entity.Patient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public interface PatientService extends GenericService<Patient, PatientSearchDto> {

    /**
     * Registers or updates the relationship between a patient and a hospital.
     * If the patient is new, creates it. If already exists, updates the hospital list
     * and assigns the main hospital if not yet set.
     *
     * @param patientData   Basic patient data (name, ID, etc.)
     * @param hospitalCode  The code of the hospital where the patient is being attended
     * @return The created or updated Patient instance
     */
    @Transactional
    Patient registerPatientVisit(Patient patientData, String hospitalCode);
}
