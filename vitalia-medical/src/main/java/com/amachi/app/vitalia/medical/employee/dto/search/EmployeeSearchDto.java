package com.amachi.app.vitalia.medical.employee.dto.search;

import com.amachi.app.core.common.dto.BaseSearchDto;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.*;

/**
 * Filtros de búsqueda dinámica ÉLITE para Empleados Staff.
 */
@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
@Hidden
public final class EmployeeSearchDto implements BaseSearchDto {
    private Long id;
    private String query; // Búsqueda por nombre o apellidos
    private String employeeCode;
    private String nationalId;
    private String email;
    private String employeeType;
    private String employeeStatus;
    private Long departmentUnitId;

    @Override
    public Long getId() {
        return id;
    }
}
