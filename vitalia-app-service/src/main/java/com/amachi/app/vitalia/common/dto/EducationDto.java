package com.amachi.app.vitalia.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Schema(name = "Education", description = "Schema to hold Education information")
public class EducationDto {

    @Schema(description = "Identificador unico de la educacion", example = "1")
    private Long id;

    @Schema(description = "Nombre de la institucion educativa", example = "University of Technology")
    private String institution;

    @Schema(description = "Titulo obtenido en la educacion", example = "Bachelor of Science in Computer Science")
    private String degree;

    @Schema(description = "Fecha de inicio de la educacion", example = "2020-01-01")
    private LocalDate startDate;

    @Schema(description = "Fecha de finalizacion de la educacion", example = "2023-12-31")
    private LocalDate endDate;

    @Schema(description = "Perfil del usuario asociado a la educacion")
    private Long profileId;
}
