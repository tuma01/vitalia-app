package com.amachi.app.core.geography.provincia.controller;

import com.amachi.app.core.common.controller.BaseController;
import com.amachi.app.core.common.dto.PageResponseDto;
import com.amachi.app.core.geography.provincia.dto.ProvinciaDto;
import com.amachi.app.core.geography.provincia.dto.search.ProvinciaSearchDto;
import com.amachi.app.core.geography.provincia.entity.Provincia;
import com.amachi.app.core.geography.provincia.mapper.ProvinciaMapper;
import com.amachi.app.core.geography.provincia.service.impl.ProvinciaServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/provincias")
@RequiredArgsConstructor
@Slf4j
public class ProvinciaController extends BaseController implements ProvinciaApi {

    private final ProvinciaServiceImpl service;
    private final ProvinciaMapper mapper;

    @Override
    public ResponseEntity<ProvinciaDto> getProvinciaById(Long id) {
        Provincia entity = service.getById(id);
        return ResponseEntity.ok(mapper.toDto(entity));
    }

    @Override
    public ResponseEntity<ProvinciaDto> createProvincia(ProvinciaDto dto) {
        Provincia entity = mapper.toEntity(dto);
        Provincia savedEntity = service.create(entity);
        return new ResponseEntity<>(mapper.toDto(savedEntity), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ProvinciaDto> updateProvincia(Long id, ProvinciaDto dto) {
        Provincia existingEntity = service.getById(id);
        mapper.updateEntityFromDto(dto, existingEntity);
        Provincia updatedEntity = service.update(id, existingEntity);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @Override
    public ResponseEntity<Void> deleteProvincia(Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<ProvinciaDto>> getAllProvincias() {
        List<Provincia> entities = service.getAll();
        List<ProvinciaDto> dtos = entities.stream()
                .map(entity -> mapper.toDto(entity)).toList();
        return ResponseEntity.ok(dtos);
    }

    @Override
    public ResponseEntity<PageResponseDto<ProvinciaDto>> getPaginatedProvincias(ProvinciaSearchDto searchDto,
            Integer pageIndex, Integer pageSize) {
        Page<Provincia> page = service.getAll(searchDto, pageIndex, pageSize);
        List<ProvinciaDto> dtos = page.getContent()
                .stream()
                .map(provincia -> mapper.toDto(provincia)).toList();

        PageResponseDto<ProvinciaDto> response = PageResponseDto.<ProvinciaDto>builder()
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
