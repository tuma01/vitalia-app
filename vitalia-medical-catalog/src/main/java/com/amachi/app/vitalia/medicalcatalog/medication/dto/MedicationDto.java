package com.amachi.app.vitalia.medicalcatalog.medication.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.validation.annotation.Validated;

@Validated
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Schema(name = "Medication", description = "Schema to hold Medication (Vademecum) information")
public class MedicationDto {

    @Schema(description = "Identificador único", example = "1")
    private Long id;

    @NotBlank(message = "Medication Code cannot be empty")
    @Size(max = 20, message = "Medication Code must be at most 20 characters")
    @Schema(description = "Unique code of the medication", example = "MED-001")
    private String code;

    @NotBlank(message = "Generic Name cannot be empty")
    @Size(max = 250, message = "Generic Name must be at most 250 characters")
    @Schema(description = "Generic Name of the medication", example = "Paracetamol")
    private String genericName;

    @Size(max = 250, message = "Commercial Name must be at most 250 characters")
    @Schema(description = "Commercial Name of the medication", example = "Tylenol")
    private String commercialName;

    @Size(max = 100, message = "Concentration must be at most 100 characters")
    @Schema(description = "Concentration level", example = "500mg")
    private String concentration;

    @Size(max = 100, message = "Pharmaceutical Form must be at most 100 characters")
    @Schema(description = "Pharmaceutical Form", example = "TABLET")
    private String pharmaceuticalForm;

    @Size(max = 250, message = "Presentation must be at most 250 characters")
    @Schema(description = "Presentation", example = "BOX X 30 TABLETS")
    private String presentation;

    @Schema(description = "Status of the medication", example = "true")
    private Boolean active;
}
