package com.amachi.app.vitalia.doctorprofessionspeciality.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Schema(name = "DoctorProfessionSpeciality", description = "Schema to hold DoctorProfessionSpeciality information")
public class DoctorProfessionSpecialityDto {

    @JsonProperty
    @Schema(description = "Id DoctorProfessionSpeciality", example = "1")
    private Long id;

    @JsonProperty
    @Schema(
            description = "Name of DoctorProfessionSpeciality", example = "Ophthalmology"
    )
    @NotBlank(message = "Name {err.required}")
    private String name;

    @JsonProperty
    @Schema(
            description = "Description of DoctorProfessionSpeciality", example = "Diagnosis and treatment of eye conditions"
    )
    private String description;
}
