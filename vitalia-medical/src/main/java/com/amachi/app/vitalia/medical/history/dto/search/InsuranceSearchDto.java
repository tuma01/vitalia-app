package com.amachi.app.vitalia.medical.history.dto.search;

import com.amachi.app.core.common.dto.BaseSearchDto;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.*;

/**
 * Filtros de búsqueda dinámica ÉLITE para Seguros Médicos (SaaS Elite Tier).
 */
@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
@Hidden
public final class InsuranceSearchDto implements BaseSearchDto {
    private Long id;
    private Long medicalHistoryId;
    private Long providerId;
    private String policyNumber;
    private String policyType;
    private Boolean isCurrent;

    @Override
    public Long getId() {
        return id;
    }
}
