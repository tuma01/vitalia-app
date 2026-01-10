package com.amachi.app.vitalia.medicalcatalog.procedure.controller;

import com.amachi.app.core.common.controller.BaseController;
import com.amachi.app.core.common.dto.PageResponseDto;
import com.amachi.app.vitalia.medicalcatalog.procedure.dto.MedicalProcedureDto;
import com.amachi.app.vitalia.medicalcatalog.procedure.dto.search.MedicalProcedureSearchDto;
import com.amachi.app.vitalia.medicalcatalog.procedure.entity.MedicalProcedure;
import com.amachi.app.vitalia.medicalcatalog.procedure.mapper.MedicalProcedureMapper;
import com.amachi.app.vitalia.medicalcatalog.procedure.service.impl.MedicalProcedureServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Medical Procedure", description = "Gestión del catálogo de procedimientos médicos y laboratorios (MDM)")
@RestController
@RequestMapping("/mdm/procedure")
@RequiredArgsConstructor
public class MedicalProcedureController extends BaseController implements MedicalProcedureApi {

    private final MedicalProcedureServiceImpl service;
    private final MedicalProcedureMapper mapper;

    @Override
    public ResponseEntity<MedicalProcedureDto> getProcedureById(Long id) {
        return ResponseEntity.ok(mapper.toDto(service.getById(id)));
    }

    @Override
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<MedicalProcedureDto> createProcedure(MedicalProcedureDto dto) {
        MedicalProcedure entity = mapper.toEntity(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDto(service.create(entity)));
    }

    @Override
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<MedicalProcedureDto> updateProcedure(Long id, MedicalProcedureDto dto) {
        MedicalProcedure existing = service.getById(id);
        mapper.updateEntityFromDto(dto, existing);
        return ResponseEntity.ok(mapper.toDto(service.update(id, existing)));
    }

    @Override
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Void> deleteProcedure(Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<MedicalProcedureDto>> getAllProcedures() {
        return ResponseEntity.ok(service.getAll().stream().map(mapper::toDto).toList());
    }

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PageResponseDto<MedicalProcedureDto>> getPaginatedProcedures(MedicalProcedureSearchDto searchDto, Integer pageIndex, Integer pageSize) {
        Page<MedicalProcedure> page = service.getAll(searchDto, pageIndex, pageSize);
        List<MedicalProcedureDto> dtos = page.getContent().stream().map(mapper::toDto).toList();

        PageResponseDto<MedicalProcedureDto> response = PageResponseDto.<MedicalProcedureDto>builder()
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
