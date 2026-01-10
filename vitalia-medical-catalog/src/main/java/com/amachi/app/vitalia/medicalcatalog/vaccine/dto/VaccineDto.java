package com.amachi.app.vitalia.medicalcatalog.vaccine.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
@Schema(name = "Vaccine", description = "Schema to hold Vaccine information")
public class VaccineDto {
    private Long id;
    @NotBlank private String code;
    @NotBlank private String name;
    private String description;
    private Boolean active;
}
