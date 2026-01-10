package com.amachi.app.core.geography.country.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.validation.annotation.Validated;

@Validated
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Schema(name = "Country", description = "Schema to hold Country information")
public class CountryDto {

    @Schema(description = "Identificador unico", example = "1")
    private Long id;

    @NotBlank(message = "Iso code shouldn't be null")
    @Size(min = 2, max = 2, message = "ISO code must be 2 characters")
    @Schema(description = "ISO code of Country", example = "BO")
    private String iso;

    @NotBlank(message = "Name of Country can not be empty")
    @Schema(description = "Name of Country", example = "BOLIVIA")
    private String name;

    @NotBlank(message = "Nice Name of Country cannot be empty")
    @Schema(description = "Nice name of Country", example = "Bolivia")
    private String niceName;


    @Size(min = 1, max = 3, message = "ISO3 must have between 1 and 3 characters")
    @Schema(description = "ISO3 of Country", example = "BOL")
    private String iso3;

    @Schema(description = "Num Code of Country", example = "68")
    private Integer numCode;

    @NotNull(message = "Phone code shouldn't be null")
    @Min(value = 1, message = "Phone code must be a positive number")
    @Schema(description = "Phone code of the Country", example = "591")
    private Integer phoneCode;
}
