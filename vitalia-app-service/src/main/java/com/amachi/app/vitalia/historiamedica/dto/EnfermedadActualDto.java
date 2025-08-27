package com.amachi.app.vitalia.historiamedica.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Schema(name = "EnfermedadActual", description = "Representa un registro de EnfermedadActual del paciente, detallando la información.")
public class EnfermedadActualDto {

    @Schema(description = "Identificador único de la enfermedad actual", example = "1")
    private Long id;

    @Schema(description = "Nombre de la enfermedad actual", example = "Diabetes")
    private String nombre;

    @Schema(description = "Fecha del diagnóstico de la enfermedad actual", example = "2023-10-01")
    private LocalDate fechaDiagnostico;

    @Schema(description = "Descripción de los síntomas de la enfermedad actual", example = "Sed excesiva, aumento de la frecuencia urinaria")
    private String sintomas;

    @Schema(description = "Tratamientos recibidos para la enfermedad actual", example = "Insulina, dieta controlada")
    private String tratamientos;

    @Schema(description = "Historia médica asociada a la enfermedad actual")
    private Long historiaMedicaId;
}
