package com.amachi.app.vitalia.medical.history.dto.request;

import com.amachi.app.vitalia.medical.common.enums.ClinicalStatus;
import com.amachi.app.vitalia.medical.common.enums.ConditionType;
import com.amachi.app.vitalia.medical.common.enums.Severity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * ≡ƒôô Solicitud para registrar un diagn├│stico formal (FHIR Condition).
 */
@NoArgsConstructor @AllArgsConstructor @Builder
@Schema(description = "Solicitud para añadir diagnóstico clínico")
public class ConditionRequest {

    @NotNull(message = "El ID de la CIE-10 es obligatorio")
    @Schema(description = "ID del cat├ílogo CIE-10 (ICD-10)", example = "45")
    private Long icd10Id;

    @NotNull(message = "El estado cl├¡nico es obligatorio")
    @Schema(description = "Estado actual (ACTIVE, INACTIVE, etc.)")
    private ClinicalStatus clinicalStatus;

    @Schema(description = "Tipo de condici├│n (ACUTE, CHRONIC, etc.)")
    private ConditionType conditionType;

    @Schema(description = "Gravedad (SEVERE, MILD, etc.)")
    private Severity severity;

    @Schema(description = "S├¡ntomas o justificaci├│n", example = "Dolor abdominal agudo")
    private String symptoms;

    @Schema(description = "Notas adicionales de tratamiento")
    private String treatmentNotes;

    // Manual Accessors
    public Long getIcd10Id() { return icd10Id; }
    public void setIcd10Id(Long icd10Id) { this.icd10Id = icd10Id; }
    public ClinicalStatus getClinicalStatus() { return clinicalStatus; }
    public void setClinicalStatus(ClinicalStatus clinicalStatus) { this.clinicalStatus = clinicalStatus; }
    public ConditionType getConditionType() { return conditionType; }
    public void setConditionType(ConditionType conditionType) { this.conditionType = conditionType; }
    public Severity getSeverity() { return severity; }
    public void setSeverity(Severity severity) { this.severity = severity; }
    public String getSymptoms() { return symptoms; }
    public void setSymptoms(String symptoms) { this.symptoms = symptoms; }
    public String getTreatmentNotes() { return treatmentNotes; }
    public void setTreatmentNotes(String treatmentNotes) { this.treatmentNotes = treatmentNotes; }
}
