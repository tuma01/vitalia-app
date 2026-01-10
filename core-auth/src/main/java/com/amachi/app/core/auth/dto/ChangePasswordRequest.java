package com.amachi.app.core.auth.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO para solicitar el cambio de contraseña por un usuario autenticado.
 */
@Getter
@Setter
public class ChangePasswordRequest {

    @NotEmpty(message = "La contraseña actual es obligatoria")
    private String oldPassword;

    @NotEmpty(message = "La nueva contraseña es obligatoria")
    @Size(min = 8, message = "La nueva contraseña debe tener al menos 8 caracteres")
    private String newPassword;

    // Se asume que el userId se obtiene del contexto de seguridad, no del cuerpo de la solicitud.
}
