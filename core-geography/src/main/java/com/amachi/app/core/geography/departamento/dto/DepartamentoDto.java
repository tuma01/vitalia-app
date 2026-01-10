package com.amachi.app.core.geography.departamento.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;

@Validated
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Departamento", description = "Schema to hold Departamento information")
public class DepartamentoDto {

    @JsonProperty
    @Schema(description = "Id del Departamento", example = "1")
    private Long id;

    @JsonProperty
    @NotBlank(message = "Nombre {err.required}")
    @Schema(
            description = "Nombre del Departamento", example = "La Paz"
    )
    private String nombre;

    @JsonProperty
    @Schema(
            description = "Poblacion del Departamento", example = "4567"
    )
    private Integer poblacion;

    @JsonProperty
    @Schema(
            description = "Superficie del Departamento", example = "12345"
    )
    private BigDecimal superficie;

    @JsonProperty
    @Schema(
            description = "Pais al que pertenece el Departamento"
    )
    private Long countryId;
}
