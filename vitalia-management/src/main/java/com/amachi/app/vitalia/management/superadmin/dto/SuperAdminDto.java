package com.amachi.app.vitalia.management.superadmin.dto;

import com.amachi.app.core.domain.dto.PersonDto;
import com.amachi.app.core.auth.dto.UserDto;
import com.amachi.app.core.common.enums.SuperAdminLevel;
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

    @Schema(description = "Datos del Usuario asociado")
    private UserDto user;
}
