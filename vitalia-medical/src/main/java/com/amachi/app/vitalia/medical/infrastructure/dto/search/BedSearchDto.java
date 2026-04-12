package com.amachi.app.vitalia.medical.infrastructure.dto.search;

import com.amachi.app.core.common.dto.BaseSearchDto;
import com.amachi.app.core.common.enums.BedStatus;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.*;

/**
 * Filtros de búsqueda dinámica ÉLITE para camas hospitalarias.
 */
@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
@Hidden
public final class BedSearchDto implements BaseSearchDto {
    private Long id;
    private Long roomId;
    private String query; // Búsqueda por código o descripción
    private String bedCode;
    private BedStatus status;
    private Boolean isOccupied;
    private Long hospitalizationId;

    @Override
    public Long getId() {
        return id;
    }
}
