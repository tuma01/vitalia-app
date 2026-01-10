package com.amachi.app.vitalia.medicalcatalog.demographic.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
@Schema(name = "CivilStatus", description = "Schema to hold Civil Status information")
public class CivilStatusDto {
    private Long id;
    @NotBlank private String code;
    @NotBlank private String name;
    private Boolean active;
}
