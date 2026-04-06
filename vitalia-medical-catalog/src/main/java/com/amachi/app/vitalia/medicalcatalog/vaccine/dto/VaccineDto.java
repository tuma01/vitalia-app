package com.amachi.app.vitalia.medicalcatalog.vaccine.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;

/**
 * Vaccine Data Transfer Object (SaaS Elite Tier).
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Schema(name = "Vaccine", description = "Schema to hold Vaccine information")
public class VaccineDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("id")
    @Schema(description = "Unique identifier", example = "1")
    private Long id;

    @JsonProperty("code")
    @NotBlank(message = "Code {err.mandatory}")
    @Size(max = 20)
    @Schema(description = "Vaccine Code", example = "V-001")
    private String code;

    @JsonProperty("name")
    @NotBlank(message = "Name {err.mandatory}")
    @Size(max = 150)
    @Schema(description = "Vaccine Name", example = "BCG")
    private String name;

    @JsonProperty("description")
    @Size(max = 250)
    @Schema(description = "Additional information about the vaccine", example = "Tuberculosis vaccine")
    private String description;

    @JsonProperty("active")
    @Schema(description = "Status of the vaccine record", example = "true")
    private Boolean active;
}
