package com.amachi.app.vitalia.medicalcatalog.identity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
@Schema(name = "IdentificationType", description = "Schema to hold Identification Type information")
public class IdentificationTypeDto {
    private Long id;
    @NotBlank private String code;
    @NotBlank private String name;
    private Boolean active;
}
