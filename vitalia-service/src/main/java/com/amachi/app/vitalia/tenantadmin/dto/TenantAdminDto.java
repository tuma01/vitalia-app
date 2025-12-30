package com.amachi.app.vitalia.tenantadmin.dto;

import com.amachi.app.vitalia.authentication.dto.UserDto;
import com.amachi.app.vitalia.common.enums.TenantAdminLevel;
import com.amachi.app.vitalia.person.dto.PersonDto;
import com.amachi.app.vitalia.tenant.dto.TenantDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "TenantAdmin", description = "Schema to hold TenantAdmin information")
public class TenantAdminDto extends PersonDto {
    @Schema(description = "Nivel de Administrador")
    private TenantAdminLevel adminLevel;

    @Schema(description = "Datos del Tenant (Para vinculación o creación)")
    private TenantDto tenant;

    @Schema(description = "Datos del Usuario asociado")
    private UserDto user;
}
