package com.amachi.app.vitalia.historiamedica.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Schema(name = "RegistroEnfermedad", description = "Esquema para almacenar información del RegistroEnfermedad")
public class RegistroEnfermedadDto {

    @Schema(description = "Identificador único del registro de enfermedad", example = "1")
    private Long id;

    @Schema(description = "Nombre de la enfermedad", example = "Diabetes Mellitus")
    private String nombreEnfermedad;

    @Schema(description = "Descripción de la enfermedad", example = "Enfermedad crónica que afecta la forma en que el cuerpo procesa la glucosa en sangre.")
    private String descripcion;

    @Schema(description = "Síntomas asociados a la enfermedad", example = "Sed excesiva, aumento de la frecuencia urinaria, fatiga")
    private String sintomas;

    @Schema(description = "Tratamientos recibidos para la enfermedad", example = "Insulina, Metformina")
    private String tratamientos;

    @Schema(description = "Historia médica asociada a este registro de enfermedad")
    private Long historiaMedicaId;
}
