package com.amachi.app.vitalia.departamentothospital.controller;

import com.amachi.app.vitalia.common.controller.BaseController;
import com.amachi.app.vitalia.common.dto.PageResponseDto;
import com.amachi.app.vitalia.departamentothospital.dto.DepartamentoHospitalDto;
import com.amachi.app.vitalia.departamentothospital.dto.search.DepartamentoHospitalSearchDto;
import com.amachi.app.vitalia.departamentothospital.entity.DepartamentoHospital;
import com.amachi.app.vitalia.departamentothospital.mapper.DepartamentoHospitalMapper;
import com.amachi.app.vitalia.departamentothospital.service.impl.DepartamentoHospitalServiceImpl;
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
@AllArgsConstructor
@Slf4j
@RequestMapping("/departamentohospitales")
public class DepartamentoHospitalController extends BaseController implements DepartamentoHospitalApi {

    private DepartamentoHospitalServiceImpl service;
    private DepartamentoHospitalMapper mapper;

    @Override
    public ResponseEntity<DepartamentoHospitalDto> getDepartamentoHospitalById(Long id) {
        Optional<DepartamentoHospital> entity = service.getById(id);
        return entity.map(value -> ResponseEntity.ok(mapper.toDto(value)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<DepartamentoHospitalDto> createDepartamentoHospital(DepartamentoHospitalDto dto) {
        DepartamentoHospital entity = mapper.toEntity(dto);
        DepartamentoHospital savedEntity = service.create(entity);
        return new ResponseEntity<>(mapper.toDto(savedEntity), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<DepartamentoHospitalDto> updateDepartamentoHospital(Long id, DepartamentoHospitalDto dto) {
        DepartamentoHospital entity = mapper.toEntity(dto);
        DepartamentoHospital updatedEntity = service.update(id, entity);

        return updatedEntity != null ? ResponseEntity.ok(mapper.toDto(updatedEntity))
                : ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<Void> deleteDepartamentoHospital(Long id) {
        if (id == null) {
            return ResponseEntity.badRequest().build(); // Bad Request si el id es nulo
        }
        boolean deleted = service.delete(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();

    }

    @Override
    public ResponseEntity<List<DepartamentoHospitalDto>> getAllDepartamentoHospital() {
        List<DepartamentoHospital> entities = service.getAll();
        List<DepartamentoHospitalDto> dtos = entities.stream()
                .map(entity -> mapper.toDto(entity)).toList();
        return ResponseEntity.ok(dtos);
    }

    @Override
    public ResponseEntity<PageResponseDto<DepartamentoHospitalDto>> getAllPaginatedDepartamentoHospital(DepartamentoHospitalSearchDto searchDto, Integer pageIndex, Integer pageSize) {
        Page<DepartamentoHospital> page = service.getAll(searchDto, pageIndex, pageSize);
        List<DepartamentoHospitalDto> dtos = page.getContent()
                .stream()
                .map(departamentoHospital -> mapper.toDto(departamentoHospital)).toList();

        PageResponseDto<DepartamentoHospitalDto> response = PageResponseDto.<DepartamentoHospitalDto>builder()
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
