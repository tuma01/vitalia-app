package com.amachi.app.vitalia.medical.nurse.service;

import com.amachi.app.core.common.service.GenericService;
import com.amachi.app.vitalia.medical.nurse.dto.search.NurseSearchDto;
import com.amachi.app.vitalia.medical.nurse.entity.Nurse;

/**
 * Interfaz de servicio para la gestion de enfermeria.
 */
public interface NurseService extends GenericService<Nurse, NurseSearchDto> {
}
