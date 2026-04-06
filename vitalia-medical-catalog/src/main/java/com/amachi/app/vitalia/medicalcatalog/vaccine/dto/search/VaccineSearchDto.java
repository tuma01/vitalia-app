package com.amachi.app.vitalia.medicalcatalog.vaccine.dto.search;

import com.amachi.app.core.common.dto.BaseSearchDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * Enterprise Search Engine for Vaccines (SaaS Elite Tier).
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "VaccineSearch", description = "Search criteria for filtering Vaccine records")
public final class VaccineSearchDto implements BaseSearchDto {

    @JsonProperty("id")
    @Schema(description = "Unique identifier of the vaccine", example = "1")
    private Long id;

    @JsonProperty("code")
    @Schema(description = "Vaccine code filter", example = "V-001")
    private String code;

    @JsonProperty("name")
    @Schema(description = "Vaccine name filter (partial match)", example = "BCG")
    private String name;

    @JsonProperty("active")
    @Schema(description = "Filter by active status", example = "true")
    private Boolean active;
}
