package com.amachi.app.vitalia.management.util;

import com.amachi.app.core.common.enums.PersonType;
import com.amachi.app.vitalia.clinical.employee.entity.Employee;
import com.amachi.app.vitalia.clinical.patient.entity.Patient;
import com.amachi.app.core.domain.entity.Person;
import com.amachi.app.vitalia.management.superadmin.entity.SuperAdmin;
import com.amachi.app.vitalia.management.tenantadmin.entity.TenantAdmin;

public final class PersonTypeValidator {

    private PersonTypeValidator() {
    }

    public static boolean matches(Person person, PersonType type) {
        return switch (type) {

            // TIPOS IMPLEMENTADOS (validamos)
            case SUPER_ADMIN -> person instanceof SuperAdmin;
            case ADMIN -> person instanceof TenantAdmin;
            case PATIENT -> person instanceof Patient;
            case EMPLOYEE -> person instanceof Employee;

            // TODO TIPOS AÚN NO IMPLEMENTADOS → no validamos
            case DOCTOR, NURSE, RECEPTIONIST, LAB_TECHNICIAN, PHARMACIST -> true;
        };
    }

    // public static boolean matches(Person person, PersonType type) {
    // return switch (type) {
    // case SUPER_ADMIN -> person instanceof SuperAdmin;
    // case ADMIN -> person instanceof TenantAdmin;
    // case DOCTOR -> person instanceof Doctor;
    // case NURSE -> person instanceof Nurse;
    // case PATIENT -> person instanceof Patient;
    // case EMPLOYEE -> person instanceof Employee;
    // };
    // }
}
