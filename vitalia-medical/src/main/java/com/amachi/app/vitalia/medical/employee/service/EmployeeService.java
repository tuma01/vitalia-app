package com.amachi.app.vitalia.medical.employee.service;

import com.amachi.app.core.common.service.GenericService;
import com.amachi.app.vitalia.medical.employee.dto.search.EmployeeSearchDto;
import com.amachi.app.vitalia.medical.employee.entity.Employee;

/**
 * Interfaz de servicio para la gestion de empleados.
 */
public interface EmployeeService extends GenericService<Employee, EmployeeSearchDto> {
}
