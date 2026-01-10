package com.amachi.app.core.domain.tenant.dto;

import com.amachi.app.core.domain.tenant.enums.TenantType;
import com.amachi.app.core.geography.address.dto.AddressDto;
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
    @Builder.Default
    private Boolean isActive = true;

    @Schema(description = "Description of the Tenant", example = "This is the main hospital tenant.")
    private String description;

    @Schema(description = "Theme ID associated with the Tenant")
    private Long themeId;

    @Schema(description = "Address of the Tenant")
    private AddressDto address;
}
