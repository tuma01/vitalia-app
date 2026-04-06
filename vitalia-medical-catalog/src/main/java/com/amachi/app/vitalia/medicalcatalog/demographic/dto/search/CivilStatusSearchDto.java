package com.amachi.app.vitalia.medicalcatalog.demographic.dto.search;

import com.amachi.app.core.common.dto.BaseSearchDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * Enterprise Search Engine for Civil Statuses (SaaS Elite Tier).
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "CivilStatusSearch", description = "Search criteria for filtering Civil Status records")
public final class CivilStatusSearchDto implements BaseSearchDto {

    @JsonProperty("id")
    @Schema(description = "Unique identifier of the civil status", example = "1")
    private Long id;

    @JsonProperty("code")
    @Schema(description = "Civil status code filter", example = "SOL")
    private String code;

    @JsonProperty("name")
    @Schema(description = "Civil status name filter (partial match)", example = "Soltero/a")
    private String name;

    @JsonProperty("active")
    @Schema(description = "Filter by active status", example = "true")
    private Boolean active;
}
