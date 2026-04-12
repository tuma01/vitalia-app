package com.amachi.app.vitalia.medical.professional.dto.search;

import com.amachi.app.core.common.dto.BaseSearchDto;
import com.amachi.app.core.common.enums.ProfessionalRoleContext;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.*;

/**
 * Filtros dinámicos para la gestión de Trayectoria Profesional.
 */
@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
@Hidden
public final class ProfessionalInfoSearchDto implements BaseSearchDto {
    private Long id;
    private Long personId;
    private ProfessionalRoleContext professionalRoleContext;
    private String position;
    private String department;
    private Boolean isCurrent;

    @Override
    public Long getId() {
        return id;
    }
}
