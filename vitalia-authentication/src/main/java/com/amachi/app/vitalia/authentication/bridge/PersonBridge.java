package com.amachi.app.vitalia.authentication.bridge;

import com.amachi.app.vitalia.authentication.dto.UserRegisterRequest;
import com.amachi.app.vitalia.authentication.entity.User;
import com.amachi.app.vitalia.common.dto.UserSummaryDto;
import com.amachi.app.vitalia.common.entity.Tenant;

public interface PersonBridge {

    /**
     * Crea una nueva persona en el módulo service a partir del registro del usuario.
     *
     * @param request Datos del usuario a registrar
     * @return ID de la persona creada
     */
    Long createPerson(UserRegisterRequest request);

    /**
     * Construye el UserSummaryDto completo, incluyendo datos de Person.
     */
    UserSummaryDto buildUserSummary(User user, Tenant tenant);
}
