package com.amachi.app.vitalia.authentication.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequest {

    @Email(message = "Email format is invalid")
    @NotBlank(message = "Email is mandatory")
    private String email;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    /**
     * Código del hospital (tenant) en el que el usuario intenta autenticarse.
     * Ejemplo: "HSP-QC-001"
     */
    @NotBlank(message = "Tenant code is required")
    private String tenantCode;
}
