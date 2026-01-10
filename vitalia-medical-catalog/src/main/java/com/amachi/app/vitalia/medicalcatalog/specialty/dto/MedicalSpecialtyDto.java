package com.amachi.app.vitalia.medicalcatalog.specialty.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.validation.annotation.Validated;

@Validated
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Schema(name = "MedicalSpecialty", description = "Schema to hold Medical Specialty information")
public class MedicalSpecialtyDto {

    @Schema(description = "Identificador único", example = "1")
    private Long id;

    @NotBlank(message = "Specialty Code cannot be empty")
    @Size(max = 20, message = "Code must be at most 20 characters")
    @Schema(description = "Official Specialty Code", example = "ESP-001")
    private String code;

    @NotBlank(message = "Specialty Name cannot be empty")
    @Size(max = 150, message = "Name must be at most 150 characters")
    @Schema(description = "Specialty Name", example = "Cardiología")
    private String name;

    @Size(max = 500, message = "Description must be at most 500 characters")
    @Schema(description = "Brief description of the specialty", example = "Especialidad dedicada al estudio y tratamiento del corazón.")
    private String description;

    @NotBlank(message = "Target Profession cannot be empty")
    @Schema(description = "Target Profession (DOCTOR, NURSE, BOTH)", example = "DOCTOR")
    private String targetProfession;

    @Schema(description = "Status of the specialty", example = "true")
    private Boolean active;
}
