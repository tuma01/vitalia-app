package com.amachi.app.vitalia.medical.infrastructure.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.validation.annotation.Validated;

/**
 * Esquema para los tipos de unidades hospitalarias (Especialidades de planta).
 * Estandar de Oro de Vitalia.
 */
@Validated
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder(toBuilder = true)
@Schema(name = "DepartmentUnitType", description = "Categoria de especialidad hospitalaria (ej: UCI, PEDIATRIA, URGENCIAS)")
public class DepartmentUnitTypeDto {

    @Schema(description = "Identificador unico de la especialidad", example = "501")
    private Long id;

    @Schema(description = "Identificador Global (FHIR/Publico)", example = "DUT-789-UCI")
    private String externalId;

    @NotBlank(message = "Nombre {err.mandatory}")
    @Schema(description = "Nombre legible de la unidad", example = "CUIDADOS INTENSIVOS ADULTOS")
    private String name;

    @Schema(description = "Descripcion tecnica de los servicios prestados", example = "Unidad de alta complejidad con soporte vital avanzado.")
    private String description;
}
