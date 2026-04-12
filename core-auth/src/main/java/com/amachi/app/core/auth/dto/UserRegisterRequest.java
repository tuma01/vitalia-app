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
    @NotBlank(message = "First name is required")
    @Size(max = 50, message = "First name cannot exceed 50 characters")
    private String firstName;

    @Size(max = 50, message = "Middle name cannot exceed 50 characters")
    private String middleName;

    @NotBlank(message = "Last name is required")
    @Size(max = 50, message = "Last name cannot exceed 50 characters")
    private String lastName;

    @Size(max = 50, message = "Second last name cannot exceed 50 characters")
    private String secondLastName;

    @NotNull(message = "El tipo de persona es obligatorio")
    private PersonType personType;

    // --- Datos del Contexto (Tenant) ---
    @NotBlank(message = "validation.required")
    @Size(max = 50, message = "El código del tenant no puede exceder los 50 caracteres")
    private String tenantCode;

}
