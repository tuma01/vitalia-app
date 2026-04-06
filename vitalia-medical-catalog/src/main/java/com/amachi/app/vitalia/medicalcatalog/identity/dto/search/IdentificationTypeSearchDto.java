package com.amachi.app.vitalia.medicalcatalog.identity.dto.search;

import com.amachi.app.core.common.dto.BaseSearchDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * Enterprise Search Engine for Identification Types (SaaS Elite Tier).
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "IdentificationTypeSearch", description = "Search criteria for filtering Identification Type records")
public final class IdentificationTypeSearchDto implements BaseSearchDto {

    @JsonProperty("id")
    @Schema(description = "Unique identifier of the identification type", example = "1")
    private Long id;

    @JsonProperty("code")
    @Schema(description = "Identification type code filter", example = "DNI")
    private String code;

    @JsonProperty("name")
    @Schema(description = "Identification type name filter (partial match)", example = "Pasaporte")
    private String name;

    @JsonProperty("active")
    @Schema(description = "Filter by active status", example = "true")
    private Boolean active;
}
