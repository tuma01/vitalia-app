package com.amachi.app.vitalia.medicalcatalog.diagnosis.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.validation.annotation.Validated;

/**
 * Schema to hold ICD-10 Diagnosis information.
 */
@Validated
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Schema(name = "Icd10", description = "Schema to hold ICD-10 Diagnosis information")
public class Icd10Dto {

    @Schema(description = "Identificador único", example = "1")
    private Long id;

    @NotBlank(message = "ICD-10 Code cannot be empty")
    @Size(max = 10, message = "ICD-10 Code must be at most 10 characters")
    @Schema(description = "ICD-10 Code", example = "A00.0")
    private String code;

    @NotBlank(message = "Description cannot be empty")
    @Size(max = 500, message = "Description must be at most 500 characters")
    @Schema(description = "Diagnosis Description", example = "Cholera due to Vibrio cholerae 01, biovar cholerae")
    private String description;

    @Schema(description = "Status of the diagnosis", example = "true")
    private Boolean active;
}
