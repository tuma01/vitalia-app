package com.amachi.app.vitalia.medical.infrastructure.controller;

import com.amachi.app.core.common.controller.BaseController;
import com.amachi.app.core.common.dto.PageResponseDto;
import com.amachi.app.vitalia.medical.infrastructure.dto.DepartmentUnitTypeDto;
import com.amachi.app.vitalia.medical.infrastructure.dto.search.DepartmentUnitTypeSearchDto;
import com.amachi.app.vitalia.medical.infrastructure.entity.DepartmentUnitType;
import com.amachi.app.vitalia.medical.infrastructure.mapper.DepartmentUnitTypeMapper;
import com.amachi.app.vitalia.medical.infrastructure.service.DepartmentUnitTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/infrastructure/unit-types")
@RequiredArgsConstructor
public class DepartmentUnitTypeController extends BaseController implements DepartmentUnitTypeApi {

    private final DepartmentUnitTypeService service;
    private final DepartmentUnitTypeMapper mapper;

    @Override
    public ResponseEntity<DepartmentUnitTypeDto> getUnitTypeById(Long id) {
        return ResponseEntity.ok(mapper.toDto(service.getById(id)));
    }

    @Override
    public ResponseEntity<DepartmentUnitTypeDto> createUnitType(DepartmentUnitTypeDto dto) {
        DepartmentUnitType entity = mapper.toEntity(dto);
        return new ResponseEntity<>(mapper.toDto(service.create(entity)), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<DepartmentUnitTypeDto> updateUnitType(Long id, DepartmentUnitTypeDto dto) {
        DepartmentUnitType entity = service.getById(id);
        mapper.updateEntityFromDto(dto, entity);
        return ResponseEntity.ok(mapper.toDto(service.update(id, entity)));
    }

    @Override
    public ResponseEntity<Void> deleteUnitType(Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<DepartmentUnitTypeDto>> getAllUnitTypes() {
        return ResponseEntity.ok(mapper.toDTOs(service.getAll()));
    }

    @Override
    public ResponseEntity<PageResponseDto<DepartmentUnitTypeDto>> getPaginatedUnitTypes(DepartmentUnitTypeSearchDto searchDto, Integer pageIndex, Integer pageSize) {
        Page<DepartmentUnitType> page = service.getAll(searchDto, pageIndex, pageSize);
        return ResponseEntity.ok(PageResponseDto.<DepartmentUnitTypeDto>builder()
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
