package com.amachi.app.vitalia.person.dto;

import com.amachi.app.vitalia.common.enums.RelationStatus;
import com.amachi.app.vitalia.common.enums.RoleContext;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "PersonTenant", description = "Schema to hold PersonTenant information")
public class PersonTenantDto {
    @Schema(description = "Id Tenant", example = "1")
    private Long id;

    @Schema(description = "Información de la persona asociada", required = true)
    private Long personId;

    @Schema(description = "Id del Tenant asociado", required = true)
    private Long tenantId;

    @Schema(description = "Identificador de salud nacional", example = "NHI123456789")
    private String nationalHealthId;

    @Schema(description = "Contexto del rol de la persona en el tenant", example = "DOCTOR, PATIENT, etc.", required = true)
    private RoleContext roleContext; // DOCTOR, PATIENT, etc.

    @Schema(description = "Fecha de registro de la persona en el tenant", example = "2023-10-01T12:00:00")
    private LocalDateTime dateRegistered;

    @Schema(description = "Fecha de desregistro de la persona en el tenant", example = "2024-10-01T12:00:00")
    private LocalDateTime  dateUnregistered;

    @Schema(description = "Estado de la relación entre la persona y el tenant", example = "ACTIVO, INACTIVO, SUSPENDIDO, etc.", required = true)
    private RelationStatus relationStatus; // ACTIVO, INACTIVO, SUSPENDIDO, etc.
}
