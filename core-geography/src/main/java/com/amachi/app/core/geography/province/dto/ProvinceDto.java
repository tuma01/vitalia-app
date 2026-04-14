package com.amachi.app.core.geography.province.dto;

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
@Schema(name = "Province", description = "Schema to hold Province information")
public class ProvinceDto {

    @Schema(description = "ID of the province.", example = "1")
    private Long id;

    @NotBlank(message = "Name {err.required}")
    @Schema(description = "Name of the province.", example = "Cercado")
    private String name;

    @Schema(description = "Population of the province.", example = "4567")
    private Integer population;

    @Schema(description = "Surface area of the province.", example = "12345")
    private BigDecimal surface;

    @NotNull(message = "State {err.mandatory}")
    @Schema(description = "ID of the State the province belongs to.", example = "1")
    private Long stateId;

}
