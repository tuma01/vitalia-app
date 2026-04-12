package com.amachi.app.vitalia.medical.consultation.controller;

import com.amachi.app.core.common.controller.BaseController;
import com.amachi.app.core.common.dto.PageResponseDto;
import com.amachi.app.vitalia.medical.consultation.dto.ConsultationDto;
import com.amachi.app.vitalia.medical.consultation.dto.search.ConsultationSearchDto;
import com.amachi.app.vitalia.medical.consultation.entity.Consultation;
import com.amachi.app.vitalia.medical.consultation.mapper.ConsultationMapper;
import com.amachi.app.vitalia.medical.consultation.service.ConsultationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Medical Consultation Controller (SaaS Elite Tier).
 */
@RestController
@RequestMapping("/clinical/consultations")
@RequiredArgsConstructor
public class ConsultationController extends BaseController implements ConsultationApi {

    private final ConsultationService service;
    private final ConsultationMapper mapper;

    @Override
    public ResponseEntity<ConsultationDto> getById(Long id) {
        return ResponseEntity.ok(mapper.toDto(service.getById(id)));
    }

    @Override
    public ResponseEntity<ConsultationDto> create(ConsultationDto dto) {
        Consultation entity = mapper.toEntity(dto);
        return new ResponseEntity<>(mapper.toDto(service.create(entity)), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ConsultationDto> update(Long id, ConsultationDto dto) {
        Consultation existing = service.getById(id);
        mapper.updateEntityFromDto(dto, existing);
        return ResponseEntity.ok(mapper.toDto(service.update(id, existing)));
    }

    @Override
    public ResponseEntity<PageResponseDto<ConsultationDto>> search(
            ConsultationSearchDto searchDto, Integer pageIndex, Integer pageSize) {
            
        Page<Consultation> page = service.getAll(searchDto, pageIndex, pageSize);
        return ResponseEntity.ok(PageResponseDto.<ConsultationDto>builder()
                .content(mapper.toDTOs(page.getContent()))
                .totalElements(page.getTotalElements())
                .pageIndex(page.getNumber())
                .pageSize(page.getSize())
                .totalPages(page.getTotalPages())
                .first(page.isFirst())
                .last(page.isLast())
                .empty(page.isEmpty())
                .numberOfElements(page.getNumberOfElements())
                .build());
    }

    @Override
    public ResponseEntity<List<ConsultationDto>> getByHistoryId(Long historyId) {
        return ResponseEntity.ok(mapper.toDTOs(service.getByMedicalHistoryId(historyId)));
    }
}
