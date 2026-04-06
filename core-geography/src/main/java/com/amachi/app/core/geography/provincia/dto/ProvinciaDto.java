package com.amachi.app.core.geography.provincia.dto;

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
@Schema(name = "Provincia", description = "Schema to hold Provincia information")
public class ProvinciaDto {

    @Schema(
            description = "ID de una Provincia", example = "1"
    )
    private Long id;

    @NotBlank(message = "Nombre {err.required}")
    @Schema(
            description = "Nombre del Provincia", example = "Cercado"
    )
    private String nombre;

    @Schema(
            description = "Poblacion de la Provincia", example = "4567"
    )
    private Integer poblacion;

    @Schema(
            description = "Superficie de la Provincia", example = "12345"
    )
    private BigDecimal superficie;

    @NotNull(message = "{err.mandatory}")
    @Schema(
            description = "ID Departamento al que pertenece la Provincia", example = "1"
    )
    private Long departamentoId;

}
