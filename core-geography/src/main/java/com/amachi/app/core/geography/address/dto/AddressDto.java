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
@Schema(name = "Address", description = "Schema to hold address information.")
public class AddressDto {

    @Schema(description = "ID of the address.", example = "1")
    private Long id;

    @Schema(description = "Street number.", example = "123")
    private String streetNumber;

    @NotBlank(message = "Street name {err.required}")
    @Schema(description = "Street name or main address line.", example = "Av. 16 de Julio")
    private String streetName;

    @Schema(description = "Block or building identifier.", example = "Block A")
    private String block;

    @Schema(description = "Floor number.", example = "3")
    private Integer floor;

    @Schema(description = "Apartment or suite number.")
    private String apartmentNumber;

    @Schema(description = "Utility meter number.")
    private String meterNumber;

    @Schema(description = "Postal code / Zip code.")
    private String zipCode;

    @Schema(description = "City name.", example = "La Paz")
    private String city;

    @Schema(description = "Geometry data or coordinates.")
    private String location;

    @Schema(description = "Country ID.", example = "26")
    private Long countryId;

    @Schema(description = "State ID.", example = "8")
    private Long stateId;

    @Schema(description = "Province ID.", example = "1")
    private Long provinceId;

    @Schema(description = "Municipality ID.", example = "1")
    private Long municipalityId;
}
