package com.amachi.app.vitalia.medical.history.dto.search;

import com.amachi.app.core.common.dto.BaseSearchDto;
import com.amachi.app.vitalia.medical.common.enums.ClinicalStatus;
import com.amachi.app.vitalia.medical.common.enums.ConditionType;
import com.amachi.app.vitalia.medical.common.enums.Severity;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.*;

import java.time.LocalDate;

/**
 * Filtros de búsqueda dinámica ÉLITE para Diagnósticos y Condiciones (FHIR).
 */
@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
@Hidden
public final class ConditionSearchDto implements BaseSearchDto {
    private Long id;
    private Long patientId;
    private Long encounterId;
    private Long practitionerId;
    private Long icd10Id;
    private String icd10Code;
    private Long episodeOfCareId;
    private Long medicalHistoryId;
    private ClinicalStatus clinicalStatus;
    private ConditionType conditionType;
    private Severity severity;
    private LocalDate diagnosisDateFrom;
    private LocalDate diagnosisDateTo;

    @Override
    public Long getId() { return id; }
}
