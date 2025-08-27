package com.amachi.app.vitalia.doctor.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Schema(name = "DoctorHospitalAssignment", description = "Schema to hold DoctorHospitalAssignment information")
public class DoctorHospitalAssignmentDto {

    @Schema(
            description = "Unique identifier for the Doctor-Hospital assignment",
            example = "1"
    )
    private Long id;

    @Schema(
            description = "Doctor associated with the assignment",
            example = "5L"
    )
    private Long doctorId;

    @Schema(
            description = "Hospital associated with the assignment",
            example = "3L"
    )
    private Long hospitalId;


    @Schema(
            description = "Start date of the assignment",
            example = "2023-01-01"
    )
    private LocalDate startDate;

    @Schema(
            description = "End date of the assignment, can be null if the doctor is still assigned",
            example = "2023-12-31"
    )
    private LocalDate endDate;
}
