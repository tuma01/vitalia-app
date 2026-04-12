package com.amachi.app.vitalia.medical.history.dto.search;

import com.amachi.app.core.common.dto.BaseSearchDto;
import com.amachi.app.vitalia.medical.common.enums.ObservationStatus;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.*;

import java.time.OffsetDateTime;

/**
 * Filtros de búsqueda dinámica ÉLITE para Hallazgos y Observaciones (FHIR).
 */
@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
@Hidden
public final class ObservationSearchDto implements BaseSearchDto {
    private Long id;
    private Long patientId;
    private Long encounterId;
    private Long practitionerId;
    private String code;
    private String query; // Búsqueda textual por nombre o notas
    private ObservationStatus status;
    private String interpretation;
    private OffsetDateTime effectiveDateFrom;
    private OffsetDateTime effectiveDateTo;

    @Override
    public Long getId() { return id; }
}
