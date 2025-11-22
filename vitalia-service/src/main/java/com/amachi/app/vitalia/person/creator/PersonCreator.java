package com.amachi.app.vitalia.person.creator;


import com.amachi.app.vitalia.authentication.dto.UserRegisterRequest;
import com.amachi.app.vitalia.common.enums.PersonType;
import com.amachi.app.vitalia.person.entity.Person;

public interface PersonCreator {
    PersonType getSupportedType();
    Person create(UserRegisterRequest dto);
}
