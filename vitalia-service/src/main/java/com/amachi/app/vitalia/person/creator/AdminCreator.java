package com.amachi.app.vitalia.person.creator;

import com.amachi.app.vitalia.authentication.dto.UserRegisterRequest;
import com.amachi.app.vitalia.common.enums.PersonType;
import com.amachi.app.vitalia.person.entity.Person;
import org.springframework.stereotype.Component;

@Component
public class AdminCreator implements PersonCreator {

    @Override
    public PersonType getSupportedType() {
        return PersonType.ADMIN;
    }

    @Override
    public Person create(UserRegisterRequest dto) {
        return null;
//        return new Admin(dto.getFirstName(), dto.getLastName(), PersonType.ADMIN);
    }
}
