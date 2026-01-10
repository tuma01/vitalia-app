package com.amachi.app.core.geography.municipio.controller;

import com.amachi.app.core.common.controller.BaseController;
import com.amachi.app.core.common.dto.PageResponseDto;
import com.amachi.app.core.geography.municipio.dto.MunicipioDto;
import com.amachi.app.core.geography.municipio.dto.search.MunicipioSearchDto;
import com.amachi.app.core.geography.municipio.entity.Municipio;
import com.amachi.app.core.geography.municipio.mapper.MunicipioMapper;
import com.amachi.app.core.geography.municipio.service.impl.MunicipioServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/municipios")
@AllArgsConstructor
@Slf4j
public class MunicipioController extends BaseController implements MunicipioApi {

    private MunicipioServiceImpl service;
    private MunicipioMapper mapper;

    @Override
    public ResponseEntity<MunicipioDto> getMunicipioById(Long id) {
        Municipio entity = service.getById(id);
        return ResponseEntity.ok(mapper.toDto(entity));
    }

    @Override
    public ResponseEntity<MunicipioDto> createMunicipio(MunicipioDto dto) {
        Municipio entity = mapper.toEntity(dto);
        Municipio savedEntity = service.create(entity);
        return new ResponseEntity<>(mapper.toDto(savedEntity), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<MunicipioDto> updateMunicipio(Long id, MunicipioDto dto) {
        Municipio existingEntity = service.getById(id);
        mapper.updateEntityFromDto(dto, existingEntity);
        Municipio updatedEntity = service.update(id, existingEntity);
        return ResponseEntity.ok(mapper.toDto(updatedEntity));
    }

    @Override
    public ResponseEntity<Void> deleteMunicipio(Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<MunicipioDto>> getAllMunicipios() {
        List<Municipio> entities = service.getAll();
        List<MunicipioDto> dtos = entities.stream()
                .map(entity -> mapper.toDto(entity)).toList();
        return ResponseEntity.ok(dtos);
    }

    @Override
    public ResponseEntity<PageResponseDto<MunicipioDto>> getPaginatedMunicipios(MunicipioSearchDto searchDto,
            Integer pageIndex, Integer pageSize) {
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