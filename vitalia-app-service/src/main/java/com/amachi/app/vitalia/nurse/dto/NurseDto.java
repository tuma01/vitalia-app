package com.amachi.app.vitalia.nurse.dto;

import com.amachi.app.vitalia.dto.PersonDto;
import com.amachi.app.vitalia.nurseprofessionspeciality.dto.NurseProfessionSpecialityDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = true)
@Schema(name = "Nurse", description = "Schema to hold Nurse information")
public class NurseDto extends PersonDto {
    @Schema(
            description = "Photo of Nurses "
    )
    @JsonProperty
    private byte[] photo;

    @Schema(
            description = "Id Card of Nurses", example = "123457"
    )
    @JsonProperty
    private String idCard;

    @Schema(
            description = "The Specialities of Nurses"
    )
    @JsonProperty
    private Set<NurseProfessionSpecialityDto> nurseProfessionSpecialities;
}
