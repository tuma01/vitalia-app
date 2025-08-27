package com.amachi.app.vitalia.historiamedica.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Schema(name = "TipoConsulta", description = "Representa un registro de TipoConsulta del paciente, detallando la información.")

public class TipoConsultaDto {

    @Schema(description = "Identificador único del tipo de consulta", example = "1")
    private Long id;

    @Schema(description = "Nombre del tipo de consulta", example = "Consulta General")
    private String nombre;

    @Schema(description = "Descripción del tipo de consulta", example = "Consulta médica general para chequeo y diagnóstico")
    private String descripcion;

    @Schema(description = "Especialidad asociada al tipo de consulta", example = "Medicina General")
    private String especialidad;
}
