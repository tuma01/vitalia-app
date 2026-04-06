package com.amachi.app.vitalia.medicalcatalog.kinship.dto.search;

import com.amachi.app.core.common.dto.BaseSearchDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * Enterprise Search Engine for Kinships (SaaS Elite Tier).
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "KinshipSearch", description = "Search criteria for filtering Kinship records")
public final class KinshipSearchDto implements BaseSearchDto {

    @JsonProperty("id")
    @Schema(description = "Unique identifier of the kinship", example = "1")
    private Long id;

    @JsonProperty("code")
    @Schema(description = "Kinship code filter", example = "FATHER")
    private String code;

    @JsonProperty("name")
    @Schema(description = "Kinship name filter (partial match)", example = "Padre")
    private String name;

    @JsonProperty("active")
    @Schema(description = "Filter by active status", example = "true")
    private Boolean active;
}
