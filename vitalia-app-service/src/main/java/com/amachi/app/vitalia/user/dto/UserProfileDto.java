package com.amachi.app.vitalia.user.dto;

import com.amachi.app.vitalia.common.dto.ConferenceDto;
import com.amachi.app.vitalia.common.dto.CourseDto;
import com.amachi.app.vitalia.common.dto.EducationDto;
import com.amachi.app.vitalia.common.dto.ExperienceDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
@Schema(name = "UserProfile", description = "Schema to hold User Profile information")
public class UserProfileDto {

    @Schema(description = "Unique identifier of the user", example = "1")
    private Long id;

    @Schema(description = "Biography of the user profile", example = "Experienced software developer with a passion for building scalable applications.")
    private String biography;

    @Schema(description = "Photo of the user profile", example = "base64EncodedImageString")
    private byte[] photo;

    @Schema(description = "List of education details associated with the user profile")
    private List<EducationDto> educationList = new ArrayList<>();

    @Schema(description = "List of experience details associated with the user profile")
    private List<ExperienceDto> experienceList = new ArrayList<>();

    @Schema(description = "List of courses associated with the user profile")
    private List<CourseDto> courseList = new ArrayList<>();

    @Schema(description = "List of conferences associated with the user profile")
    private List<ConferenceDto> conferenceList = new ArrayList<>();
}
