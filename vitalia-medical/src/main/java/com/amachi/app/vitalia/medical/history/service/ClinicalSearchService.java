package com.amachi.app.vitalia.medical.history.service;

import com.amachi.app.vitalia.medical.history.dto.search.ConditionSearchDto;
import com.amachi.app.vitalia.medical.history.dto.search.EncounterSearchDto;
import com.amachi.app.vitalia.medical.history.dto.search.ObservationSearchDto;
import com.amachi.app.vitalia.medical.history.dto.timeline.ClinicalEventDto;
import com.amachi.app.vitalia.medical.history.entity.Condition;
import com.amachi.app.vitalia.medical.history.entity.Encounter;
import com.amachi.app.vitalia.medical.history.entity.Observation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * ≡ƒöì Motor de b├║squeda cl├¡nica unificada (Elite Tier Clinical Search).
 * Cruza datos de Actos M├⌐dicos, Diagn├│sticos, Mediciones y Prescripciones.
 */
public interface ClinicalSearchService {

    Page<Encounter> searchEncounters(EncounterSearchDto criteria, Pageable pageable);

    Page<Condition> searchConditions(ConditionSearchDto criteria, Pageable pageable);

    Page<Observation> searchObservations(ObservationSearchDto criteria, Pageable pageable);

    /**
     * Construye la l├¡nea de tiempo cl├¡nica completa del paciente.
     * Consolida todos los registros cl├¡nicos en una sola vista cronol├│gica descendente.
     */
    List<ClinicalEventDto> getPatientClinicalHistoryStream(Long patientId);

    /**
     * Motor anal├¡tico r├ípido para métricas cl├¡nicas (ej: Dashboard por Patolog├¡a).
     */
    long countObservationsByCodeAndThreshold(String loincCode, String thresholdValueRange);
}
