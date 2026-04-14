package com.amachi.app.vitalia.medical.consultation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import java.time.OffsetDateTime;

/**
 * Medical Consultation DTO (SaaS Elite Tier).
 */
@Validated
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder(toBuilder = true)
@Schema(name = "Consultation", description = "Medical encounter and clinical record representation")
public class ConsultationDto {

    @Schema(description = "Primary Key (Internal)", example = "7001")
    private Long id;

    @Schema(description = "SaaS External ID (Idempotency)", example = "330e8400-e29b-41d4-a716-446655449999")
    private String externalId;

    @NotNull(message = "patient.error.required")
    @Schema(description = "Patient ID", example = "5001")
    private Long patientId;

    @Schema(description = "Patient Full Name (Read-only)", example = "JUAN CARLOS PEREZ GARCIA")
    private String patientFullName;

    @Schema(description = "Doctor ID", example = "2001")
    private Long doctorId;

    @Schema(description = "Doctor Full Name (Read-only)", example = "DR. RODRIGUEZ")
    private String doctorFullName;

    @NotNull(message = "history.error.required")
    @Schema(description = "Medical History Context ID", example = "9001")
    private Long medicalHistoryId;

    @Schema(description = "Consultation Type ID", example = "10")
    private Long typeId;

    @NotNull(message = "consultation.date.error.required")
    @Schema(description = "Consultation date and time", example = "2026-03-25T14:30:00Z")
    private OffsetDateTime consultationDate;

    @Schema(description = "Clinical status (PENDING, COMPLETED)", example = "COMPLETED")
    private String status;

    @Schema(description = "Additional clinical remarks and findings")
    private String notes;

    @Schema(description = "Triage or urgency level (NORMAL, URGENT, EMERGENCY)", example = "URGENT")
    private String triageLevel;
}
