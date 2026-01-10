package com.amachi.app.vitalia.management.superadmin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "TenantAdminUpdateRequest", description = "Request para actualizar un Tenant Admin")
public class TenantAdminUpdateRequest {

    @Size(max = 50)
    @Schema(example = "Juan")
    private String firstName;

    @Size(max = 50)
    @Schema(example = "Perez")
    private String lastName;

    @Size(max = 50)
    @Schema(example = "Gomez")
    private String maternalName;

    @Size(max = 100)
    @Schema(example = "newemail@example.com")
    private String email;

    @Schema(description = "Si se envía, actualiza la contraseña")
    private String password;

    @Schema(description = "Habilita o deshabilita al usuario")
    private Boolean enabled;
}
