package com.amachi.app.vitalia.medical.patient.service;

import com.amachi.app.core.common.service.GenericService;
import com.amachi.app.vitalia.medical.patient.dto.search.PatientSearchDto;
import com.amachi.app.vitalia.medical.patient.entity.Patient;

/**
 * Interfaz de servicio central para la gestion de pacientes.
 */
public interface PatientService extends GenericService<Patient, PatientSearchDto> {
}
