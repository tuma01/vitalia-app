package com.amachi.app.vitalia.common.factory;

import com.amachi.app.vitalia.authentication.dto.UserRegisterDto;
import com.amachi.app.vitalia.user.entity.Person;
import com.amachi.app.vitalia.common.utils.PersonType;

public interface PersonCreator {
    PersonType getSupportedType();
    Person create(UserRegisterDto dto);
}
