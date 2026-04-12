package com.amachi.app.vitalia.medical.history.dto;

import com.amachi.app.vitalia.medical.common.enums.ObservationStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import java.time.OffsetDateTime;

/**
 * Esquema para mediciones clínicas y hallazgos diagnósticos (FHIR Observation).
 * Proporciona un soporte estandarizado para Signos Vitales y Resultados de Laboratorio.
 */
@Validated
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder(toBuilder = true)
@Schema(name = "Observation", description = "Esquema unificado de hallazgo o medición clínica (Grado Hospitalario)")
public class ObservationDto {

    @Schema(description = "Identificador interno (PK)", example = "1001", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Identificador Global de la Observación (FHIR/Publico)", example = "OBS-789-UUID", accessMode = Schema.AccessMode.READ_ONLY)
    private String externalId;

    @NotNull(message = "Paciente {err.mandatory}")
    @Schema(description = "ID del paciente", example = "5001")
    private Long patientId;

    @Schema(description = "Nombre completo del paciente (Solo lectura)", example = "JUAN CARLOS PEREZ", accessMode = Schema.AccessMode.READ_ONLY)
    private String patientFullName;

    @Schema(description = "ID del encuentro médico asociado", example = "12001")
    private Long encounterId;

    @NotNull(message = "Médico {err.mandatory}")
    @Schema(description = "ID del facultativo que registra la medición", example = "2001")
    private Long practitionerId;

    @Schema(description = "Nombre del médico (Solo lectura)", example = "DR. MARCOS SOLIZ", accessMode = Schema.AccessMode.READ_ONLY)
    private String practitionerFullName;

    @NotNull(message = "Estado {err.mandatory}")
    @Schema(description = "Estado de la medición (REGISTERED, PRELIMINARY, FINAL, AMENDED, CANCELLED)", example = "FINAL")
    private ObservationStatus status;

    @NotNull(message = "Código {err.mandatory}")
    @Schema(description = "Código clínico (ej: LOINC, Atómico)", example = "8867-4")
    private String code;

    @Schema(description = "Nombre descriptivo de la prueba/hallazgo", example = "Frecuencia Cardíaca")
    private String name;

    @NotNull(message = "Valor {err.mandatory}")
    @Schema(description = "Valor clínico obtenido", example = "72")
    private String value;

    @Schema(description = "Unidad de medida", example = "bpm")
    private String unit;

    @Schema(description = "Rango de referencia normal", example = "60-100 bpm")
    private String referenceRange;

    @Schema(description = "Interpretación clínica (NORMAL, HIGH, LOW, CRITICAL)", example = "NORMAL")
    private String interpretation;

    @NotNull(message = "Fecha Efectiva {err.mandatory}")
    @Schema(description = "Fecha y hora en que se realizó la medición", example = "2026-03-30T10:35:00Z")
    private OffsetDateTime effectiveDateTime;

    @Schema(description = "Notas adicionales de la observación", example = "Paciente en reposo")
    private String notes;
}
