package com.amachi.app.vitalia.medical.history.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.validation.annotation.Validated;

/**
 * Esquema para la evaluación de antecedentes hereditarios y salud familiar. (SaaS Elite Tier)
 */
@Validated
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder(toBuilder = true)
@Schema(name = "FamilyHistory", description = "Evaluación de antecedentes familiares (Clinical Tier)")
public class FamilyHistoryDto {

    @Schema(description = "Identificador único interno (PK)", example = "30001", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Identificador Global de Antecedentes (HID/FHIR)", example = "FAM-7788-UUID", accessMode = Schema.AccessMode.READ_ONLY)
    private String externalId;

    @NotNull(message = "Medical History {err.mandatory}")
    @Schema(description = "ID del expediente clínico vinculado", example = "102")
    private Long medicalHistoryId;

    @Schema(description = "Indica si es la evaluación vigente", example = "true")
    private Boolean isCurrent;

    @Schema(description = "Información de salud de la madre", example = "Hipertensión controlada")
    private String motherHealthInfo;

    @Schema(description = "Información de salud del padre", example = "Sano")
    private String fatherHealthInfo;

    @Schema(description = "Historial de enfermedades cardíacas en la familia", example = "Abuelo paterno con infarto")
    private String cardiacFamilyHistory;

    @Schema(description = "Historial de enfermedades mentales en la familia", example = "Ninguno")
    private String mentalFamilyHistory;

    @Schema(description = "Historial de diabetes en la familia", example = "Madre con diabetes tipo 2")
    private String diabetesFamilyHistory;

    @Schema(description = "Etnia familiar (Factor de riesgo genético)", example = "Hispano")
    private String familyEthnicity;

    @Schema(description = "Índice de riesgo genético (LOW, MEDIUM, HIGH)", example = "LOW")
    private String geneticRiskIndex;

    @Schema(description = "Notas adicionales", example = "Se recomienda tamizaje de glucosa anual")
    private String additionalNotes;
}
