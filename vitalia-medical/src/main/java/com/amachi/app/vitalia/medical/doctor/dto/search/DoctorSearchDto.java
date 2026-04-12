package com.amachi.app.vitalia.medical.doctor.dto.search;

import com.amachi.app.core.common.dto.BaseSearchDto;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.*;

/**
 * Filtros de búsqueda avanzada ÉLITE para Médicos.
 * Soporta control de Billing y relación de Nómina (Standard GOLD Amachi).
 */
@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
@Hidden
public final class DoctorSearchDto implements BaseSearchDto {
    private Long id;
    private String query; // Filtro por nombre o apellidos
    private String licenseNumber;
    private String providerNumber;
    private String specialty; // Búsqueda textual en especialidadesSummary
    private String nationalId;
    private Long employeeId;
    private Long departmentUnitId;

    @Override
    public Long getId() {
        return id;
    }
}
