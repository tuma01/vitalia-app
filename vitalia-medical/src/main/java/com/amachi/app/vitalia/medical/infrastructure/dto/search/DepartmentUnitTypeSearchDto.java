package com.amachi.app.vitalia.medical.infrastructure.dto.search;

import com.amachi.app.core.common.dto.BaseSearchDto;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.*;

/**
 * Filtros de búsqueda dinámica ÉLITE para tipos de unidad departamental.
 */
@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
@Hidden
public final class DepartmentUnitTypeSearchDto implements BaseSearchDto {
    private Long id;
    private String query; // Búsqueda por nombre

    @Override
    public Long getId() {
        return id;
    }
}
