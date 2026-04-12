package com.amachi.app.vitalia.medical.infrastructure.controller;

import com.amachi.app.core.common.controller.BaseController;
import com.amachi.app.core.common.dto.PageResponseDto;
import com.amachi.app.vitalia.medical.infrastructure.dto.BedDto;
import com.amachi.app.vitalia.medical.infrastructure.dto.search.BedSearchDto;
import com.amachi.app.vitalia.medical.infrastructure.entity.Bed;
import com.amachi.app.vitalia.medical.infrastructure.mapper.BedMapper;
import com.amachi.app.vitalia.medical.infrastructure.service.BedService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/infrastructure/beds")
@RequiredArgsConstructor
public class BedController extends BaseController implements BedApi {

    private final BedService service;
    private final BedMapper mapper;

    @Override
    public ResponseEntity<BedDto> getBedById(Long id) {
        return ResponseEntity.ok(mapper.toDto(service.getById(id)));
    }

    @Override
    public ResponseEntity<BedDto> createBed(BedDto dto) {
        Bed entity = mapper.toEntity(dto);
        return new ResponseEntity<>(mapper.toDto(service.create(entity)), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<BedDto> updateBed(Long id, BedDto dto) {
        Bed entity = service.getById(id);
        mapper.updateEntityFromDto(dto, entity);
        return ResponseEntity.ok(mapper.toDto(service.update(id, entity)));
    }

    @Override
    public ResponseEntity<Void> deleteBed(Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<PageResponseDto<BedDto>> getPaginatedBeds(BedSearchDto searchDto, Integer pageIndex, Integer pageSize) {
        Page<Bed> page = service.getAll(searchDto, pageIndex, pageSize);
        return ResponseEntity.ok(PageResponseDto.<BedDto>builder()
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
}
