package com.amachi.app.vitalia.medical.history.controller;

import com.amachi.app.core.common.controller.BaseController;
import com.amachi.app.core.common.dto.PageResponseDto;
import com.amachi.app.core.common.utils.AppConstants;
import com.amachi.app.vitalia.medical.history.dto.MedicalHistoryDto;
import com.amachi.app.vitalia.medical.history.dto.search.MedicalHistorySearchDto;
import com.amachi.app.vitalia.medical.history.entity.MedicalHistory;
import com.amachi.app.vitalia.medical.history.mapper.MedicalHistoryMapper;
import com.amachi.app.vitalia.medical.history.service.MedicalHistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(AppConstants.Url.API_V1 + "/medical-histories")
public class MedicalHistoryController extends BaseController implements MedicalHistoryApi {

    private final MedicalHistoryService service;
    private final MedicalHistoryMapper mapper;

    public MedicalHistoryController(MedicalHistoryService service, MedicalHistoryMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @Override
    public ResponseEntity<MedicalHistoryDto> getMedicalHistoryById(Long id) {
        return ResponseEntity.ok(mapper.toDto(service.getById(id)));
    }

    @Override
    public ResponseEntity<MedicalHistoryDto> createMedicalHistory(MedicalHistoryDto dto) {
        MedicalHistory entity = mapper.toEntity(dto);
        MedicalHistory saved = service.create(entity);
        return ResponseEntity.created(URI.create(AppConstants.Url.API_V1 + "/medical-histories/" + saved.getId()))
                .body(mapper.toDto(saved));
    }

    @Override
    public ResponseEntity<MedicalHistoryDto> updateMedicalHistory(Long id, MedicalHistoryDto dto) {
        MedicalHistory entity = mapper.toEntity(dto);
        return ResponseEntity.ok(mapper.toDto(service.update(id, entity)));
    }

    @Override
    public ResponseEntity<Void> deleteMedicalHistory(Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<MedicalHistoryDto>> getAllMedicalHistories() {
        return ResponseEntity.ok(mapper.toDtoList(service.getAll()));
    }

    @Override
    public ResponseEntity<PageResponseDto<MedicalHistoryDto>> getPaginatedMedicalHistories(MedicalHistorySearchDto searchDto, Integer pageIndex, Integer pageSize) {
        Page<MedicalHistory> page = service.getAll(searchDto, pageIndex, pageSize);
        return ResponseEntity.ok(PageResponseDto.<MedicalHistoryDto>builder()
                .content(mapper.toDtoList(page.getContent()))
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .pageIndex(page.getNumber())
                .pageSize(page.getSize())
                .build());
    }
}
