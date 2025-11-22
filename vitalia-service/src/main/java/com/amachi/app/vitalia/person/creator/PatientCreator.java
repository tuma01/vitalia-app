package com.amachi.app.vitalia.person.creator;


import com.amachi.app.vitalia.authentication.dto.UserRegisterRequest;
import com.amachi.app.vitalia.common.enums.PersonType;
import com.amachi.app.vitalia.person.entity.Person;
import org.springframework.stereotype.Component;

@Component
public class PatientCreator implements PersonCreator {

    @Override
    public PersonType getSupportedType() {
        return PersonType.PATIENT;
    }

    @Override
    public Person create(UserRegisterRequest dto) {
        // TODO a cambiar
        return null;
//        return Patient.builder()
//                .nombre(dto.getFirstName())
//                .apellidoPaterno(dto.getLastName())
//                .personType(PersonType.PATIENT)
//                .build();
    }
}
