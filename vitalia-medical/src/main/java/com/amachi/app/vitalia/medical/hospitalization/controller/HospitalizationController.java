package com.amachi.app.vitalia.medical.hospitalization.controller;

import com.amachi.app.core.common.controller.BaseController;
import com.amachi.app.core.common.dto.PageResponseDto;
import com.amachi.app.vitalia.medical.hospitalization.dto.HospitalizationDto;
import com.amachi.app.vitalia.medical.hospitalization.dto.search.HospitalizationSearchDto;
import com.amachi.app.vitalia.medical.hospitalization.entity.Hospitalization;
import com.amachi.app.vitalia.medical.hospitalization.mapper.HospitalizationMapper;
import com.amachi.app.vitalia.medical.hospitalization.service.HospitalizationService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Enterprise Controller for Hospitalization.
 */
@RestController
@RequestMapping("/clinical/hospitalizations")
public class HospitalizationController extends BaseController implements HospitalizationApi {

    private final HospitalizationService service;
    private final HospitalizationMapper mapper;

    public HospitalizationController(HospitalizationService service, HospitalizationMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @Override
    public ResponseEntity<HospitalizationDto> getHospitalizationById(Long id) {
        return ResponseEntity.ok(mapper.toDto(service.getById(id)));
    }

    @Override
    public ResponseEntity<HospitalizationDto> createHospitalization(HospitalizationDto dto) {
        Hospitalization entity = mapper.toEntity(dto);
        return new ResponseEntity<>(mapper.toDto(service.create(entity)), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<HospitalizationDto> updateHospitalization(Long id, HospitalizationDto dto) {
        Hospitalization existing = service.getById(id);
        mapper.updateEntityFromDto(dto, existing);
        return ResponseEntity.ok(mapper.toDto(service.update(id, existing)));
    }

    @Override
    public ResponseEntity<PageResponseDto<HospitalizationDto>> getPaginatedHospitalizations(HospitalizationSearchDto searchDto, Integer pageIndex, Integer pageSize) {
        Page<Hospitalization> page = service.getAll(searchDto, pageIndex, pageSize);
        return ResponseEntity.ok(PageResponseDto.<HospitalizationDto>builder()
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
    public ResponseEntity<HospitalizationDto> dischargePatient(Long id, String dischargeSummary) {
        return ResponseEntity.ok(mapper.toDto(service.dischargePatient(id, dischargeSummary)));
    }
}
