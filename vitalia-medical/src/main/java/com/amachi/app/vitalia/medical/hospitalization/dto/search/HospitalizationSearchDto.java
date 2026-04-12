package com.amachi.app.vitalia.medical.hospitalization.dto.search;

import com.amachi.app.core.common.dto.BaseSearchDto;
import com.amachi.app.vitalia.medical.common.enums.HospitalizationStatus;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Filtros de búsqueda dinámica ÉLITE para procesos de hospitalización.
 */
@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
@Hidden
public final class HospitalizationSearchDto implements BaseSearchDto {
    private Long id;
    private Long patientId;
    private Long doctorId;
    private Long unitId;
    private Long bedId;
    private HospitalizationStatus status;
    private LocalDateTime admissionDateFrom;
    private LocalDateTime admissionDateTo;

    @Override
    public Long getId() { return id; }
}
