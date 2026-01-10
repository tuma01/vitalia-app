package com.amachi.app.vitalia.medicalcatalog.allergy.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
@Schema(name = "Allergy", description = "Schema to hold Allergy information")
public class AllergyDto {
    private Long id;
    @NotBlank private String code;
    @NotBlank private String name;
    @NotBlank private String type;
    private String description;
    private Boolean active;
}
