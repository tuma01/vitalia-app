package com.amachi.app.vitalia.medicalcatalog.kinship.dto;

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
@Schema(name = "Kinship", description = "Schema to hold Kinship information")
public class KinshipDto {

    @Schema(description = "Identificador único", example = "1")
    private Long id;

    @NotBlank(message = "Code cannot be empty")
    @Size(max = 20)
    @Schema(description = "Kinship Code", example = "FATHER")
    private String code;

    @NotBlank(message = "Name cannot be empty")
    @Size(max = 100)
    @Schema(description = "Kinship Name", example = "Padre")
    private String name;

    @Schema(description = "Status", example = "true")
    private Boolean active;
}
