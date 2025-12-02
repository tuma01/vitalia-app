package com.amachi.app.vitalia.person.creator;

import com.amachi.app.vitalia.authentication.dto.UserRegisterRequest;
import com.amachi.app.vitalia.common.enums.EmployeeStatus;
import com.amachi.app.vitalia.common.enums.EmployeeType;
import com.amachi.app.vitalia.common.enums.PersonType;
import com.amachi.app.vitalia.employee.entity.Employee;
import com.amachi.app.vitalia.person.entity.Person;
import org.springframework.stereotype.Component;

@Component
public class EmployeeCreator implements PersonCreator {

    @Override
    public PersonType getSupportedType() {
        return PersonType.EMPLOYEE;
    }

    @Override
    public Person create(UserRegisterRequest dto) {
//        return null;
        return Employee.builder()
                .nombre(dto.getNombre())
                .segundoNombre(dto.getSegundoNombre())
                .apellidoPaterno(dto.getApellidoPaterno())
                .apellidoMaterno(dto.getApellidoMaterno())
                .employeeType(EmployeeType.TECNICO)
                .employeeStatus(EmployeeStatus.ACTIVO)
//                .personType(PersonType.EMPLOYEE)
                .build();
    }
}
