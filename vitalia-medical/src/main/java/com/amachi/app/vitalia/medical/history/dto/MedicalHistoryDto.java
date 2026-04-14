package com.amachi.app.vitalia.medical.history.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;

/**
 * Esquema Elite para la gestión y consulta del expediente clínico longitudinal (EHR).
 */
@Validated
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder(toBuilder = true)
@Schema(name = "MedicalHistory", description = "Expediente maestro de salud longitudinal del paciente")
public class MedicalHistoryDto {

    @Schema(description = "Identificador interno (DB ID)", example = "7001", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Identificador Global del Expediente (FHIR/HID)", example = "HIS-789-UUID", accessMode = Schema.AccessMode.READ_ONLY)
    private String externalId;

    @NotNull(message = "Número de Historia {err.mandatory}")
    @Schema(description = "Código funcional de la historia clínica", example = "HC-2026-X001")
    private String historyNumber;
    @Schema(description = "UUID Global del documento", example = "550e8400-e29b-41d4-a716-446655440000")
    private String documentUuid;

    // --- Identidad y Contexto ---
    @NotNull(message = "Paciente {err.mandatory}")
    @Schema(description = "ID del paciente propietario", example = "5001")
    private Long patientId;

    @Schema(description = "Nombre completo del paciente (Solo lectura)", example = "JUAN PEREZ")
    private String patientFullName;

    @Schema(description = "ID del médico responsable principal", example = "2001")
    private Long responsibleDoctorId;

    @Schema(description = "Nombre del médico responsable (Solo lectura)", example = "DR. MARIO SOLIZ")
    private String responsibleDoctorName;

    @Schema(description = "ID del Tenant/SaaS", example = "1")
    private Long tenantId;

    // --- Biometría y Riesgos ---
    // El grupo sanguíneo se gestiona ahora desde el DTO del paciente (PatientDto).

    @Schema(description = "Indica si es donante de órganos", example = "true")
    private Boolean isOrganDonor;

    // --- Estado y Seguridad ---
    @Schema(description = "Estado administrativo (ACTIVE, ARCHIVED, LOCKED)", example = "ACTIVE")
    private String status;

    @Schema(description = "Flag de bloqueo legal", example = "false")
    private Boolean isLocked;

    @Schema(description = "Nivel de confidencialidad (NORMAL, VIP, SECRET)", example = "NORMAL")
    private String confidentialityLevel;

    @Schema(description = "Versión del expediente", example = "1")
    private Integer recordVersion;

    @Schema(description = "Indica si es la versión actual", example = "true")
    private Boolean isCurrent;

    // --- Cronología ---
    @NotNull(message = "Fecha Apertura {err.mandatory}")
    @Schema(description = "Fecha de inicio del registro", example = "2026-03-24")
    private LocalDate recordDate;

    @Schema(description = "Fecha de vigencia final", example = "2027-03-24")
    private LocalDate validUntil;

    // --- Notas y Observaciones ---
    @Schema(description = "Notas descriptivas generales")
    private String notes;

    @Schema(description = "Observaciones clínicas del facultativo")
    private String observations;
}
