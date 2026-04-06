package com.amachi.app.vitalia.medicalcatalog.demographic.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serializable;

/**
 * Civil Status Data Transfer Object (SaaS Elite Tier).
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Schema(name = "CivilStatus", description = "Schema to hold Civil Status information")
public class CivilStatusDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("id")
    @Schema(description = "Unique identifier", example = "1")
    private Long id;

    @JsonProperty("code")
    @NotBlank(message = "Code {err.mandatory}")
    @Schema(description = "Civil Status Code", example = "SINGLE")
    private String code;

    @JsonProperty("name")
    @NotBlank(message = "Name {err.mandatory}")
    @Schema(description = "Civil Status Name", example = "Single")
    private String name;

    @JsonProperty("active")
    @Schema(description = "Status of the record", example = "true")
    private Boolean active;
}
