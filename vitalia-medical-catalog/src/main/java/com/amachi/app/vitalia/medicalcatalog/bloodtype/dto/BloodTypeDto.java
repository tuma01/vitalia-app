package com.amachi.app.vitalia.medicalcatalog.bloodtype.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;

/**
 * Blood Type Data Transfer Object (SaaS Elite Tier).
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Schema(name = "BloodType", description = "Schema to hold Blood Type information")
public class BloodTypeDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("id")
    @Schema(description = "Unique identifier", example = "1")
    private Long id;

    @JsonProperty("code")
    @NotBlank(message = "Code {err.mandatory}")
    @Size(max = 20)
    @Schema(description = "Blood Type Code", example = "A+")
    private String code;

    @JsonProperty("name")
    @NotBlank(message = "Name {err.mandatory}")
    @Size(max = 50)
    @Schema(description = "Blood Type Name", example = "A Positivo")
    private String name;

    @JsonProperty("active")
    @Schema(description = "Status of the blood type record", example = "true")
    private Boolean active;
}
