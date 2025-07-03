package com.amachi.app.vitalia.common.factory;

import com.amachi.app.vitalia.authentication.dto.UserRegisterDto;
import com.amachi.app.vitalia.user.entity.Person;
import com.amachi.app.vitalia.common.utils.PersonType;
import org.springframework.stereotype.Component;

@Component
public class PatientCreator implements PersonCreator {
    @Override
    public PersonType getSupportedType() {
        return PersonType.PATIENT;
    }

    @Override
    public Person create(UserRegisterDto dto) {
        return null;
//        return Patient.builder()
//                .nombre(dto.getFirstName())
//                .apellidoPaterno(dto.getLastName())
//                .personType(PersonType.PATIENT)
//                .build();
    }
}