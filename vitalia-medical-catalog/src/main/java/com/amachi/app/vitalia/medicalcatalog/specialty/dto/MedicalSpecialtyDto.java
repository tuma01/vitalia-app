package com.amachi.app.vitalia.medicalcatalog.specialty.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * Standardized MedicalSpecialty DTO following SaaS Elite Tier patterns.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "MedicalSpecialty", description = "Standard Medical Specialty representation")
public final class MedicalSpecialtyDto {

    @JsonProperty("id")
    @Schema(description = "Unique Identifier", example = "1")
    private Long id;

    @JsonProperty("code")
    @NotBlank(message = "Specialty Code {err.mandatory}")
    @Size(max = 20, message = "Code {err.size.max}")
    @Schema(description = "Official Specialty Code", example = "ESP-001")
    private String code;

    @JsonProperty("name")
    @NotBlank(message = "Specialty Name {err.mandatory}")
    @Size(max = 150, message = "Name {err.size.max}")
    @Schema(description = "Specialty Name", example = "Cardiology")
    private String name;

    @JsonProperty("description")
    @Size(max = 500, message = "Description {err.size.max}")
    @Schema(description = "Brief description of the specialty", example = "Specialty dedicated to the study and treatment of the heart.")
    private String description;

    @JsonProperty("targetProfession")
    @NotBlank(message = "Target Profession {err.mandatory}")
    @Schema(description = "Target Profession (DOCTOR, NURSE, BOTH)", example = "DOCTOR")
    private String targetProfession;

    @JsonProperty("active")
    @Schema(description = "Status of the specialty", example = "true")
    private Boolean active;
}
