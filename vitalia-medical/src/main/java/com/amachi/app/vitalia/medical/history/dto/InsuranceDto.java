package com.amachi.app.vitalia.medical.history.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Contrato de aseguramiento de salud (Póliza/Convenio) vinculado directamente a un paciente.
 * Contiene los datos transaccionales necesarios para la facturación y la cobertura clínica.
 */
@Validated
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder(toBuilder = true)
@Schema(name = "Insurance", description = "Esquema transaccional del seguro médico del paciente")
public class InsuranceDto {

    @Schema(description = "Identificador único interno (PK)", example = "7001", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Identificador Global del Seguro (UUID)", example = "INS-5566-UUID", accessMode = Schema.AccessMode.READ_ONLY)
    private String externalId;

    @NotNull(message = "Medical History {err.mandatory}")
    @Schema(description = "ID del expediente clínico vinculado (Medical History)", example = "102")
    private Long medicalHistoryId;

    @NotNull(message = "Healthcare Provider {err.mandatory}")
    @Schema(description = "ID del catálogo maestro de HealthcareProvider", example = "5")
    private Long providerId;

    @Schema(description = "Nombre oficial de la aseguradora (Extraído del catálogo)", example = "ALLIANZ SEGUROS", accessMode = Schema.AccessMode.READ_ONLY)
    private String providerName;

    @Schema(description = "Logo corporativo de la aseguradora (Extraído del catálogo)", example = "assets/logos/allianz.png", accessMode = Schema.AccessMode.READ_ONLY)
    private String providerLogoUrl;

    @Schema(description = "Línea de asistencia 24/7 de la aseguradora (Extraída del catálogo)", example = "+1 800 123 456", accessMode = Schema.AccessMode.READ_ONLY)
    private String providerEmergencyPhone;

    @Schema(description = "Número único de la póliza o carnet asignado al paciente", example = "PX-9900-ABC")
    private String policyNumber;

    @Schema(description = "Categoría o tipo de plan contratado (ej: ORO, PREFERENCIAL, BÁSICO)", example = "ORO")
    private String policyType;

    @Schema(description = "Fecha en que inicia la cobertura actual de la póliza", example = "2024-01-01")
    private LocalDate effectiveDate;

    @Schema(description = "Fecha de vencimiento o renovación de la póliza", example = "2025-12-31")
    private LocalDate expirationDate;

    @Schema(description = "Detalle narrativo sobre alcances y exclusiones de la cobertura", example = "Cubrimiento total en cirugías especializadas")
    private String coverageDetails;

    @Schema(description = "Monto fijo de copago que el paciente debe abonar por consulta", example = "15.00")
    private BigDecimal copayAmount;

    @Schema(description = "Monto del deducible anual pactado con la aseguradora", example = "200.00")
    private BigDecimal deductibleAmount;

    @Schema(description = "Indica si es el seguro de salud principal o por defecto del paciente", example = "true")
    private Boolean isCurrent;
}
