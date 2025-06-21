package com.amachi.app.vitalia.departamentothospital.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Schema(name = "DepartamentoHospital", description = "Schema to hold DepartamentoHospital information")
public class DepartamentoHospitalDto {

    @Schema(description = "id of Department ", example = "1")
    private Long id;

    @JsonProperty
    @Schema(
            description = "Name of Department", example = "Pediatría"
    )
    @NotBlank(message = "Name {err.required}")
    private String name;

    @JsonProperty
    @Schema(
            description = "Description of Department", example = "Departamento enfocado en el cuidado de niños y adolescentes."
    )
    private String description;
}
