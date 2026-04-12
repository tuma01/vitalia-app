package com.amachi.app.vitalia.medical.history.dto.search;

import com.amachi.app.core.common.dto.BaseSearchDto;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.*;

/**
 * Filtros de búsqueda dinámica ÉLITE para el Expediente Clínico (EHR).
 */
@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
@Hidden
public final class MedicalHistorySearchDto implements BaseSearchDto {
    private Long id;
    private Long patientId;
    private String historyNumber; 
    private String patientFullName;
    private String recordVersion;
    private Boolean isOrganDonor;

    @Override
    public Long getId() {
        return id;
    }
}
