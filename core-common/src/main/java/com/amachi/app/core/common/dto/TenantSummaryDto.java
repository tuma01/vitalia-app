package com.amachi.app.core.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO para el resumen del Tenant en respuestas de autenticación (Elite Tier).
 * Asegura que el cliente reciba la identidad organizativa tras el login.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Resumen de información del Tenant")
public class TenantSummaryDto implements Serializable {

    @Schema(description = "ID interno del Tenant")
    private Long id;

    @Schema(description = "Código único del Tenant (Aislamiento)")
    private String code;

    @Schema(description = "Nombre comercial del Tenant")
    private String name;

    @Schema(description = "Logo o marca del Tenant")
    private String logoUrl;
}
