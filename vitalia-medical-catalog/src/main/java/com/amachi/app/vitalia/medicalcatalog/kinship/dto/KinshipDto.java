package com.amachi.app.vitalia.medicalcatalog.kinship.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.validation.annotation.Validated;

@Validated
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Schema(name = "Kinship", description = "Schema to hold Kinship information")
public class KinshipDto {

    @JsonProperty("id")
    @Schema(description = "Identificador único", example = "1")
    private Long id;

    @JsonProperty("code")
    @NotBlank(message = "Code {err.mandatory}")
    @Size(max = 20)
    @Schema(description = "Kinship Code", example = "FATHER")
    private String code;

    @JsonProperty("name")
    @NotBlank(message = "Nombre {err.mandatory}")
    @Size(max = 100)
    @Schema(description = "Kinship Name", example = "Padre")
    private String name;

    @JsonProperty("active")
    @Schema(description = "Status", example = "true")
    private Boolean active;
}
