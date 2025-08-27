package com.amachi.app.vitalia.historiamedica.dto;

import com.amachi.app.vitalia.common.utils.EstadoConsultaEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Schema(name = "ConsultaMedica", description = "Representa un registro de ConsultaMedica del paciente, detallando la información.")
public class ConsultaMedicaDto {

    @Schema(description = "Identificador único de la consulta médica", example = "1")
    private Long id;

    @Schema(description = "Estado actual de la consulta médica", example = "PENDING")
    private EstadoConsultaEnum estadoConsulta;

    @Schema(description = "Tipo de consulta médica", example = "GENERAL")
    private TipoConsultaDto tipoConsulta;

    @Schema(description = "Fecha y hora de la consulta médica", example = "2023-10-01T10:00:00")
    private LocalDateTime fechaConsulta;

    @Schema(description = "Doctor que realizó la consulta médica", example = "5L")
    private Long doctorId;

    @Schema(description = "Motivo de la consulta médica", example = "Dolor de cabeza persistente")
    private String motivoConsulta;

    @Schema(description = "Síntomas presentados por el paciente", example = "Fiebre, tos, dolor de garganta")
    private String sintomas;

    @Schema(description = "Diagnóstico realizado por el doctor", example = "Infección viral leve")
    private String diagnostico;

    @Schema(description = "Tratamiento recomendado por el doctor", example = "Reposo y líquidos abundantes")
    private String tratamiento;

    @Schema(description = "Recomendaciones adicionales del doctor", example = "Evitar el contacto con personas enfermas")
    private String recomendaciones;

    @Schema(description = "Fecha y hora de la próxima cita programada", example = "2023-10-15T10:00:00")
    private LocalDateTime fechaProximaCita;

    @Schema(description = "Observaciones adicionales de la consulta médica", example = "Paciente debe regresar si los síntomas persisten")
    private String observaciones;

    @Schema(description = "Historia médica asociada a la consulta", example = "55L")
    private Long historiaMedicaId;
}
