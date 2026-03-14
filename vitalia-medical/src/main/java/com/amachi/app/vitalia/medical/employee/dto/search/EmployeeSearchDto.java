package com.amachi.app.vitalia.medical.employee.dto.search;

import com.amachi.app.core.common.dto.BaseSearchDto;
import com.amachi.app.core.common.enums.EmployeeStatus;
import com.amachi.app.core.common.enums.EmployeeType;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Hidden
public final class EmployeeSearchDto implements BaseSearchDto {
    private Long id;
    private String nombre;
    private String apellidoPaterno;
    private String nationalId;
    private EmployeeType employeeType;
    private EmployeeStatus employeeStatus;
    private String codigoEmpleado;

    @Override
    public Long getId() {
        return id;
    }
}
