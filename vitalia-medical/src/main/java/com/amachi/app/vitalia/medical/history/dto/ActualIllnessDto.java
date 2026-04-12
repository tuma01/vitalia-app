package com.amachi.app.vitalia.medical.history.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;

@Validated
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder(toBuilder = true)
@Schema(name = "ActualIllness", description = "Esquema de patología activa detectada")
public class ActualIllnessDto {

    @Schema(description = "Identificador interno (PK)", example = "10001", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Identificador Global (UUID)", example = "ACT-111-UUID", accessMode = Schema.AccessMode.READ_ONLY)
    private String externalId;

    @NotBlank(message = "Disease Name {err.mandatory}")
    @Schema(description = "Nombre de la enfermedad", example = "Hipertensión Arterial")
    private String name;

    @Schema(description = "Fecha de diagnóstico", example = "2024-01-15")
    private LocalDate diagnosisDate;

    @Schema(description = "Sintomatología asociada", example = "Cefalea, mareos")
    private String symptoms;

    @Schema(description = "Tratamientos previos", example = "Enalapril 10mg")
    private String treatments;

    @NotNull(message = "Medical History {err.mandatory}")
    @Schema(description = "ID del expediente clínico", example = "102")
    private Long medicalHistoryId;
}
