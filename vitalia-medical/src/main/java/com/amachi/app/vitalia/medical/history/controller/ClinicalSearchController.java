package com.amachi.app.vitalia.medical.history.controller;

import com.amachi.app.core.common.controller.BaseController;
import com.amachi.app.core.common.dto.PageResponseDto;
import com.amachi.app.core.common.utils.AppConstants;
import com.amachi.app.vitalia.medical.history.dto.ConditionDto;
import com.amachi.app.vitalia.medical.history.dto.EncounterDto;
import com.amachi.app.vitalia.medical.history.dto.ObservationDto;
import com.amachi.app.vitalia.medical.history.dto.search.ConditionSearchDto;
import com.amachi.app.vitalia.medical.history.dto.search.EncounterSearchDto;
import com.amachi.app.vitalia.medical.history.dto.search.ObservationSearchDto;
import com.amachi.app.vitalia.medical.history.dto.timeline.ClinicalEventDto;
import com.amachi.app.vitalia.medical.history.entity.Condition;
import com.amachi.app.vitalia.medical.history.entity.Encounter;
import com.amachi.app.vitalia.medical.history.entity.Observation;
import com.amachi.app.vitalia.medical.history.mapper.ConditionMapper;
import com.amachi.app.vitalia.medical.history.mapper.EncounterMapper;
import com.amachi.app.vitalia.medical.history.mapper.ObservationMapper;
import com.amachi.app.vitalia.medical.history.service.ClinicalSearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(AppConstants.Url.API_V1 + "/clinical")
public class ClinicalSearchController extends BaseController implements ClinicalSearchApi {

    private final ClinicalSearchService service;
    private final EncounterMapper encounterMapper;
    private final ConditionMapper conditionMapper;
    private final ObservationMapper observationMapper;

    public ClinicalSearchController(ClinicalSearchService service, EncounterMapper encounterMapper, 
                                    ConditionMapper conditionMapper, ObservationMapper observationMapper) {
        this.service = service;
        this.encounterMapper = encounterMapper;
        this.conditionMapper = conditionMapper;
        this.observationMapper = observationMapper;
    }

    @Override
    public ResponseEntity<List<ClinicalEventDto>> getClinicalHistoryStream(Long id) {
        return ResponseEntity.ok(service.getPatientClinicalHistoryStream(id));
    }

    @Override
    public ResponseEntity<PageResponseDto<EncounterDto>> searchEncounters(EncounterSearchDto criteria, Integer pageIndex, Integer pageSize) {
        Page<Encounter> page = service.searchEncounters(criteria, PageRequest.of(pageIndex, pageSize));
        return ResponseEntity.ok(PageResponseDto.<EncounterDto>builder()
                .content(encounterMapper.toDtoList(page.getContent()))
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .pageIndex(page.getNumber())
                .pageSize(page.getSize())
                .build());
    }

    @Override
    public ResponseEntity<PageResponseDto<ConditionDto>> searchConditions(ConditionSearchDto criteria, Integer pageIndex, Integer pageSize) {
        Page<Condition> page = service.searchConditions(criteria, PageRequest.of(pageIndex, pageSize));
        return ResponseEntity.ok(PageResponseDto.<ConditionDto>builder()
                .content(conditionMapper.toDtoList(page.getContent()))
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .pageIndex(page.getNumber())
                .pageSize(page.getSize())
                .build());
    }

    @Override
    public ResponseEntity<PageResponseDto<ObservationDto>> searchObservations(ObservationSearchDto criteria, Integer pageIndex, Integer pageSize) {
        Page<Observation> page = service.searchObservations(criteria, PageRequest.of(pageIndex, pageSize));
        return ResponseEntity.ok(PageResponseDto.<ObservationDto>builder()
                .content(observationMapper.toDtoList(page.getContent()))
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .pageIndex(page.getNumber())
                .pageSize(page.getSize())
                .build());
    }
}
