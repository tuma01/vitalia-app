package com.amachi.app.vitalia.employee.entity;

import com.amachi.app.vitalia.user.entity.Person;
import com.amachi.app.vitalia.common.utils.EmployeeRole;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

//@Entity
@Getter @Setter
@DiscriminatorValue("EMPLOYEE")
public class Employee  extends Person {

    @Enumerated(EnumType.STRING)
    private EmployeeRole employeeRole; // RECEPCIONISTA, LIMPIEZA, TECNICO, etc.
}
