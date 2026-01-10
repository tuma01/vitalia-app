package com.amachi.app.core.geography.municipio.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
@Schema(name = "Municipio", description = "Schema to hold Municipio information")
public class MunicipioDto {

    @JsonProperty
    @Schema(
            description = "Id Municipio de un Municipio", example = "1"
    )
    private Long id;

    @JsonProperty
    @NotBlank(message = "Nombre {err.required}")
    @Schema(
            description = "Nombre del Municipio", example = "Cercado"
    )
    private String nombre;

    @JsonProperty
    @Schema(
            description = "Direccion del Municipio", example = "4567 calle Union"
    )
    private String direccion;

    @JsonProperty
    @Schema(
            description = "Codigo del Municipio", example = "12345"
    )
    private Integer codigoMunicipio;

    @JsonProperty
    @Schema(
            description = "Poblacion del Municipio", example = "4567"
    )
    private Integer poblacion;

    @JsonProperty
    @Schema(
            description = "Superficie del Municipio", example = "12345"
    )
    private BigDecimal superficie;

    @JsonProperty
    @NotNull(message = "Provincia {err.mandatory}")
    @Schema(
            description = "ID Provincia al que pertenece el Municipio", example = "1"
    )
    private Long provinciaId;

}
