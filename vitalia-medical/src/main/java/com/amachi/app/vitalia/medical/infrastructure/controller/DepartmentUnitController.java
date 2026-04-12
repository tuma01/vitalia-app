package com.amachi.app.vitalia.medical.infrastructure.controller;

import com.amachi.app.core.common.controller.BaseController;
import com.amachi.app.core.common.dto.PageResponseDto;
import com.amachi.app.vitalia.medical.infrastructure.dto.DepartmentUnitDto;
import com.amachi.app.vitalia.medical.infrastructure.dto.search.DepartmentUnitSearchDto;
import com.amachi.app.vitalia.medical.infrastructure.entity.DepartmentUnit;
import com.amachi.app.vitalia.medical.infrastructure.mapper.DepartmentUnitMapper;
import com.amachi.app.vitalia.medical.infrastructure.service.DepartmentUnitService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/infrastructure/units")
@RequiredArgsConstructor
public class DepartmentUnitController extends BaseController implements DepartmentUnitApi {

    private final DepartmentUnitService service;
    private final DepartmentUnitMapper mapper;

    @Override
    public ResponseEntity<DepartmentUnitDto> getUnitById(Long id) {
        return ResponseEntity.ok(mapper.toDto(service.getById(id)));
    }

    @Override
    public ResponseEntity<DepartmentUnitDto> createUnit(DepartmentUnitDto dto) {
        DepartmentUnit entity = mapper.toEntity(dto);
        return new ResponseEntity<>(mapper.toDto(service.create(entity)), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<DepartmentUnitDto> updateUnit(Long id, DepartmentUnitDto dto) {
         DepartmentUnit entity = service.getById(id);
         mapper.updateEntityFromDto(dto, entity);
         return ResponseEntity.ok(mapper.toDto(service.update(id, entity)));
    }

    @Override
    public ResponseEntity<Void> deleteUnit(Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<DepartmentUnitDto>> getAllUnits() {
        return ResponseEntity.ok(mapper.toDTOs(service.getAll()));
    }

    @Override
    public ResponseEntity<PageResponseDto<DepartmentUnitDto>> getPaginatedUnits(DepartmentUnitSearchDto searchDto, Integer pageIndex, Integer pageSize) {
        Page<DepartmentUnit> page = service.getAll(searchDto, pageIndex, pageSize);
        return ResponseEntity.ok(PageResponseDto.<DepartmentUnitDto>builder()
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
