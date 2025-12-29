package com.amachi.app.vitalia.superadmin.dto;

import com.amachi.app.vitalia.person.dto.PersonDto;
import com.amachi.app.vitalia.common.enums.SuperAdminLevel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.experimental.SuperBuilder;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "SuperAdmin", description = "Schema to hold SuperAdmin information")

public class SuperAdminDto extends PersonDto {

    @Schema(description = "Nivel del SuperAdmin", example = "LEVEL_1, LEVEL_2, etc.", required = true)
    private SuperAdminLevel level;

    @Schema(description = "Indica si el SuperAdmin tiene acceso global", example = "true or false", required = true)
    private Boolean globalAccess;

    @Schema(description = "ID del Usuario asociado", example = "1")
    private Long userId;

//    // Campos de User para creación/edición
//    @Schema(description = "Correo electrónico del usuario", example = "superadmin@vitalia.com")
//    private String email;
//
//    @Schema(description = "Contraseña", example = "********")
//    private String password;
//
//    @Schema(description = "Estado de la cuenta", example = "true")
//    private Boolean enabled;
}
