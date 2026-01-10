package com.amachi.app.core.auth.dto;

import com.amachi.app.core.common.enums.PersonType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * DTO utilizado para el registro inicial de un nuevo usuario en un tenant específico.
 * Contiene información de credenciales (User) y de identidad (Person).
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterRequest {

    // --- Datos de la Cuenta (User) ---
    @NotBlank(message = "validation.required")
    @Email(message = "validation.email")
    @Size(max = 100, message = "El email no puede exceder los 100 caracteres")
    private String email;

    @NotBlank(message = "validation.required")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    private String password;

    // --- Datos de la Persona (Person) ---
    @NotBlank(message = "validation.required")
    @Size(max = 50, message = "El nombre no puede exceder los 50 caracteres")
    private String nombre;

    @Size(max = 50, message = "El segundo nombre no puede exceder los 50 caracteres")
    private String segundoNombre;

    @NotBlank(message = "validation.required")
    @Size(max = 50, message = "El apellido paterno no puede exceder los 50 caracteres")
    private String apellidoPaterno;

    @Size(max = 50, message = "El apellido materno no puede exceder los 50 caracteres")
    private String apellidoMaterno;

    @NotNull(message = "El tipo de persona es obligatorio")
    private PersonType personType;

    // --- Datos del Contexto (Tenant) ---
    @NotBlank(message = "validation.required")
    @Size(max = 50, message = "El código del tenant no puede exceder los 50 caracteres")
    private String tenantCode;

}
