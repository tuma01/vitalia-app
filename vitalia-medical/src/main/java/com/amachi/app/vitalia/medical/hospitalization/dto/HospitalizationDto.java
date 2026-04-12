package com.amachi.app.vitalia.medical.hospitalization.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Enterprise Schema for Hospitalization DTO (SaaS Elite Tier).
 */
@Validated
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder(toBuilder = true)
@Schema(name = "Hospitalization", description = "Clinical control for hospital inpatient episodes")
public class HospitalizationDto {

    @Schema(description = "Internation unique identifier (Internal)", example = "8001", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Identificador Global del Episodio (FHIR)", example = "HOSP-990-UUID", accessMode = Schema.AccessMode.READ_ONLY)
    private String externalId;

    @NotNull(message = "Patient {err.mandatory}")
    @Schema(description = "ID of the hospitalized patient", example = "5001")
    private Long patientId;

    @Schema(description = "Patient full name (Read only)", example = "JOHN DOE", accessMode = Schema.AccessMode.READ_ONLY)
    private String patientFullName;

    @Schema(description = "ID of the source clinical encounter", example = "3001")
    private Long encounterId;

    @Schema(description = "ID of the management hospital unit", example = "101")
    private Long unitId;

    @Schema(description = "Unit name (Read only)", example = "INTENSIVE CARE UNIT (ICU)", accessMode = Schema.AccessMode.READ_ONLY)
    private String unitName;

    @Schema(description = "Assigned room ID", example = "202")
    private Long roomId;

    @Schema(description = "Room number/code (Read only)", example = "304-A", accessMode = Schema.AccessMode.READ_ONLY)
    private String roomNumber;

    @Schema(description = "Assigned bed ID", example = "505")
    private Long bedId;

    @Schema(description = "Bed code (Read only)", example = "CAMA-01-B", accessMode = Schema.AccessMode.READ_ONLY)
    private String bedCode;

    @NotNull(message = "Responsible Doctor {err.mandatory}")
    @Schema(description = "ID of the doctor in charge", example = "2001")
    private Long doctorId;

    @Schema(description = "Responsible doctor name (Read only)", example = "DR. SMITH", accessMode = Schema.AccessMode.READ_ONLY)
    private String doctorFullName;

    @Schema(description = "Shift head nurse ID", example = "4001")
    private Long nurseId;

    @Schema(description = "Responsible nurse name (Read only)", example = "LIC. JANE DOE", accessMode = Schema.AccessMode.READ_ONLY)
    private String nurseFullName;

    @NotNull(message = "Admission Type {err.mandatory}")
    @Schema(description = "Type of entry (URGENCY, PROGRAMMED)", example = "URGENCY")
    private String admissionType;

    @Schema(description = "Criticality level for hospitalization", example = "CRITICAL")
    private String priority;

    @Schema(description = "Hospitalization operational status", example = "ADMITTED")
    private String status;

    @Schema(description = "Formal medical discharge classification", example = "RECOVERED")
    private String dischargeStatus;

    @Schema(description = "Reason for admission", example = "ACUTE ABDOMINAL PAIN")
    private String admissionReason;

    @Schema(description = "Initial diagnosis at admission", example = "ACUTE APPENDICITIS")
    private String admissionDiagnosis;

    @Schema(description = "Final diagnosis after studies", example = "SUPPURATED ACUTE APPENDICITIS")
    private String finalDiagnosis;
    
    @Schema(description = "Treatment plan during stay", example = "LAPAROSCOPIC APPENDECTOMY")
    private String treatmentPlan;

    @Schema(description = "Reason for clinical discharge", example = "EVIDENT CLINICAL IMPROVEMENT")
    private String dischargeReason;

    @NotNull(message = "Admission Date {err.mandatory}")
    @Schema(description = "Exact date and time of hospital admission", example = "2026-03-24T10:00:00")
    private LocalDateTime admissionDate;

    @Schema(description = "Estimated discharge date (Planning)", example = "2026-03-28T14:30:00")
    private LocalDateTime estimatedDischargeDate;

    @Schema(description = "Actual clinical discharge date and time", example = "2026-03-28T14:30:00")
    private LocalDateTime dischargeDate;

    @Schema(description = "Scheduled follow-up date", example = "2026-04-05")
    private java.time.LocalDate followUpDate;

    @Schema(description = "Insurance authorization number", example = "AUTH-12345-XYZ")
    private String insuranceAuthorizationNumber;

    @Schema(description = "Accumulated cost (Informational)", example = "1500.50", accessMode = Schema.AccessMode.READ_ONLY)
    private BigDecimal totalCost;

    @Schema(description = "Cost currency", example = "USD")
    private String currency;

    @Schema(description = "Additional clinical observations", example = "Patient requires soft diet")
    private String observations;

    @Schema(description = "Discharge instructions given to patient")
    private String dischargeInstructions;
}
