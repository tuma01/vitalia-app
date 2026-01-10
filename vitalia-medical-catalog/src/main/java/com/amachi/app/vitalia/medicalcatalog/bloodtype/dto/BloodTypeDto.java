package com.amachi.app.vitalia.medicalcatalog.bloodtype.dto;

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
@Schema(name = "BloodType", description = "Schema to hold Blood Type information")
public class BloodTypeDto {

    @Schema(description = "Identificador único", example = "1")
    private Long id;

    @NotBlank(message = "Code cannot be empty")
    @Size(max = 10)
    @Schema(description = "Blood Type Code", example = "A+")
    private String code;

    @NotBlank(message = "Name cannot be empty")
    @Size(max = 50)
    @Schema(description = "Blood Type Name", example = "A Positivo")
    private String name;

    @Schema(description = "Status", example = "true")
    private Boolean active;
}
