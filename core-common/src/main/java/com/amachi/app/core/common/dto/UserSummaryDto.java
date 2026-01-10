package com.amachi.app.core.common.dto;

import com.amachi.app.core.common.enums.PersonType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "UserSummary", description = "Información básica del usuario autenticado")
public class UserSummaryDto {

    @Schema(description = "Identificador único del usuario", example = "12")
    private Long id;

    @Schema(description = "Correo electrónico del usuario", example = "doctor@example.com")
    private String email;

    @Schema(description = "Nombre completo derivado de la entidad Person")
    private String personName;

    @Schema(description = "Código del tenant (hospital) actual", example = "HSP001")
    private String tenantCode;

    @Schema(description = "Nombre del tenant (hospital) actual", example = "Hospital Central")
    private String tenantName;

    @Schema(description = "Tipo de persona (DOCTOR, NURSE, PATIENT, etc.)")
    private PersonType personType;

    @Schema(description = "Roles del usuario dentro del tenant actual")
    private List<String> roles;

    private boolean enabled; // ✅ agregado
}
