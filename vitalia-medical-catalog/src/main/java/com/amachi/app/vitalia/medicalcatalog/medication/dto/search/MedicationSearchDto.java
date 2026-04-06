package com.amachi.app.vitalia.medicalcatalog.medication.dto.search;

import com.amachi.app.core.common.dto.BaseSearchDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * Enterprise Search Engine for Medication (Vademecum - SaaS Elite Tier).
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "MedicationSearch", description = "Search criteria for filtering Medication records")
public final class MedicationSearchDto implements BaseSearchDto {

    @JsonProperty("id")
    @Schema(description = "Unique identifier of the medication", example = "1")
    private Long id;

    @JsonProperty("code")
    @Schema(description = "Medication code filter", example = "MED-001")
    private String code;

    @JsonProperty("genericName")
    @Schema(description = "Generic pharmacological name filter (partial match)", example = "Paracetamol")
    private String genericName;

    @JsonProperty("commercialName")
    @Schema(description = "Commercial brand name filter (partial match)", example = "Tylenol")
    private String commercialName;

    @JsonProperty("pharmaceuticalForm")
    @Schema(description = "Pharmaceutical dosage form filter", example = "TABLET")
    private String pharmaceuticalForm;

    @JsonProperty("active")
    @Schema(description = "Filter by active status", example = "true")
    private Boolean active;
}
