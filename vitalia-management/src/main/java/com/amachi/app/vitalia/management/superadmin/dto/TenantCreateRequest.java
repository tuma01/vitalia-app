package com.amachi.app.vitalia.management.superadmin.dto;

import com.amachi.app.core.domain.tenant.enums.TenantType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "TenantCreateRequest", description = "Request para crear un Tenant y su TenantAdmin inicial")
public class TenantCreateRequest {

    // Tenant
    @NotBlank(message = "El código es obligatorio")
    @Size(max = 100, message = "El código no puede exceder 100 caracteres")
    @Schema(example = "HOSP_B")
    private String code;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 200, message = "El nombre no puede exceder 200 caracteres")
    @Schema(example = "Hospital B")
    private String name;

    @NotBlank(message = "El typo es obligatorio")
    @Schema(example = "HOSPITAL", allowableValues = { "HOSPITAL", "CLINIC", "LABORATORY", "PHARMACY", "GLOBAL" })
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
    @NotBlank
    @Email
    @Size(max = 100)
    @Schema(example = "admin@hospitalb.com")
    private String adminEmail;

    @Size(min = 8, max = 100)
    @Schema(example = "ChangeMe123!")
    private String adminPassword; // opcional, si null se genera

    @NotBlank
    @Size(max = 50)
    @Schema(example = "Tenant")
    private String adminFirstName;

    @NotBlank
    @Size(max = 50)
    @Schema(example = "Administrator")
    private String adminLastName;

    @Schema(example = "ADMIN")
    private String adminPersonType = "ADMIN";
}
