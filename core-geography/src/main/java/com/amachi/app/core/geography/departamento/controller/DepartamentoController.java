package com.amachi.app.core.geography.departamento.controller;

import com.amachi.app.core.common.controller.BaseController;
import com.amachi.app.core.common.dto.PageResponseDto;
import com.amachi.app.core.geography.departamento.dto.DepartamentoDto;
import com.amachi.app.core.geography.departamento.dto.search.DepartamentoSearchDto;
import com.amachi.app.core.geography.departamento.entity.Departamento;
import com.amachi.app.core.geography.departamento.mapper.DepartamentoMapper;
import com.amachi.app.core.geography.departamento.service.impl.DepartamentoServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departamentos")
@RequiredArgsConstructor
@Slf4j
public class DepartamentoController extends BaseController implements DepartamentoApi {

    private final DepartamentoServiceImpl service;
    private final DepartamentoMapper mapper;

    @Override
    public ResponseEntity<DepartamentoDto> getDepartamentoById(@NonNull @PathVariable Long id) {
        Departamento entity = service.getById(id);
        return ResponseEntity.ok(mapper.toDto(entity));
    }

    @Override
    public ResponseEntity<DepartamentoDto> createDepartamento(@Valid @RequestBody @NonNull DepartamentoDto dto) {
        Departamento entity = mapper.toEntity(dto);
        Departamento savedEntity = service.create(entity);
        return new ResponseEntity<>(mapper.toDto(savedEntity), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<DepartamentoDto> updateDepartamento(@NonNull Long id, @Valid @RequestBody @NonNull DepartamentoDto dto) {
        Departamento existingEntity = service.getById(id);
        mapper.updateEntityFromDto(dto, existingEntity);
        Departamento updatedEntity = service.update(id, existingEntity);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @Override
    public ResponseEntity<Void> deleteDepartamento(@NonNull Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<DepartamentoDto>> getAllDepartamentos() {
        List<Departamento> entities = service.getAll();
        List<DepartamentoDto> dtos = entities.stream()
                .map(mapper::toDto).toList();
        return ResponseEntity.ok(dtos);
    }

    @Override
    public ResponseEntity<PageResponseDto<DepartamentoDto>> getPaginatedDepartamentos(
            @NonNull DepartamentoSearchDto departamentoSearchDto, Integer pageIndex, Integer pageSize) {
        Page<Departamento> page = service.getAll(departamentoSearchDto, pageIndex, pageSize);
        List<DepartamentoDto> dtos = page.getContent()
                .stream()
                .map(mapper::toDto)
                .toList();

        PageResponseDto<DepartamentoDto> response = PageResponseDto.<DepartamentoDto>builder()
                .content(dtos)
                .totalElements(page.getTotalElements())
                .pageIndex(page.getNumber())
                .pageSize(page.getSize())
                .totalPages(page.getTotalPages())
                .first(page.isFirst())
                .last(page.isLast())
                .empty(page.isEmpty())
                .numberOfElements(page.getNumberOfElements())
                .build();

        return ResponseEntity.ok(response);
    }
}
