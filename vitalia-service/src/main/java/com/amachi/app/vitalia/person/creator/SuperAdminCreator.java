package com.amachi.app.vitalia.person.creator;

import com.amachi.app.vitalia.authentication.dto.UserRegisterRequest;
import com.amachi.app.vitalia.common.enums.PersonType;
import com.amachi.app.vitalia.common.enums.SuperAdminLevel;
import com.amachi.app.vitalia.person.entity.Person;
import com.amachi.app.vitalia.superadmin.entity.SuperAdmin;
import org.springframework.stereotype.Component;

@Component
public class SuperAdminCreator implements PersonCreator {

    @Override
    public PersonType getSupportedType() {
        return PersonType.SUPER_ADMIN;
    }

    @Override
    public Person create(UserRegisterRequest dto) {
        return SuperAdmin.builder()
                .nombre(dto.getNombre())
                .apellidoPaterno(dto.getApellidoPaterno())
                .apellidoMaterno(dto.getApellidoMaterno())
                .globalAccess(true)
                // .personType(PersonType.SUPER_ADMIN)
                .level(SuperAdminLevel.LEVEL_1)
                .build();
    }
}
