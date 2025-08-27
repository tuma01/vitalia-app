package com.amachi.app.vitalia.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Schema(name = "Experience", description = "Schema to hold Experience information")
public class ExperienceDto {

    @Schema(description = "Identificador unico de la experiencia", example = "1")
    private Long id;

    @Schema(description = "Titulo de la experiencia", example = "Software Engineer")
    private String title;

    @Schema(description = "Nombre de la institucion donde se adquirio la experiencia", example = "Tech Solutions Inc.")
    private String institution;

    @Schema(description = "Fecha de inicio de la experiencia", example = "2020-01-01")
    private LocalDate startDate;

    @Schema(description = "Fecha de finalizacion de la experiencia", example = "2023-12-31")
    private LocalDate endDate;

    @Schema(description = "Indicates if the experience is currently ongoing", example = "false")
    private String description;

    @Schema(description = "Profile associated with the experience", example = "UserProfile object")
    private Long profileId;
}
