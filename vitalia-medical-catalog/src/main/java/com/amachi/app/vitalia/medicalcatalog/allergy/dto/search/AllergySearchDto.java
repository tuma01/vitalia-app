package com.amachi.app.vitalia.medicalcatalog.allergy.dto.search;

import com.amachi.app.core.common.dto.BaseSearchDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Enterprise Search Engine for Allergies (SaaS Elite Tier).
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "AllergySearch", description = "Search criteria for filtering Allergy records")
public final class AllergySearchDto implements BaseSearchDto {

    @JsonProperty("id")
    @Schema(description = "Unique identifier of the allergy", example = "1")
    private Long id;

    @JsonProperty("code")
    @Schema(description = "Allergy code filter", example = "DRUG-001")
    private String code;

    @JsonProperty("name")
    @Schema(description = "Allergy name filter (partial match)", example = "Penicilina")
    private String name;

    @JsonProperty("type")
    @Schema(description = "Allergy type filter", example = "DRUG", allowableValues = {"DRUG", "FOOD", "ENVIRONMENTAL", "OTHER"})
    private String type;

    @JsonProperty("active")
    @Schema(description = "Filter by active status", example = "true")
    private Boolean active;
}
