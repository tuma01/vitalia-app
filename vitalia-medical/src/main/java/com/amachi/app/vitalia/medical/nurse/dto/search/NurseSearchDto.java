package com.amachi.app.vitalia.medical.nurse.dto.search;

import com.amachi.app.core.common.dto.BaseSearchDto;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.*;

/**
 * Filtros de búsqueda dinámica ÉLITE para personal de enfermería staff.
 */
@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
@Hidden
public final class NurseSearchDto implements BaseSearchDto {
    private Long id;
    private String query; // Búsqueda por nombres o apellidos
    private String licenseNumber;
    private Long departmentUnitId;
    private String workShift;
    private Boolean active;

    @Override
    public Long getId() {
        return id;
    }
}
