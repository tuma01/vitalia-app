package com.amachi.app.vitalia.medicalcatalog.bloodtype.dto.search;

import com.amachi.app.core.common.dto.BaseSearchDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * Enterprise Search Engine for Blood Types (SaaS Elite Tier).
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "BloodTypeSearch", description = "Search criteria for filtering Blood Type records")
public final class BloodTypeSearchDto implements BaseSearchDto {

    @JsonProperty("id")
    @Schema(description = "Unique identifier of the blood type", example = "1")
    private Long id;

    @JsonProperty("code")
    @Schema(description = "Blood type code filter", example = "A+")
    private String code;

    @JsonProperty("name")
    @Schema(description = "Blood type name filter (partial match)", example = "A Positivo")
    private String name;

    @JsonProperty("active")
    @Schema(description = "Filter by active status", example = "true")
    private Boolean active;
}
