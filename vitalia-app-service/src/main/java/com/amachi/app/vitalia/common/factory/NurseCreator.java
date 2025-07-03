package com.amachi.app.vitalia.common.factory;

import com.amachi.app.vitalia.authentication.dto.UserRegisterDto;
import com.amachi.app.vitalia.user.entity.Person;
import com.amachi.app.vitalia.common.utils.PersonType;
import com.amachi.app.vitalia.nurse.entity.Nurse;
import org.springframework.stereotype.Component;

@Component
public class NurseCreator implements PersonCreator {

    @Override
    public PersonType getSupportedType() {
        return PersonType.NURSE;
    }

    @Override
    public Person create(UserRegisterDto dto) {
        return Nurse.builder()
                .nombre(dto.getFirstName())
                .apellidoPaterno(dto.getLastName())
                .personType(PersonType.NURSE)
                .build();
    }
}