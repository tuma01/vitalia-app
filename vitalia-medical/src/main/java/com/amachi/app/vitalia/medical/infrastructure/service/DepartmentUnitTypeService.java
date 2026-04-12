package com.amachi.app.vitalia.medical.infrastructure.service;

import com.amachi.app.core.common.service.GenericService;
import com.amachi.app.vitalia.medical.infrastructure.dto.search.DepartmentUnitTypeSearchDto;
import com.amachi.app.vitalia.medical.infrastructure.entity.DepartmentUnitType;

/**
 * Interfaz de servicio para la gestion de tipos de unidades hospitalarias.
 */
public interface DepartmentUnitTypeService extends GenericService<DepartmentUnitType, DepartmentUnitTypeSearchDto> {
}
