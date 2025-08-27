package com.amachi.app.vitalia.departamentothospital.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Schema(name = "DepartamentoTipo", description = "Schema to hold DepartamentoTipo information")
public class DepartamentoTipoDto {

    @Schema(description = "id of Department ", example = "1")
    private Long id;

    @Schema(description = "Name of Department", example = "Cardiology")
    private String name;

    @Schema(description ="Description of Department", example = "Department specializing in heart-related conditions")
    private String description;
}
