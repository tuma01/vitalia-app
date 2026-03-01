package com.amachi.app.vitalia.medicalcatalog.procedure.controller;

import com.amachi.app.core.common.controller.BaseController;
import com.amachi.app.core.common.dto.PageResponseDto;
import com.amachi.app.vitalia.medicalcatalog.procedure.dto.MedicalProcedureDto;
import com.amachi.app.vitalia.medicalcatalog.procedure.dto.search.MedicalProcedureSearchDto;
import com.amachi.app.vitalia.medicalcatalog.procedure.entity.MedicalProcedure;
import com.amachi.app.vitalia.medicalcatalog.procedure.mapper.MedicalProcedureMapper;
import com.amachi.app.vitalia.medicalcatalog.procedure.service.impl.MedicalProcedureServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/mdm/procedure")
@AllArgsConstructor
public class MedicalProcedureController extends BaseController implements MedicalProcedureApi {

    MedicalProcedureServiceImpl service;
    MedicalProcedureMapper mapper;

    @Override
    public ResponseEntity<MedicalProcedureDto> getProcedureById(Long id) {
        MedicalProcedure entity = service.getById(id);
        return ResponseEntity.ok(mapper.toDto(entity));
    }

    @Override
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<MedicalProcedureDto> createProcedure(MedicalProcedureDto dto) {
        MedicalProcedure entity = mapper.toEntity(dto);
        MedicalProcedure savedEntity = service.create(entity);
        return new ResponseEntity<>(mapper.toDto(savedEntity), HttpStatus.CREATED);
    }

    @Override
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<MedicalProcedureDto> updateProcedure(Long id, MedicalProcedureDto dto) {
        MedicalProcedure existing = service.getById(id);
        mapper.updateEntityFromDto(dto, existing);
        MedicalProcedure savedEntity = service.update(id, existing);
        return ResponseEntity.ok(mapper.toDto(savedEntity));
    }

    @Override
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Void> deleteProcedure(Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<MedicalProcedureDto>> getAllProcedures() {
        List<MedicalProcedure> entities = service.getAll();
        List<MedicalProcedureDto> dtos = entities.stream().map(mapper::toDto).toList();
        return ResponseEntity.ok(dtos);
    }

    @Override
    public ResponseEntity<PageResponseDto<MedicalProcedureDto>> getPaginatedProcedures(
            MedicalProcedureSearchDto searchDto, Integer pageIndex, Integer pageSize) {
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
