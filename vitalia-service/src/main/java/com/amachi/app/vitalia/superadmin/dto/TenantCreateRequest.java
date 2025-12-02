package com.amachi.app.vitalia.superadmin.dto;


import com.amachi.app.vitalia.common.enums.TenantType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Schema(name = "TenantCreateRequest", description = "Request para crear un Tenant y su TenantAdmin inicial")
public class TenantCreateRequest {

    // Tenant
    @NotBlank @Size(max = 100)
    @Schema(example = "HOSP_B", required = true)
    private String code;

    @NotBlank @Size(max = 200)
    @Schema(example = "Hospital B", required = true)
    private String name;

    @NotNull
    @Schema(example = "HOSPITAL", required = true, allowableValues = {"HOSPITAL","CLINIC","LABORATORY","PHARMACY","GLOBAL"})
    private TenantType type;

    @Size(max = 1000)
    @Schema(example = "Hospital B - sucursal")
    private String description;

    @Size(max = 100)
    @Schema(example = "X-Tenant-Code")
    private String fallbackHeader;

    @Schema(example = "true")
    private Boolean allowLocal = true;

    @Size(max = 200)
    @Schema(example = "hospitalb.local")
    private String defaultDomain;

    // Tenant Admin (primer admin)
    @NotBlank @Email @Size(max = 100)
    @Schema(example = "admin@hospitalb.com", required = true)
    private String adminEmail;

    @Size(min = 8, max = 100)
    @Schema(example = "ChangeMe123!")
    private String adminPassword; // opcional, si null se genera

    @NotBlank @Size(max = 50)
    @Schema(example = "Tenant")
    private String adminFirstName;

    @NotBlank @Size(max = 50)
    @Schema(example = "Administrator")
    private String adminLastName;

    @Schema(example = "ADMIN")
    private String adminPersonType = "ADMIN";
}

