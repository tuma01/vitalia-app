package com.amachi.app.vitalia.medicalcatalog.diagnosis.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;

/**
 * ICD-10 Diagnosis Data Transfer Object (SaaS Elite Tier).
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Schema(name = "Icd10", description = "Schema to hold ICD-10 Diagnosis information")
public class Icd10Dto implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("id")
    @Schema(description = "Unique identifier", example = "1")
    private Long id;

    @JsonProperty("code")
    @NotBlank(message = "Code {err.mandatory}")
    @Size(max = 20)
    @Schema(description = "ICD-10 Code", example = "A00.0")
    private String code;

    @JsonProperty("description")
    @NotBlank(message = "Description {err.mandatory}")
    @Size(max = 500)
    @Schema(description = "Diagnosis Description", example = "Cholera due to Vibrio cholerae 01, biovar cholerae")
    private String description;

    @JsonProperty("active")
    @Schema(description = "Status of the diagnosis record", example = "true")
    private Boolean active;
}
