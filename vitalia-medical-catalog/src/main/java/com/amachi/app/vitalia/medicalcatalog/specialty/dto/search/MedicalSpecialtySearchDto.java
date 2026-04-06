package com.amachi.app.vitalia.medicalcatalog.specialty.dto.search;

import com.amachi.app.core.common.dto.BaseSearchDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * Search DTO for Medical Specialty catalog, following standard patterns.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "MedicalSpecialtySearch", description = "Filter criteria for Medical Specialties")
public final class MedicalSpecialtySearchDto implements BaseSearchDto {

    @JsonProperty("id")
    @Schema(description = "Internal ID", example = "1")
    private Long id;

    @JsonProperty("code")
    @Schema(description = "Unique alphanumeric code", example = "PED-001")
    private String code;

    @JsonProperty("name")
    @Schema(description = "Specialty name", example = "Pediatrics")
    private String name;

    @JsonProperty("targetProfession")
    @Schema(description = "Target profession associated", example = "DOCTOR")
    private String targetProfession;

    @JsonProperty("active")
    @Schema(description = "Availability status")
    private Boolean active;
}
