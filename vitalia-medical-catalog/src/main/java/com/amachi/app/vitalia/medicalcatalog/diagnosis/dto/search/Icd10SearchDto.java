package com.amachi.app.vitalia.medicalcatalog.diagnosis.dto.search;

import com.amachi.app.core.common.dto.BaseSearchDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * Enterprise Search Engine for ICD-10 Diagnosis (SaaS Elite Tier).
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Icd10Search", description = "Search criteria for filtering ICD-10 diagnosis records")
public final class Icd10SearchDto implements BaseSearchDto {

    @JsonProperty("id")
    @Schema(description = "Unique identifier of the diagnosis", example = "1")
    private Long id;

    @JsonProperty("code")
    @Schema(description = "ICD-10 Code filter", example = "A00.0")
    private String code;

    @JsonProperty("description")
    @Schema(description = "Diagnosis description filter (partial match)", example = "Cholera")
    private String description;

    @JsonProperty("active")
    @Schema(description = "Filter by active status", example = "true")
    private Boolean active;
}
