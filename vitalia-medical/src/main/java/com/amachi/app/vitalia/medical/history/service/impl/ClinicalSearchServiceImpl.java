package com.amachi.app.vitalia.medical.history.service.impl;

import com.amachi.app.vitalia.medical.history.dto.search.ConditionSearchDto;
import com.amachi.app.vitalia.medical.history.dto.search.EncounterSearchDto;
import com.amachi.app.vitalia.medical.history.dto.search.ObservationSearchDto;
import com.amachi.app.vitalia.medical.history.dto.timeline.ClinicalEventDto;
import com.amachi.app.vitalia.medical.history.entity.Condition;
import com.amachi.app.vitalia.medical.history.entity.Encounter;
import com.amachi.app.vitalia.medical.history.entity.Observation;
import com.amachi.app.vitalia.medical.history.repository.ConditionRepository;
import com.amachi.app.vitalia.medical.history.repository.EncounterRepository;
import com.amachi.app.vitalia.medical.history.repository.MedicationRequestRepository;
import com.amachi.app.vitalia.medical.history.repository.ObservationRepository;
import com.amachi.app.vitalia.medical.history.service.ClinicalSearchService;
import com.amachi.app.vitalia.medical.history.specification.ConditionSpecification;
import com.amachi.app.vitalia.medical.history.specification.EncounterSpecification;
import com.amachi.app.vitalia.medical.history.specification.ObservationSpecification;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Motor de Búsqueda Clínica Avanzada (Clinical Analytics Tier).
 * Cruza datos multi-entidad para generar vistas unificadas del paciente.
 */
@Service
@AllArgsConstructor
public class ClinicalSearchServiceImpl implements ClinicalSearchService {

    private final EncounterRepository encounterRepository;
    private final ConditionRepository conditionRepository;
    private final ObservationRepository observationRepository;
    private final MedicationRequestRepository medicationRequestRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<Encounter> searchEncounters(EncounterSearchDto criteria, Pageable pageable) {
        Specification<Encounter> spec = new EncounterSpecification(criteria);
        return encounterRepository.findAll(spec, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Condition> searchConditions(ConditionSearchDto criteria, Pageable pageable) {
        Specification<Condition> spec = new ConditionSpecification(criteria);
        return conditionRepository.findAll(spec, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Observation> searchObservations(ObservationSearchDto criteria, Pageable pageable) {
        Specification<Observation> spec = new ObservationSpecification(criteria);
        return observationRepository.findAll(spec, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClinicalEventDto> getPatientClinicalHistoryStream(Long patientId) {
        List<ClinicalEventDto> events = new ArrayList<>();

        // 1. Mapear Encuentros
        EncounterSearchDto encounterCriteria = new EncounterSearchDto();
        encounterCriteria.setPatientId(patientId);

        encounterRepository.findAll(new EncounterSpecification(encounterCriteria))
                .forEach(e -> {
                    ClinicalEventDto event = new ClinicalEventDto();
                    event.setType("ENCOUNTER");
                    event.setResourceId(e.getId());
                    event.setTitle("Encuentro: " + e.getEncounterType());
                    event.setContent(e.getReason());
                    event.setPractitionerName(e.getDoctor() != null ? e.getDoctor().getFullName() : "N/A");
                    event.setEffectiveDateTime(e.getEncounterDateTime());
                    event.setStatus(e.getStatus() != null ? e.getStatus().name() : "N/A");
                    event.setEncounterId(e.getId());
                    events.add(event);
                });

        // 2. Mapear Diagnósticos (Conditions)
        ConditionSearchDto conditionCriteria = new ConditionSearchDto();
        conditionCriteria.setPatientId(patientId);

        conditionRepository.findAll(new ConditionSpecification(conditionCriteria))
                .forEach(c -> {
                    ClinicalEventDto event = new ClinicalEventDto();
                    event.setType("CONDITION");
                    event.setResourceId(c.getId());
                    event.setTitle("Diagnóstico: " + (c.getIcd10() != null ? c.getIcd10().getCode() : "N/A"));
                    event.setContent(c.getName());
                    event.setPractitionerName(c.getPractitioner() != null ? c.getPractitioner().getFullName() : "N/A");
                    event.setEffectiveDateTime(c.getCreatedDate() != null ? c.getCreatedDate().atOffset(java.time.ZoneOffset.UTC) : null);
                    event.setStatus(c.getClinicalStatus() != null ? c.getClinicalStatus().name() : "N/A");
                    event.setEncounterId(c.getEncounter() != null ? c.getEncounter().getId() : null);
                    events.add(event);
                });

        // 3. Mapear Mediciones (Observations)
        ObservationSearchDto observationCriteria = new ObservationSearchDto();
        observationCriteria.setPatientId(patientId);

        observationRepository.findAll(new ObservationSpecification(observationCriteria))
                .forEach(o -> {
                    ClinicalEventDto event = new ClinicalEventDto();
                    event.setType("OBSERVATION");
                    event.setResourceId(o.getId());
                    event.setTitle("Observación: " + o.getName());
                    event.setContent(o.getValue() + " " + (o.getUnit() != null ? o.getUnit() : ""));
                    event.setPractitionerName(o.getPractitioner() != null ? o.getPractitioner().getFullName() : "N/A");
                    event.setEffectiveDateTime(o.getEffectiveDateTime());
                    event.setStatus(o.getStatus() != null ? o.getStatus().name() : "N/A");
                    event.setEncounterId(o.getEncounter() != null ? o.getEncounter().getId() : null);
                    events.add(event);
                });

        // Ordenar cronológicamente descendente
        return events.stream()
                .filter(ev -> ev.getEffectiveDateTime() != null)
                .sorted(Comparator.comparing(ClinicalEventDto::getEffectiveDateTime).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public long countObservationsByCodeAndThreshold(String loincCode, String thresholdValueRange) {
        return 0;
    }
}
