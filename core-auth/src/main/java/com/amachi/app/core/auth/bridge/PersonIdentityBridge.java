package com.amachi.app.core.auth.bridge;

import com.amachi.app.core.auth.entity.User;
import com.amachi.app.core.common.enums.PersonType;
import com.amachi.app.core.domain.entity.Person;
import java.util.Map;

/**
 * Interface used by the auth module to request the creation of a Person
 * without knowing the specific subclasses (TenantAdmin, Employee, etc.).
 *
 * <p>Implementation resides in core-management which has access to the full domain hierarchy.
 */
public interface PersonIdentityBridge {

    /**
     * Factory method to instantiate the correct Person subclass based on its type and context.
     *
     * @param type    The type of person to create (ADMIN, EMPLOYEE, etc.)
     * @param context Additional creation context (e.g. tenant, adminLevel)
     * @return An instance of the specific Person subclass.
     */
    Person createPerson(PersonType type, Map<String, Object> context);

    /**
     * Bidirectional linking for the hybrid model. Sets the back-reference
     * from the specific Person subclass to the User entity.
     *
     * @param person The person instance (e.g. TenantAdmin).
     * @param user   The user instance.
     */
    void linkUser(Person person, User user);

    /**
     * Validates if a person instance matches the expected PersonType.
     *
     * @param person The person instance to validate.
     * @param type   The expected person type.
     * @return true if it matches, false otherwise.
     */
    boolean validatePersonType(Person person, PersonType type);
}
