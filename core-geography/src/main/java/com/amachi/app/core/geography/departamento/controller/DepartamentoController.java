package com.amachi.app.core.geography.departamento.controller;

import com.amachi.app.core.common.controller.BaseController;
import com.amachi.app.core.common.dto.PageResponseDto;
import com.amachi.app.core.geography.departamento.dto.DepartamentoDto;
import com.amachi.app.core.geography.departamento.dto.search.DepartamentoSearchDto;
import com.amachi.app.core.geography.departamento.entity.Departamento;
import com.amachi.app.core.geography.departamento.mapper.DepartamentoMapper;
import com.amachi.app.core.geography.departamento.service.impl.DepartamentoServiceImpl;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/departamentos")
@RequiredArgsConstructor
@Slf4j
public class DepartamentoController extends BaseController implements DepartamentoApi {

    private final DepartamentoServiceImpl service;
    private final DepartamentoMapper mapper;

    @Override
    @GetMapping(value = ID, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DepartamentoDto> getDepartamentoById(@PathVariable Long id) {
        Departamento entity = service.getById(id);
        return ResponseEntity.ok(mapper.toDto(entity));
    }

    @Override
    public ResponseEntity<DepartamentoDto> createDepartamento(DepartamentoDto dto) {
        Departamento entity = mapper.toEntity(dto);
        Departamento savedEntity = service.create(entity);
        return new ResponseEntity<>(mapper.toDto(savedEntity), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<DepartamentoDto> updateDepartamento(Long id, DepartamentoDto dto) {
        Departamento existingEntity = service.getById(id);
        mapper.updateEntityFromDto(dto, existingEntity);
        Departamento updatedEntity = service.update(id, existingEntity);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @Override
    public ResponseEntity<Void> deleteDepartamento(Long id) {
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
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PageResponseDto<DepartamentoDto>> getPaginatedDepartamentos(
            DepartamentoSearchDto departamentoSearchDto, Integer pageIndex, Integer pageSize) {
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
