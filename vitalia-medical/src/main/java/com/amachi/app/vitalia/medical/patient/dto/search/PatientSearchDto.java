package com.amachi.app.vitalia.medical.patient.dto.search;

import com.amachi.app.core.common.dto.BaseSearchDto;
import com.amachi.app.core.common.enums.PatientStatus;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.*;

/**
 * Filtros de búsqueda dinámica ÉLITE para Pacientes (Standard GOLD HIS).
 */
@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
@Hidden
public final class PatientSearchDto implements BaseSearchDto {
    private Long id;
    private String query; // Búsqueda por nombre o apellidos
    private String externalId;
    private String nationalId;
    private String nhc;
    private String email;
    private String mobileNumber;
    private PatientStatus patientStatus;
    private Long currentBedId;

    @Override
    public Long getId() {
        return id;
    }
}
