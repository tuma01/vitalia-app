package com.amachi.app.vitalia.boot.bridge;

import com.amachi.app.core.auth.bridge.PersonIdentityBridge;
import com.amachi.app.core.auth.entity.User;
import com.amachi.app.core.common.enums.PersonType;
import com.amachi.app.core.common.enums.EmployeeType;
import com.amachi.app.core.common.enums.EmployeeStatus;
import com.amachi.app.core.common.enums.SuperAdminLevel;
import com.amachi.app.core.common.enums.TenantAdminLevel;
import com.amachi.app.core.domain.entity.Person;
import com.amachi.app.core.management.superadmin.entity.SuperAdmin;
import com.amachi.app.core.management.tenantadmin.entity.TenantAdmin;
import com.amachi.app.core.management.util.PersonTypeValidator;
import com.amachi.app.vitalia.medical.employee.entity.Employee;
import com.amachi.app.core.domain.tenant.entity.Tenant;
import lombok.extern.slf4j.Slf4j;
import java.util.Map;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Component;

/**
 * Implementation of the PersonIdentityBridge located in the boot module
 * to avoid circular dependencies while having visibility over all domain modules.
 */
@Slf4j
@Component
public class PersonIdentityBridgeImpl implements PersonIdentityBridge {

    @Override
    public Person createPerson(PersonType type, Map<String, Object> context) {
        log.debug("🛠️ Identity Bridge: Creating person instance for type [{}] with context", type);

        if (type == null) {
            return Person.builder().build();
        }

        Person genericPerson = switch (type) {
            case SUPER_ADMIN -> SuperAdmin.builder()
                    .level(SuperAdminLevel.LEVEL_1)
                    .globalAccess(true)
                    .build();
            case ADMIN       -> {
                TenantAdmin admin = TenantAdmin.builder()
                        .adminLevel(TenantAdminLevel.LEVEL_1)
                        .build();
                if (context != null && context.containsKey("tenant")) {
                    admin.setTenant((Tenant) context.get("tenant"));
                }
                yield admin;
            }
            case EMPLOYEE, NURSE, DOCTOR -> Employee.builder()
                    .employeeType(EmployeeType.ADMINISTRATIVO)
                    .employeeStatus(EmployeeStatus.ACTIVO)
                    .build();
            default -> {
                log.warn("⚠️ No specific subclass found for type [{}], falling back to generic Person", type);
                yield Person.builder().build();
            }
        };

        // Extract names from context if available (No more patches!)
        if (context != null) {
            if (context.containsKey("firstName")) {
                genericPerson.setFirstName((String) context.get("firstName"));
            }
            if (context.containsKey("lastName")) {
                genericPerson.setLastName((String) context.get("lastName"));
            }
        }
        
        return genericPerson;
    }

    @Override
    public void linkUser(Person person, User user) {
        if (person == null) return;
        
        PersonType type = person.getPersonType();
        log.debug("🛠️ Identity Bridge: Linking user to person type [{}]", type);

        // Professional Inheritance Handling: unproxy the entity to perform safe casting 
        // to the specific subclass (TenantAdmin, Employee, etc.)
        Person realPerson = (Person) Hibernate.unproxy(person);

        switch (type) {
            case ADMIN -> {
                if (realPerson instanceof TenantAdmin ta) {
                    ta.setUser(user);
                }
            }
            case SUPER_ADMIN -> {
                if (realPerson instanceof SuperAdmin sa) {
                    sa.setUser(user);
                }
            }
            case EMPLOYEE, DOCTOR, NURSE -> {
                if (realPerson instanceof Employee e) {
                    e.setUser(user);
                }
            }
            default -> log.warn("⚠️ linkUser: Unhandled linking logic for type [{}]", type);
        }
    }

    @Override
    public boolean validatePersonType(Person person, PersonType type) {
        // Robust validation delegating to the discriminator-based logic
        return PersonTypeValidator.matches(person, type);
    }
}
