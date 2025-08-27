package com.amachi.app.vitalia.country.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Schema(name = "Country", description = "Schema to hold Country information")

public class CountrySummaryDto {
    @Schema(description = "Identificador unico", example = "1")
    private Long id;

    @NotBlank(message = "Iso code shouldn't be null")
    @Size(min = 2, max = 2, message = "ISO code must be 2 characters")
    @Schema(description = "ISO code of Country", example = "BO")
    private String iso;

    @NotBlank(message = "Name of Country can not be empty")
    @Schema(description = "Name of Country", example = "BOLIVIA")
    private String name;
}
