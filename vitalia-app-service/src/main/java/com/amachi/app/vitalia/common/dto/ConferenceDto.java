package com.amachi.app.vitalia.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Schema(name = "Conference", description = "Schema to hold Conference information")
public class ConferenceDto {

    @Schema(description = "Unique identifier for the course", example = "1")
    private Long id;

    @Schema(description = "Title of the conference", example = "International Health Conference 2023")
    private String topic;

    @Schema(description = "Description of the conference", example = "A conference discussing global health issues and innovations.")
    private String description;

    @Schema(description = "Organizer of the conference", example = "World Health Organization")
    private String organizer;

    @Schema(description = "Location of the conference", example = "Geneva, Switzerland")
    private String location;

    @Schema(description = "Indicates if the conference is international", example = "true")
    private boolean international;

    @Schema(description = "Start date of the conference", example = "2023-10-01")
    private LocalDate date;

    @Schema(description = "Profile associated with the course", example = "UserProfile object")
    private Long profileId;
}
