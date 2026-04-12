package com.amachi.app.vitalia.medical.history.controller;

import com.amachi.app.core.common.controller.BaseController;
import com.amachi.app.core.common.dto.PageResponseDto;
import com.amachi.app.core.common.utils.AppConstants;
import com.amachi.app.vitalia.medical.history.dto.ConditionDto;
import com.amachi.app.vitalia.medical.history.dto.EncounterDto;
import com.amachi.app.vitalia.medical.history.dto.ObservationDto;
import com.amachi.app.vitalia.medical.history.dto.request.ConditionRequest;
import com.amachi.app.vitalia.medical.history.dto.request.ObservationRequest;
import com.amachi.app.vitalia.medical.history.dto.request.StartEncounterRequest;
import com.amachi.app.vitalia.medical.history.dto.search.EncounterSearchDto;
import com.amachi.app.vitalia.medical.history.entity.Encounter;
import com.amachi.app.vitalia.medical.history.entity.MedicationRequest;
import com.amachi.app.vitalia.medical.history.mapper.ConditionMapper;
import com.amachi.app.vitalia.medical.history.mapper.EncounterMapper;
import com.amachi.app.vitalia.medical.history.mapper.ObservationMapper;
import com.amachi.app.vitalia.medical.history.service.EncounterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(AppConstants.Url.API_V1 + "/encounters")
public class EncounterController extends BaseController implements EncounterApi {

    private final EncounterService service;
    private final EncounterMapper mapper;
    private final ConditionMapper conditionMapper;
    private final ObservationMapper observationMapper;

    public EncounterController(EncounterService service, EncounterMapper mapper, 
                               ConditionMapper conditionMapper, ObservationMapper observationMapper) {
        this.service = service;
        this.mapper = mapper;
        this.conditionMapper = conditionMapper;
        this.observationMapper = observationMapper;
    }

    @Override
    public ResponseEntity<EncounterDto> getEncounterById(Long id) {
        return ResponseEntity.ok(mapper.toDto(service.getById(id)));
    }

    @Override
    public ResponseEntity<EncounterDto> createEncounter(EncounterDto dto) {
        Encounter entity = mapper.toEntity(dto);
        Encounter saved = service.create(entity);
        return ResponseEntity.created(URI.create(AppConstants.Url.API_V1 + "/encounters/" + saved.getId()))
                .body(mapper.toDto(saved));
    }

    @Override
    public ResponseEntity<EncounterDto> updateEncounter(Long id, EncounterDto dto) {
        Encounter entity = mapper.toEntity(dto);
        return ResponseEntity.ok(mapper.toDto(service.update(id, entity)));
    }

    @Override
    public ResponseEntity<Void> deleteEncounter(Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<EncounterDto>> getAllEncounters() {
        return ResponseEntity.ok(mapper.toDtoList(service.getAll()));
    }

    @Override
    public ResponseEntity<PageResponseDto<EncounterDto>> getPaginatedEncounters(EncounterSearchDto searchDto, Integer pageIndex, Integer pageSize) {
        Page<Encounter> page = service.getAll(searchDto, pageIndex, pageSize);
        return ResponseEntity.ok(PageResponseDto.<EncounterDto>builder()
                .content(mapper.toDtoList(page.getContent()))
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .pageIndex(page.getNumber())
                .pageSize(page.getSize())
                .build());
    }

    @Override
    public ResponseEntity<EncounterDto> startEncounter(StartEncounterRequest request) {
        return ResponseEntity.ok(mapper.toDto(service.startEncounter(request)));
    }

    @Override
    public ResponseEntity<EncounterDto> holdEncounter(Long id, String reason) {
        return ResponseEntity.ok(mapper.toDto(service.holdEncounter(id, reason)));
    }

    @Override
    public ResponseEntity<EncounterDto> resumeEncounter(Long id) {
        return ResponseEntity.ok(mapper.toDto(service.resumeEncounter(id)));
    }

    @Override
    public ResponseEntity<EncounterDto> completeEncounter(Long id) {
        return ResponseEntity.ok(mapper.toDto(service.completeEncounter(id)));
    }

    @Override
    public ResponseEntity<EncounterDto> cancelEncounter(Long id, String reason) {
        return ResponseEntity.ok(mapper.toDto(service.cancelEncounter(id, reason)));
    }

    @Override
    public ResponseEntity<ConditionDto> addCondition(Long id, ConditionRequest request) {
        return ResponseEntity.ok(conditionMapper.toDto(service.addCondition(id, request)));
    }

    @Override
    public ResponseEntity<ObservationDto> addObservation(Long id, ObservationRequest request) {
        return ResponseEntity.ok(observationMapper.toDto(service.addObservation(id, request)));
    }

    @Override
    public ResponseEntity<Void> prescribeMedication(Long id, MedicationRequest request) {
        service.prescribeMedication(id, request);
        return ResponseEntity.noContent().build();
    }
}
