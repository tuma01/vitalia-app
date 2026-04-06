package com.amachi.app.vitalia.medicalcatalog.demographic.dto.search;

import com.amachi.app.core.common.dto.BaseSearchDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * Enterprise Search Engine for Genders (SaaS Elite Tier).
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "GenderSearch", description = "Search criteria for filtering Gender records")
public final class GenderSearchDto implements BaseSearchDto {

    @JsonProperty("id")
    @Schema(description = "Unique identifier of the gender", example = "1")
    private Long id;

    @JsonProperty("code")
    @Schema(description = "Gender code filter", example = "M")
    private String code;

    @JsonProperty("name")
    @Schema(description = "Gender name filter (partial match)", example = "Masculino")
    private String name;

    @JsonProperty("active")
    @Schema(description = "Filter by active status", example = "true")
    private Boolean active;
}
