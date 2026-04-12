package com.amachi.app.vitalia.medical.history.dto.search;

import com.amachi.app.core.common.dto.BaseSearchDto;
import com.amachi.app.core.common.enums.VisitTypeEnum;
import com.amachi.app.vitalia.medical.common.enums.EncounterStatus;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.*;

import java.time.OffsetDateTime;

/**
 * Filtros de búsqueda dinámica ÉLITE para Encuentros Clínicos (FHIR).
 */
@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
@Hidden
public final class EncounterSearchDto implements BaseSearchDto {
    private Long id;
    private Long patientId;
    private Long doctorId;
    private Long episodeOfCareId;
    private Long appointmentId;
    private Long medicalHistoryId;
    private EncounterStatus status;
    private VisitTypeEnum encounterType;
    private OffsetDateTime encounterDateFrom;
    private OffsetDateTime encounterDateTo;
    private String triageLevel;

    @Override
    public Long getId() { return id; }
}
