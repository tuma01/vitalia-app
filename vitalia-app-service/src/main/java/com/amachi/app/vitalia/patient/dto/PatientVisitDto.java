package com.amachi.app.vitalia.patient.dto;

import com.amachi.app.vitalia.common.utils.VisitTypeEnum;
import com.amachi.app.vitalia.historiamedica.dto.HospitalizacionDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Schema(name = "PatientVisit", description = "Schema to hold PatientVisit information")
public class PatientVisitDto {
    @Schema(description = "Unique identifier of the patient visit", example = "1")
    private Long id;

    @Schema(description = "Patient details associated with the visit")
    private Long patientId;

    @Schema(description = "Hospital details where the visit took place")
    private Long hospitalId;

    @Schema(description = "Date of the visit", example = "2023-10-01")
    private LocalDate visitDate;

    @Schema(description = "Reason for the visit", example = "Routine check-up")
    private Long historiaMedicaId;

    @Schema(description = "Observations made during the visit", example = "Patient is in good health")
    private Set<HospitalizacionDto> hospitalizaciones = new HashSet<>();

    @Schema(description = "Categorizes the type of patient visit.",
            example = "INPATIENT_VISIT",
            allowableValues = {"CONSULTA_EXTERNA", "VISITA_EMERGENCIA", "VISITA_EMERGENCIA", "VISITA_QUIRURGICA", "VISITA_DIAGNOSTICA", "VISITA_SEGUIMIENTO", "TRASLADO_ENTRADA", "TELECONSULTA", "VISITA_ADMINISTRATIVA", "OTRO"})
    private VisitTypeEnum visitType;
}
