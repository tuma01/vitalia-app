package com.amachi.app.vitalia.municipio.controller;

import com.amachi.app.vitalia.common.controller.BaseController;
import com.amachi.app.vitalia.common.dto.PageResponseDto;
import com.amachi.app.vitalia.municipio.dto.MunicipioDto;
import com.amachi.app.vitalia.municipio.dto.search.MunicipioSearchDto;
import com.amachi.app.vitalia.municipio.entity.Municipio;
import com.amachi.app.vitalia.municipio.mapper.MunicipioMapper;
import com.amachi.app.vitalia.municipio.service.impl.MunicipioServiceImpl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/municipios")
@AllArgsConstructor
@Slf4j
public class MunicipioController extends BaseController implements MunicipioApi {

    private MunicipioServiceImpl service;
    private MunicipioMapper mapper;

    @Override
    public ResponseEntity<MunicipioDto> getMunicipioById(Long id) {
        Optional<Municipio> entity = service.getById(id);
        return entity.map(value -> ResponseEntity.ok(mapper.toDto(value)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<MunicipioDto> createMunicipio(MunicipioDto dto) {
        Municipio entity = mapper.toEntity(dto);
        Municipio savedEntity = service.create(entity);
        return new ResponseEntity<>(mapper.toDto(savedEntity), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<MunicipioDto> updateMunicipio(Long id, MunicipioDto dto) {
        Municipio entity = mapper.toEntity(dto);
        Municipio updatedEntity = service.update(id, entity);

        return updatedEntity != null ? ResponseEntity.ok(mapper.toDto(updatedEntity))
                : ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<Void> deleteMunicipio(Long id) {
        if (id == null) {
            return ResponseEntity.badRequest().build(); // Bad Request si el id es nulo
        }
        boolean deleted = service.delete(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();

    }

    @Override
    public ResponseEntity<List<MunicipioDto>> getAllMunicipios() {
        List<Municipio> entities = service.getAll();
        List<MunicipioDto> dtos = entities.stream()
                .map(entity -> mapper.toDto(entity)).toList();
        return ResponseEntity.ok(dtos);
    }

    @Override
    public ResponseEntity<PageResponseDto<MunicipioDto>> getPaginatedMunicipios(MunicipioSearchDto searchDto, Integer pageIndex, Integer pageSize) {
        Page<Municipio> page = service.getAll(searchDto, pageIndex, pageSize);
        List<MunicipioDto> dtos = page.getContent()
                .stream()
                .map(municipio -> mapper.toDto(municipio)).toList();

        PageResponseDto<MunicipioDto> response = PageResponseDto.<MunicipioDto>builder()
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