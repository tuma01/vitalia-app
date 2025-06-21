package com.amachi.app.vitalia.nurseprofessionspeciality.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Schema(name = "NurseProfessionSpeciality", description = "Schema to hold NurseProfessionSpeciality information")

public class NurseProfessionSpecialityDto {

    @JsonProperty
    @Schema(description = "Id NursingSpecialty of Nurse", example = "1")
    private Long id;

    @Schema(
            description = "Name of Nurses speciality", example = "EMERGENCIAS"
    )
    @JsonProperty
    private String name;

    @Schema(
            description = "Description of Nurse specialitys", example = "Enfermería de emergencias"
    )
    @JsonProperty
    private String description;

}