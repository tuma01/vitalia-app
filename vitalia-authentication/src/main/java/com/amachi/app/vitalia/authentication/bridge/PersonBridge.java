package com.amachi.app.vitalia.authentication.bridge;

import com.amachi.app.vitalia.authentication.dto.UserRegisterRequest;

public interface PersonBridge {

    /**
     * Crea una nueva persona en el módulo service a partir del registro del usuario.
     *
     * @param request Datos del usuario a registrar
     * @return ID de la persona creada
     */
    Long createPerson(UserRegisterRequest request);
}
