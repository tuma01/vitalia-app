package com.amachi.app.vitalia.medicalcatalog.procedure.dto.search;

import com.amachi.app.core.common.dto.BaseSearchDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * Enterprise Search Engine for Medical Procedures (SaaS Elite Tier).
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "MedicalProcedureSearch", description = "Search criteria for filtering Medical Procedure records")
public final class MedicalProcedureSearchDto implements BaseSearchDto {

    @JsonProperty("id")
    @Schema(description = "Unique identifier of the procedure", example = "1")
    private Long id;

    @JsonProperty("code")
    @Schema(description = "Procedure code filter", example = "90.3.8.01")
    private String code;

    @JsonProperty("name")
    @Schema(description = "Procedure name filter (partial match)", example = "HEMOGRAMA")
    private String name;

    @JsonProperty("type")
    @Schema(description = "Filter by procedure type", example = "LABORATORY")
    private String type;

    @JsonProperty("active")
    @Schema(description = "Filter by active status", example = "true")
    private Boolean active;
}
