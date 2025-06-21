package com.amachi.app.vitalia.departamento.controller;

import com.amachi.app.vitalia.common.controller.BaseController;
import com.amachi.app.vitalia.common.dto.PageResponseDto;

import com.amachi.app.vitalia.departamento.dto.DepartamentoDto;
import com.amachi.app.vitalia.departamento.dto.search.DepartamentoSearchDto;
import com.amachi.app.vitalia.departamento.entity.Departamento;
import com.amachi.app.vitalia.departamento.mapper.DepartamentoMapper;
import com.amachi.app.vitalia.departamento.service.impl.DepartamentoServiceImpl;
import lombok.AllArgsConstructor;
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
import java.util.Optional;

@RestController
@RequestMapping("/departamentos")
@AllArgsConstructor
@Slf4j
public class DepartamentoController extends BaseController implements DepartamentoApi {
//public class DepartamentoController  implements DepartamentoApi {

    private DepartamentoServiceImpl service;
    private DepartamentoMapper mapper;

    @Override
    @GetMapping(value = ID, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DepartamentoDto> getDepartamentoById(@PathVariable Long id) {
        Optional<Departamento> entity = service.getById(id);
        return entity.map(value -> ResponseEntity.ok(mapper.toDto(value)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<DepartamentoDto> createDepartamento(DepartamentoDto dto) {
        Departamento entity = mapper.toEntity(dto);
        Departamento savedEntity = service.create(entity);
        return new ResponseEntity<>(mapper.toDto(savedEntity), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<DepartamentoDto> updateDepartamento(Long id, DepartamentoDto dto) {
        Departamento entity = mapper.toEntity(dto);
        Departamento updatedEntity = service.update(id, entity);

        return updatedEntity != null ? ResponseEntity.ok(mapper.toDto(updatedEntity))
                : ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<Void> deleteDepartamento(Long id) {
        if (id == null) {
            return ResponseEntity.badRequest().build(); // Bad Request si el id es nulo
        }
        boolean deleted = service.delete(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<List<DepartamentoDto>> getAllDepartamentos() {
        List<Departamento> entities = service.getAll();
        List<DepartamentoDto> dtos =  entities.stream()
                .map(entity -> mapper.toDto(entity)).toList();
        return ResponseEntity.ok(dtos);
    }

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PageResponseDto<DepartamentoDto>> getPaginatedDepartamentos(DepartamentoSearchDto departamentoSearchDto, Integer pageIndex, Integer pageSize) {
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
