package com.amachi.app.vitalia.medical.infrastructure.dto;

import com.amachi.app.core.common.enums.BedStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;

/**
 * Esquema Profesional Elite para la gestión de Camas Hospitalarias.
 * Soporta control de ocupación clínica y trazabilidad técnica.
 */
@Validated
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder(toBuilder = true)
@Schema(name = "Bed", description = "Unidad mínima de ocupación hospitalaria (Cama/Box)")
public class BedDto {

    @Schema(description = "Identificador único de la cama", example = "1001")
    private Long id;

    @Schema(description = "Identificador Global (FHIR/Publico)", example = "BED-01A-PEDIATRIA")
    private String externalId;

    @NotNull(message = "Habitación {err.mandatory}")
    @Schema(description = "ID de la habitación a la que pertenece", example = "701")
    private Long roomId;

    @Schema(description = "Número o nombre de la habitación (Solo lectura)", example = "HAB-301")
    private String roomNumber;

    @NotBlank(message = "Código Cama {err.mandatory}")
    @Schema(description = "Código identificador físico de la cama", example = "CAMA-B")
    private String bedCode;

    @Schema(description = "Número de rotulación física", example = "01-B")
    private String bedNumber;

    @Schema(description = "Estado operativo actual", example = "AVAILABLE")
    private BedStatus status;

    @Schema(description = "Indica si la cama está físicamente ocupada", example = "false")
    private Boolean isOccupied;

    @Schema(description = "Especificaciones técnicas (ej: Eléctrica, Ortopédica)", example = "CAMA ELÉCTRICA MULTIPOSICIÓN")
    private String description;

    // --- Relación Clínica ---
    @Schema(description = "ID de la hospitalización vigente que ocupa la cama", example = "8001")
    private Long hospitalizationId;

    @Schema(description = "Nombre del paciente actual (Solo lectura)", example = "JUAN CARLOS PEREZ")
    private String currentPatientName;

    // --- Mantenimiento ---
    @Schema(description = "Fecha de próximo mantenimiento técnico", example = "2026-12-31")
    private LocalDate maintenanceDue;
}
