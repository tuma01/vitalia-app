package com.amachi.app.core.management.util;

import com.amachi.app.core.common.enums.PersonType;
import com.amachi.app.vitalia.medical.employee.entity.Employee;
import com.amachi.app.vitalia.medical.patient.entity.Patient;
import com.amachi.app.core.domain.entity.Person;
import com.amachi.app.core.management.superadmin.entity.SuperAdmin;
import com.amachi.app.core.management.tenantadmin.entity.TenantAdmin;

public final class PersonTypeValidator {

    private PersonTypeValidator() {
    }

    public static boolean matches(Person person, PersonType type) {
        if (person == null || type == null) {
            return false;
        }
        // Robust Architectural Choice: Use the persisted personType discriminator.
        // This is superior to 'instanceof' because it works seamlessly with 
        // Hibernate Proxies and Lazy Loading without needing to unproxy entities.
        return person.getPersonType() == type;
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
