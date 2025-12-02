package com.amachi.app.vitalia.util;

import com.amachi.app.vitalia.common.enums.PersonType;
import com.amachi.app.vitalia.employee.entity.Employee;
import com.amachi.app.vitalia.patient.entity.Patient;
import com.amachi.app.vitalia.person.entity.Person;
import com.amachi.app.vitalia.person.entity.SuperAdmin;
import com.amachi.app.vitalia.tenant.entity.TenantAdmin;

public final class PersonTypeValidator {

    private PersonTypeValidator() {}

    public static boolean matches(Person person, PersonType type) {
        return switch (type) {

            // TIPOS IMPLEMENTADOS (validamos)
            case SUPER_ADMIN -> person instanceof SuperAdmin;
            case TENANT_ADMIN       -> person instanceof TenantAdmin;
            case PATIENT     -> person instanceof Patient;
            case EMPLOYEE    -> person instanceof Employee;

            // TODO TIPOS AÚN NO IMPLEMENTADOS → no validamos
            case DOCTOR, NURSE -> true;
        };
    }

//    public static boolean matches(Person person, PersonType type) {
//        return switch (type) {
//            case SUPER_ADMIN -> person instanceof SuperAdmin;
//            case ADMIN       -> person instanceof TenantAdmin;
//            case DOCTOR      -> person instanceof Doctor;
//            case NURSE       -> person instanceof Nurse;
//            case PATIENT     -> person instanceof Patient;
//            case EMPLOYEE    -> person instanceof Employee;
//        };
//    }
}
