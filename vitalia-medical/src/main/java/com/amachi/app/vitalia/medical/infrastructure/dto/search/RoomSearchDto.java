package com.amachi.app.vitalia.medical.infrastructure.dto.search;

import com.amachi.app.core.common.dto.BaseSearchDto;
import com.amachi.app.vitalia.medical.common.enums.CleaningStatus;
import com.amachi.app.vitalia.medical.common.enums.TypeRoomEnum;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.*;

/**
 * Filtros de búsqueda dinámica ÉLITE para habitaciones hospitalarias.
 */
@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
@Hidden
public final class RoomSearchDto implements BaseSearchDto {
    private Long id;
    private Long unitId;
    private String query; // Búsqueda por número o descripción
    private String roomNumber;
    private String blockCode;
    private TypeRoomEnum typeRoom;
    private CleaningStatus cleaningStatus;
    private Boolean isPrivate;
    private Boolean active;

    @Override
    public Long getId() {
        return id;
    }
}
