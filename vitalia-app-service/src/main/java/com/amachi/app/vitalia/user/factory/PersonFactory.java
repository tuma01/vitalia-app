package com.amachi.app.vitalia.user.factory;

import com.amachi.app.vitalia.authentication.dto.UserRegisterDto;
import com.amachi.app.vitalia.common.utils.PersonType;
import com.amachi.app.vitalia.admin.entity.Admin;
import com.amachi.app.vitalia.nurse.entity.Nurse;
import com.amachi.app.vitalia.user.entity.Person;

import java.util.Map;

public class PersonFactory {
    private final Map<PersonType, Class<? extends Person>> personMap = Map.of(
            PersonType.NURSE, Nurse.class,
            PersonType.ADMIN, Admin.class
//            PersonType.DOCTOR, Doctor.class,
//            PersonType.PATIENT, Patient.class,
//            PersonType.USER, GenericUser.class
    );

    public Person create(UserRegisterDto dto) {
        Class<? extends Person> clazz = personMap.get(dto.getPersonType());

        if (clazz == null) {
            throw new IllegalArgumentException("Unsupported PersonType: " + dto.getPersonType());
        }

        try {
            Person person = clazz.getDeclaredConstructor().newInstance();

            // Setters comunes
            person.setPersonType(dto.getPersonType());
            person.setNombre(dto.getFirstName());
            person.setApellidoPaterno(dto.getLastName());

            return person;
        } catch (Exception e) {
            throw new IllegalArgumentException("Error creating person of type: " + dto.getPersonType(), e);
        }
    }
}
