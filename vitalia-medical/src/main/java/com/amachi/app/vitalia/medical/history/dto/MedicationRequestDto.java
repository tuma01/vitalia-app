package com.amachi.app.vitalia.medical.history.dto;

import com.amachi.app.vitalia.medical.common.enums.MedicationRequestStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

/**
 * Esquema para órdenes de medicación y recetas electrónicas (FHIR MedicationRequest).
 */
@Validated
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder(toBuilder = true)
@Schema(name = "MedicationRequest", description = "Esquema de prescripción o receta médica (Clinical Tier)")
public class MedicationRequestDto {

    @Schema(description = "Identificador único interno (PK)", example = "9001", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Identificador Global de la Receta (UUID)", example = "RX-3344-UUID", accessMode = Schema.AccessMode.READ_ONLY)
    private String externalId;

    @NotNull(message = "Paciente {err.mandatory}")
    @Schema(description = "ID del paciente", example = "5001")
    private Long patientId;

    @Schema(description = "Nombre del paciente (Solo lectura)", example = "JUAN CARLOS PEREZ", accessMode = Schema.AccessMode.READ_ONLY)
    private String patientFullName;

    @Schema(description = "ID del encuentro asociado", example = "12001")
    private Long encounterId;

    @NotNull(message = "Médico {err.mandatory}")
    @Schema(description = "ID del profesional prescriptor", example = "2001")
    private Long practitionerId;

    @Schema(description = "Nombre del médico (Solo lectura)", example = "DR. MARCOS SOLIZ", accessMode = Schema.AccessMode.READ_ONLY)
    private String practitionerFullName;

    @Schema(description = "ID del medicamento (Catálogo maestro)", example = "10")
    private Long medicationId;

    @Schema(description = "Nombre del medicamento (Solo lectura/Literal)", example = "Paracetamol 500mg Tabletas")
    private String medicationDisplayName;

    @NotNull(message = "Estado {err.mandatory}")
    @Schema(description = "Estado de la receta (ACTIVE, ON_HOLD, CANCELLED, COMPLETED, ENTERED_IN_ERROR, STOPPED, DRAFT, UNKNOWN)", example = "ACTIVE")
    private MedicationRequestStatus status;

    @NotNull(message = "Fecha {err.mandatory}")
    @Schema(description = "Fecha de emisión de la receta", example = "2026-03-30T11:00:00Z")
    private OffsetDateTime authoredOn;

    @Schema(description = "Instrucciones de dosificación", example = "1 tableta cada 8 horas por 3 días")
    private String dosageInstruction;

    @Schema(description = "Vía de administración", example = "ORAL")
    private String route;

    @Schema(description = "Frecuencia", example = "BID (Dos veces al día)")
    private String frequency;

    @Schema(description = "Cantidad total prescrita", example = "10.00")
    private BigDecimal quantity;

    @Schema(description = "Prioridad (ROUTINE, URGENT, STAT, ASAP)", example = "ROUTINE")
    private String priority;

    @Schema(description = "Notas adicionales", example = "Tomar con abundante agua")
    private String notes;
}
