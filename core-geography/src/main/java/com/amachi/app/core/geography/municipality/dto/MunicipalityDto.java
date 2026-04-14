package com.amachi.app.core.geography.municipality.dto;

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
@Schema(name = "Municipality", description = "Schema to hold Municipality information")
public class MunicipalityDto {

    @JsonProperty
    @Schema(description = "ID of the municipality.", example = "1")
    private Long id;

    @JsonProperty
    @NotBlank(message = "Name {err.required}")
    @Schema(description = "Name of the municipality.", example = "Cercado")
    private String name;

    @JsonProperty
    @Schema(description = "Address of the municipality office.", example = "123 Union Street")
    private String address;

    @JsonProperty
    @Schema(description = "Internal code of the municipality.", example = "12345")
    private Integer municipalityCode;

    @JsonProperty
    @Schema(description = "Population of the municipality.", example = "4567")
    private Integer population;

    @JsonProperty
    @Schema(description = "Surface area of the municipality.", example = "12345")
    private BigDecimal surface;

    @JsonProperty
    @NotNull(message = "Province {err.mandatory}")
    @Schema(description = "ID of the Province the municipality belongs to.", example = "1")
    private Long provinceId;

}
