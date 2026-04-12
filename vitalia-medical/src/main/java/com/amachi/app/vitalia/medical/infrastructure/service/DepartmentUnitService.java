package com.amachi.app.vitalia.medical.infrastructure.service;

import com.amachi.app.core.common.service.GenericService;
import com.amachi.app.vitalia.medical.infrastructure.dto.search.DepartmentUnitSearchDto;
import com.amachi.app.vitalia.medical.infrastructure.entity.DepartmentUnit;

/**
 * Interfaz de servicio para la gestion de unidades hospitalarias.
 */
public interface DepartmentUnitService extends GenericService<DepartmentUnit, DepartmentUnitSearchDto> {
}
