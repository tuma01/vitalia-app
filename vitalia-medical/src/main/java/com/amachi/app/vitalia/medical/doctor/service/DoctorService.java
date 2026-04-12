package com.amachi.app.vitalia.medical.doctor.service;

import com.amachi.app.core.common.service.GenericService;
import com.amachi.app.vitalia.medical.doctor.dto.search.DoctorSearchDto;
import com.amachi.app.vitalia.medical.doctor.entity.Doctor;

/**
 * Interfaz de servicio para la gestion de medicos.
 */
public interface DoctorService extends GenericService<Doctor, DoctorSearchDto> {
}
