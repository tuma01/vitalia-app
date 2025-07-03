package com.amachi.app.vitalia.common.factory;

import com.amachi.app.vitalia.authentication.dto.UserRegisterDto;
import com.amachi.app.vitalia.user.entity.Person;
import com.amachi.app.vitalia.common.utils.PersonType;
import com.amachi.app.vitalia.admin.entity.Admin;
import org.springframework.stereotype.Component;

@Component
public class AdminCreator implements PersonCreator {
    @Override
    public PersonType getSupportedType() {
        return PersonType.ADMIN;
    }

    @Override
    public Person create(UserRegisterDto dto) {
        return Admin.builder()
                .nombre(dto.getFirstName())
                .apellidoPaterno(dto.getLastName())
                .personType(PersonType.ADMIN)
                .build();
    }
}