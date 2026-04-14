package com.amachi.app.vitalia.medical.history.dto;

import com.amachi.app.vitalia.medical.common.enums.EpisodeOfCareStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;

/**
 * Esquema para la gestión de episodios de cuidado (FHIR EpisodeOfCare).
 * Agrupa eventos clínicos relacionados con una condición específica (ej: embarazo, tratamiento oncológico).
 */
@Validated
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder(toBuilder = true)
@Schema(name = "EpisodeOfCare", description = "Esquema de gestión de cuidado continuado (Analytics Tier)")
public class EpisodeOfCareDto {

    @Schema(description = "Identificador único interno (PK)", example = "8001", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Identificador Global del Episodio (UUID)", example = "EPI-1122-UUID", accessMode = Schema.AccessMode.READ_ONLY)
    private String externalId;

    @NotNull(message = "Paciente {err.mandatory}")
    @Schema(description = "ID del paciente", example = "5001")
    private Long patientId;

    @Schema(description = "Nombre completo del paciente (Solo lectura)", example = "JUAN CARLOS PEREZ", accessMode = Schema.AccessMode.READ_ONLY)
    private String patientFullName;

    @NotNull(message = "Responsable {err.mandatory}")
    @Schema(description = "ID del médico responsable del episodio", example = "2001")
    private Long managingDoctorId;

    @Schema(description = "Nombre del médico responsable (Solo lectura)", example = "DR. MARCOS SOLIZ", accessMode = Schema.AccessMode.READ_ONLY)
    private String managingDoctorFullName;

    @NotNull(message = "Estado {err.mandatory}")
    @Schema(description = "Estado del episodio (PLANNED, ACTIVE, ONHOLD, FINISHED, CANCELLED)", example = "ACTIVE")
    private EpisodeOfCareStatus status;

    @Schema(description = "Tipo de episodio (ej: OBSTETRIC)", example = "OBSTETRIC")
    private String typeDescription;

    @NotNull(message = "Inicio {err.mandatory}")
    @Schema(description = "Fecha de inicio del episodio", example = "2026-03-30T10:00:00Z")
    private java.time.OffsetDateTime periodStart;

    @Schema(description = "Fecha de finalización del episodio", example = "2026-12-30T10:00:00Z")
    private java.time.OffsetDateTime periodEnd;

    @Schema(description = "Objetivos clínicos del episodio", example = "Control prenatal completo y parto seguro")
    private String goals;

    @Schema(description = "Notas adicionales", example = "Paciente de alto riesgo por edad")
    private String notes;
}
