package com.amachi.app.core.geography.address.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Schema(name = "Address", description = "Schema to hold Address information")
public class AddressDto {

        @Schema(description = "Id Address de Address", example = "1")
        private Long id;

        @Schema(description = "Numero de la Direccion", example = "11957")
        private String numero;

        @NotBlank(message = "Direccion {err.required}")
        @Schema(description = "Direccion principal de address", example = "calle Jean Talon")
        private String direccion;

        @Schema(description = "Bloque al que pertenece el address", example = "Bloque A")
        private String bloque;

        @Schema(description = "Piso del address", example = "1")
        private Integer piso;

        @Schema(description = "Numero de departamento de la Direccion")
        private String numeroDepartamento;

        @Schema(description = "medidor de la Direccion")
        private String medidor;

        @Schema(description = "Casilla Postal de Address")
        private String casillaPostal;

        @Schema(description = "Ciudad de Address", example = "La Paz")
        private String ciudad;

        @Schema(description = "Geometry data with spatial index")
        private String location;

        @Schema(description = "Pais del Address", example = "4")
        private Long countryId;

        @Schema(description = "El departamento de la Direccion", example = "2")
        private Long departamentoId;

        @Schema(description = "La provincia de la Direccion", example = "3")
        private Long provinciaId;

        @Schema(description = "El municipio de la Direccion", example = "5")
        private Long municipioId;
}
