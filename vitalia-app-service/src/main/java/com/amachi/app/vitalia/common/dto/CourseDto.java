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
@Schema(name = "Course", description = "Schema to hold Course information")
public class CourseDto {

    @Schema(description = "Unique identifier for the course", example = "1")
    private Long id;

    @Schema(description = "Title of the course", example = "Advanced Java Programming")
    private String title;

    @Schema(description = "Institution offering the course", example = "Tech University")
    private String institution;

    @Schema(description = "Description of the course", example = "A comprehensive course on advanced Java programming techniques.")
    private String description;

    @Schema(description = "Date when the course was completed", example = "2023-05-15")
    private LocalDate date;

    @Schema(description = "Duration of the course in hours", example = "40")
    private int durationInHours;

    @Schema(description = "Certificate obtained upon completion of the course", example = "https://example.com/certificate/12345")
    private String certificate;

    @Schema(description = "Profile associated with the course", example = "UserProfile object")
    private Long profileId;
}
