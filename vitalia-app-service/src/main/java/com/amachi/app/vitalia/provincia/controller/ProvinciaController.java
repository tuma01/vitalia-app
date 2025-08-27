package com.amachi.app.vitalia.provincia.controller;

import com.amachi.app.vitalia.common.controller.BaseController;
import com.amachi.app.vitalia.common.dto.PageResponseDto;
import com.amachi.app.vitalia.provincia.dto.ProvinciaDto;
import com.amachi.app.vitalia.provincia.dto.search.ProvinciaSearchDto;
import com.amachi.app.vitalia.provincia.entity.Provincia;
import com.amachi.app.vitalia.provincia.mapper.ProvinciaMapper;
import com.amachi.app.vitalia.provincia.service.impl.ProvinciaServiceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/provincias")
@RequiredArgsConstructor
@Slf4j
public class ProvinciaController extends BaseController implements ProvinciaApi {

    private final ProvinciaServiceImpl service;
    private final ProvinciaMapper mapper;

    @Override
    public ResponseEntity<ProvinciaDto> getProvinciaById(Long id) {
        Optional<Provincia> entity = service.getById(id);
        return entity.map(value -> ResponseEntity.ok(mapper.toDto(value)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<ProvinciaDto> createProvincia(ProvinciaDto dto) {
        Provincia entity = mapper.toEntity(dto);
        Provincia savedEntity = service.create(entity);
        return new ResponseEntity<>(mapper.toDto(savedEntity), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ProvinciaDto> updateProvincia(Long id, ProvinciaDto dto) {
        Provincia entity = mapper.toEntity(dto);
        Provincia updatedEntity = service.update(id, entity);

        return updatedEntity != null ? ResponseEntity.ok(mapper.toDto(updatedEntity))
                : ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<Void> deleteProvincia(Long id) {
        if (id == null) {
            return ResponseEntity.badRequest().build(); // Bad Request si el id es nulo
        }
        boolean deleted = service.delete(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();

    }

    @Override
    public ResponseEntity<List<ProvinciaDto>> getAllProvincias() {
        List<Provincia> entities = service.getAll();
        List<ProvinciaDto> dtos = entities.stream()
                .map(entity -> mapper.toDto(entity)).toList();
        return ResponseEntity.ok(dtos);
    }

    @Override
    public ResponseEntity<PageResponseDto<ProvinciaDto>> getPaginatedProvincias(ProvinciaSearchDto searchDto, Integer pageIndex, Integer pageSize) {
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

