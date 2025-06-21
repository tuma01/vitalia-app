package com.amachi.app.vitalia.country.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Builder;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Validated
@Data
@Builder(toBuilder = true)
@Schema(name = "Country", description = "Schema to hold Country information")
public class CountryDto {
    @JsonProperty
    @Schema(description = "Identificador unico", example = "1")
    private Long id;

    @JsonProperty
    @NotBlank(message = "Iso code shouldn't be null")
    @Size(min = 2, max = 2, message = "ISO code must be 2 characters")
    @Schema(description = "ISO code of Country", example = "BO")
    private String iso;

    @JsonProperty
    @NotBlank(message = "Name of Country can not be empty")
    @Schema(description = "Name of Country", example = "BOLIVIA")
    private String name;

    @JsonProperty
    @NotBlank(message = "Nice Name of Country cannot be empty")
    @Schema(description = "Nice name of Country", example = "Bolivia")
    private String niceName;


    @JsonProperty
    @Size(min = 1, max = 3, message = "ISO3 must have between 1 and 3 characters")
    @Schema(description = "ISO3 of Country", example = "BOL")
    private String iso3;

    @JsonProperty
    @Schema(description = "Num Code of Country", example = "68")
    private Integer numCode;

    @JsonProperty
    @NotNull(message = "Phone code shouldn't be null")
    @Min(value = 1, message = "Phone code must be a positive number")
    @Schema(description = "Phone code of the Country", example = "591")
    private Integer phoneCode;
}
