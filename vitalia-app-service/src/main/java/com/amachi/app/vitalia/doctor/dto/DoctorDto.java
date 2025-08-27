package com.amachi.app.vitalia.doctor.dto;

import com.amachi.app.vitalia.doctorprofessionspeciality.dto.DoctorProfessionSpecialityDto;
import com.amachi.app.vitalia.historiamedica.dto.HistoriaMedicaDto;
import com.amachi.app.vitalia.user.dto.PersonDto;
import com.amachi.app.vitalia.user.dto.UserProfileDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Schema(name = "Doctor", description = "Schema to hold Doctor information")
public class DoctorDto extends PersonDto {

    @Schema(
            description = "Actividades o especialidades del Medico",
            example = "[{\"speciality\":\"Ophthalmology\"}]"
    )
    @JsonProperty
    private Set<DoctorProfessionSpecialityDto> doctorProfessionSpecialities;

    @Schema(
            description = "Licencia del Doctor",
            example = "LIC-123456789"
    )
    private String licenseNumber;

    @Schema(
            description = "Hospitales donde trabaja el Doctor",
            example = "[{\"id\":1,\"name\":\"Hospital Central\"}]"
    )
    @JsonProperty
    private Set<DoctorHospitalAssignmentDto> doctorHospitalAssignments;

    @Schema(
            description = "Historias Medicas asociadas al Doctor",
            example = "[{\"id\":1,\"patientName\":\"John Doe\",\"diagnosis\":\"Flu\"}]"
    )
    @JsonProperty
    private Set<HistoriaMedicaDto> historiaMedicas;

    @Schema(
            description = "Indicates if the Doctor is available for appointments",
            example = "true"
    )
    @JsonProperty
    private Boolean isAvailable;

    @Schema(
            description = "Summary of the Doctor's specialties",
            example = "Ophthalmology, Cardiology"
    )
    private String specialtiesSummary;

    @Schema(
            description = "Years of experience of the Doctor",
            example = "10"
    )
    private Integer yearsOfExperience;

    @Schema(
            description = "Rating of the Doctor",
            example = "4.5"
    )
    private Double rating;

    @Schema(
            description = "Profile of the Doctor",
            example = "{\"id\":1,\"username\":\"dr.john\",\"email\":\"emailprofile@exememple.com\"}")
    private UserProfileDto profile;
}
