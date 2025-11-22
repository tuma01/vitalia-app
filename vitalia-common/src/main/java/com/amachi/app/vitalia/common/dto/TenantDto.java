package com.amachi.app.vitalia.common.dto;

import com.amachi.app.vitalia.common.enums.TenantType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Tenant", description = "Schema to hold Tenant information")
public class TenantDto {

    @Schema(description = "Id Tenant", example = "1")
    private Long id;

    @Schema(description = "Code of the Tenant", example = "TENANT001")
    private String code;

    @Schema(description = "Name of the Tenant", example = "Main Hospital")
    private String name;

    @Schema(description = "Type of the Tenant", example = "HOSPITAL")
    private TenantType type;// Ej: HOSPITAL, CLINICA, LABORATORIO, PHARMACY

    @Schema(description = "Indicates if the Tenant is active", example = "true")
    private Boolean isActive = true;
}
