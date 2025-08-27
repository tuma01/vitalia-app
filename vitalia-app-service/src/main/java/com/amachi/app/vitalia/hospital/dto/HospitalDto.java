package com.amachi.app.vitalia.hospital.dto;

import com.amachi.app.vitalia.address.dto.AddressDto;
import com.amachi.app.vitalia.common.entities.Model;
import com.amachi.app.vitalia.departamentothospital.dto.DepartamentoHospitalDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(name = "Hospital", description = "Schema to hold Hospital information")
public class HospitalDto implements Model {

    @Schema(description = "Id Hospital of Hospital ", example = "1", hidden = true)
    private Long id;

    @NotBlank(message = "Name {err.required}")
    @Schema(description = "Name of Hospital", example = "Hospital Jean Talon")
    private String name;

    @Schema(description = "Legal Name of Hospital", example = "Hospital Jean Talon S.A.")
    private String legalName;

    @NotBlank(message = "Hospital Code {err.required}")
    @Schema(description = "Hospital Code", example = "HJT-001")
    private String hospitalCode;

    @Schema(description = "Description of Hospital", example = "A leading hospital in Montreal providing comprehensive healthcare services.")
    private String description;

    @Schema(description = "The telephone of Hospital", example = "514 2367944")
    private String telephone;

    @Schema(description = "The secondary telephone of Hospital", example = "514 1234567")
    private String secondaryTelephone;

    @NotBlank(message = "Email {err.required}")
    @Schema(description = "The email of Hospital", example = "hospital@mihospital.com")
    private String email;

    @Schema(description = "The web site of Hospital", example = "https://www.mihospital.com")
    private String webSite;

    @Schema(description = "The tax identification number of Hospital", example = "123456789")
    private String taxIdentificationNumber;

    @Schema(description = "The license number of Hospital", example = "LIC-123456")
    private String licenseNumber;

    @Schema(description = "The bed capacity of Hospital", example = "500")
    private Integer bedCapacity;

    @Schema(description = "The foundation year of Hospital", example = "1990")
    private Integer foundationYear;

    @Schema(description = "The time zone of Hospital", example = "America/Montreal")
    private String timeZone;

    @JsonProperty
    @Schema(description = "The logo of Hospital", example = "Base64 encoded image")
    private byte[] logo;

    @JsonProperty
    @Schema(description = "Indicates if this is the default hospital", example = "true")
    private boolean isDefault = false;

    @JsonProperty
    @Schema(description = "The address of Hospital", example = "{ \"idAddress\":1 }")
    private AddressDto address;

    @JsonProperty
    @Schema(description = "The Departments of Hospital")
    private Set<DepartamentoHospitalDto> departamentoHospitals;
}
