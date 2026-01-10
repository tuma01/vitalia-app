package com.amachi.app.vitalia.medicalcatalog.demographic.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
@Schema(name = "Gender", description = "Schema to hold Gender information")
public class GenderDto {
    private Long id;
    @NotBlank private String code;
    @NotBlank private String name;
    private Boolean active;
}
