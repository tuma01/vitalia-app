package com.amachi.app.vitalia.nurse.dto;

import com.amachi.app.vitalia.user.dto.PersonDto;
import com.amachi.app.vitalia.nurseprofessionspeciality.dto.NurseProfessionSpecialityDto;
import com.amachi.app.vitalia.user.dto.UserProfileDto;
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
    private byte[] photo;

    @Schema(
            description = "Id Card of Nurses", example = "123457"
    )
    private String idCard;

    @Schema(
            description = "The Specialities of Nurses"
    )
    private Set<NurseProfessionSpecialityDto> nurseProfessionSpecialities;

    @Schema(
            description = "User Profile of Nurses"
    )
    private UserProfileDto profile;
}
