package com.amachi.app.vitalia.medicalcatalog.healthcareprovider.dto.search;

import com.amachi.app.core.common.dto.BaseSearchDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * Enterprise Search Engine for Healthcare Providers (SaaS Elite Tier).
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "HealthcareProviderSearch", description = "Search criteria for filtering Healthcare Provider records")
public final class HealthcareProviderSearchDto implements BaseSearchDto {

    @JsonProperty("id")
    @Schema(description = "Unique identifier of the provider", example = "1")
    private Long id;

    @JsonProperty("code")
    @Schema(description = "Provider code filter", example = "EPS-001")
    private String code;

    @JsonProperty("name")
    @Schema(description = "Provider name filter (partial match)", example = "SURA")
    private String name;

    @JsonProperty("taxId")
    @Schema(description = "Tax ID filter", example = "860066942-7")
    private String taxId;

    @JsonProperty("active")
    @Schema(description = "Filter by active status", example = "true")
    private Boolean active;
}
