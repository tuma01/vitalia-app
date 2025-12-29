package com.amachi.app.vitalia.person.creator;

import com.amachi.app.vitalia.authentication.dto.UserRegisterRequest;
import com.amachi.app.vitalia.common.enums.PersonType;
import com.amachi.app.vitalia.common.enums.TenantAdminLevel;
import com.amachi.app.vitalia.person.entity.Person;
import com.amachi.app.vitalia.tenantadmin.entity.TenantAdmin;
import org.springframework.stereotype.Component;

@Component
public class TenantAdminCreator implements PersonCreator {

    @Override
    public PersonType getSupportedType() {
        return PersonType.ADMIN;
    }

    @Override
    public Person create(UserRegisterRequest dto) {
        return TenantAdmin.builder()
                .nombre(dto.getNombre())
                .apellidoPaterno(dto.getApellidoPaterno())
                .apellidoMaterno(dto.getApellidoMaterno())
                // Tenant association is handled by the caller (BootstrapService)
                .adminLevel(TenantAdminLevel.LEVEL_1)
                .build();
    }
}
