package com.amachi.app.vitalia.medical.history.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.validation.annotation.Validated;

@Validated
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder(toBuilder = true)
@Schema(name = "PastIllness", description = "Esquema de patología crónica o histórica")
public class PastIllnessDto {

    @Schema(description = "Identificador interno (PK)", example = "20001", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Identificador Global (UUID)", example = "PAST-222-UUID", accessMode = Schema.AccessMode.READ_ONLY)
    private String externalId;

    @NotBlank(message = "Disease Name {err.mandatory}")
    @Schema(description = "Nombre oficial de la patología", example = "Diabetes Mellitus Tipo 2")
    private String name;

    @Schema(description = "Descripción clínica", example = "Diagnosticado hace 10 años")
    private String description;

    @Schema(description = "Sintomatología", example = "Poliuria, polidipsia")
    private String symptoms;

    @Schema(description = "Tratamientos previos", example = "Metformina 850mg")
    private String treatments;

    @NotNull(message = "Medical History {err.mandatory}")
    @Schema(description = "ID del expediente clínico", example = "102")
    private Long medicalHistoryId;
}
