package com.amachi.app.vitalia.departamentothospital.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(name = "DepartamentoHospital", description = "Schema to hold DepartamentoHospital information")
public class DepartamentoHospitalDto {

    @Schema(description = "Unique identifier of the department", example = "1")
    private Long id;

    @Schema(description ="Hospital where the Department is located", example = "1")
    private Long hospitalId;

    @Schema(description = "Type of Department", example = "Cardiology")
    private DepartamentoTipoDto departamentoTipo;

    @Schema(description ="Description of the Department", example = "Department specializing in heart-related conditions")
    private String description;

    @Schema(description = "floor of the Department", example = "3rd Floor")
    private String floor;

    @Schema(description = "Contact phone number of the Department", example = "+1234567890")
    private String contactPhone;

    @Schema(description = "Head Doctor of the Department")
    private Long headDoctorId;
}
