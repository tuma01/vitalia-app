package com.amachi.app.core.geography.address.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Schema(name = "Address", description = "Schema to hold Address information")
public class AddressDto {

    @JsonProperty
    @Schema(
            description = "Id Address de Address", example = "1"
    )
    private Long id;

    @JsonProperty
    @Schema(
            description = "Numero de la Direccion", example = "11957"
    )
    private String numero;

    @JsonProperty
    @NotBlank(message = "Direccion {err.required}")
    @Schema(
            description = "Direccion principal de address", example = "calle Jean Talon"
    )
    private String direccion;

    @JsonProperty
    @Schema(
            description = "Bloque al que pertenece el address", example = "Bloque A"
    )
    private String bloque;

    @JsonProperty
    @Schema(
            description = "Piso del address", example = "1"
    )
    private Integer piso;

    @JsonProperty
    @Schema(
            description = "Numero de departamento de la Direccion"
    )
    private String numeroDepartamento;

    @JsonProperty
    @Schema(
            description = "medidor de la Direccion"
    )
    private String medidor;

    @JsonProperty
    @Schema(
            description = "Casilla Postal de Address"
    )
    private String casillaPostal;

    @JsonProperty
    @Schema(
            description = "Ciudad de Address", example = "La Paz"
    )
    private String ciudad;

    @JsonProperty
    @Schema(
            description = "Geometry data with spatial index"
    )
    private String location;


    @JsonProperty
    @Schema( description = "Pais del Address", example = "4" )
    private Long countryId;

    @JsonProperty
    @Schema( description = "El departamento de la Direccion", example = "2" )
    private Long departamentoId;
}
