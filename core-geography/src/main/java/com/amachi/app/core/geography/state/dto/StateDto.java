package com.amachi.app.core.geography.state.dto;

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
@Schema(name = "State", description = "Schema to hold state information")
public class StateDto {

    @JsonProperty
    @Schema(description = "Id of the state", example = "1")
    private Long id;

    @JsonProperty
    @NotBlank(message = "name {err.required}")
    @Schema(description = "Name of the state", example = "La Paz")
    private String name;

    @JsonProperty
    @Schema(description = "Population of the state", example = "4567")
    private Integer population;

    @JsonProperty
    @Schema(description = "Surface area of the state", example = "12345")
    private BigDecimal surface;

    @JsonProperty
    @Schema(description = "Country ID the state belongs to")
    private Long countryId;
}
