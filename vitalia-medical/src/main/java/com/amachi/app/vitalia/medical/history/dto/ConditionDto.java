package com.amachi.app.vitalia.medical.history.dto;

import com.amachi.app.vitalia.medical.common.enums.ClinicalStatus;
import com.amachi.app.vitalia.medical.common.enums.ConditionType;
import com.amachi.app.vitalia.medical.common.enums.Severity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;

/**
 * Esquema para el registro de patologías y diagnósticos (FHIR Condition).
 * Utilizado para el seguimiento longitudinal de la salud del paciente.
 */
@Validated
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder(toBuilder = true)
@Schema(name = "Condition", description = "Esquema unificado de diagnóstico clínico (CIE-10)")
public class ConditionDto {

    @Schema(description = "Identificador único interno (PK)", example = "15001")
    private Long id;

    @Schema(description = "Identificador Global de la Condición (FHIR)", example = "COND-456-UUID", accessMode = Schema.AccessMode.READ_ONLY)
    private String externalId;

    @NotNull(message = "Paciente {err.mandatory}")
    @Schema(description = "ID del paciente", example = "5001")
    private Long patientId;

    @Schema(description = "Nombre completo del paciente (Solo lectura)", example = "JUAN CARLOS PEREZ", accessMode = Schema.AccessMode.READ_ONLY)
    private String patientFullName;

    @Schema(description = "ID del encuentro médico asociado", example = "12001")
    private Long encounterId;

    @Schema(description = "ID del código CIE-10 asignado", example = "1")
    private Long icd10Id;

    @Schema(description = "Código CIE-10 (Solo lectura)", example = "A00.0", accessMode = Schema.AccessMode.READ_ONLY)
    private String icd10Code;

    @Schema(description = "Nombre del diagnóstico (Literal)", example = "Cólera debido a Vibrio cholerae 01, biotipo cholerae")
    private String name;

    @NotNull(message = "Médico {err.mandatory}")
    @Schema(description = "ID del facultativo diagnosticador", example = "2001")
    private Long practitionerId;

    @Schema(description = "Nombre del médico (Solo lectura)", example = "DR. MARCOS SOLIZ", accessMode = Schema.AccessMode.READ_ONLY)
    private String practitionerFullName;

    @NotNull(message = "Estado Clínico {err.mandatory}")
    @Schema(description = "Estado actual de la patología (ACTIVE, RECURRENCE, RELAPSE, INACTIVE, REMISSION, RESOLVED)", example = "ACTIVE")
    private ClinicalStatus clinicalStatus;

    @NotNull(message = "Tipo Condición {err.mandatory}")
    @Schema(description = "Tipo de diagnóstico (PROBLEM_LIST_ITEM, ENCOUNTER_DIAGNOSIS)", example = "ENCOUNTER_DIAGNOSIS")
    private ConditionType conditionType;

    @Schema(description = "Gravedad de la patología (SEVERE, MODERATE, MILD)", example = "MODERATE")
    private Severity severity;

    @Schema(description = "Sintomatología asociada", example = "Diarrea severa, deshidratación")
    private String symptoms;

    @Schema(description = "Notas de tratamiento específico", example = "Hidratación intravenosa inmediata")
    private String treatmentNotes;

    @NotNull(message = "Fecha Diagnóstico {err.mandatory}")
    @Schema(description = "Fecha formal del diagnóstico", example = "2026-03-30")
    private LocalDate diagnosisDate;

    @Schema(description = "Fecha de resolución o abatimiento de la condición", example = "2026-04-10")
    private LocalDate abatementDate;

    @Schema(description = "ID del episodio de cuidado al que pertenece", example = "301")
    private Long episodeOfCareId;

    @NotNull(message = "History ID {err.mandatory}")
    @Schema(description = "ID del expediente clínico vinculado", example = "102")
    private Long medicalHistoryId;
}
