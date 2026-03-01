package com.amachi.app.vitalia.medicalcatalog.specialty.controller;

import com.amachi.app.core.common.controller.BaseController;
import com.amachi.app.core.common.dto.PageResponseDto;
import com.amachi.app.vitalia.medicalcatalog.specialty.dto.MedicalSpecialtyDto;
import com.amachi.app.vitalia.medicalcatalog.specialty.dto.search.MedicalSpecialtySearchDto;
import com.amachi.app.vitalia.medicalcatalog.specialty.entity.MedicalSpecialty;
import com.amachi.app.vitalia.medicalcatalog.specialty.mapper.MedicalSpecialtyMapper;
import com.amachi.app.vitalia.medicalcatalog.specialty.service.impl.MedicalSpecialtyServiceImpl;
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
@RequestMapping("/mdm/medical-specialty")
@AllArgsConstructor
public class MedicalSpecialtyController extends BaseController implements MedicalSpecialtyApi {

    MedicalSpecialtyServiceImpl service;
    MedicalSpecialtyMapper mapper;

    @Override
    public ResponseEntity<MedicalSpecialtyDto> getSpecialtyById(Long id) {
        MedicalSpecialty entity = service.getById(id);
        return ResponseEntity.ok(mapper.toDto(entity));
    }

    @Override
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<MedicalSpecialtyDto> createSpecialty(MedicalSpecialtyDto dto) {
        MedicalSpecialty entity = mapper.toEntity(dto);
        MedicalSpecialty savedEntity = service.create(entity);
        return new ResponseEntity<>(mapper.toDto(savedEntity), HttpStatus.CREATED);
    }

    @Override
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<MedicalSpecialtyDto> updateSpecialty(Long id, MedicalSpecialtyDto dto) {
        MedicalSpecialty existing = service.getById(id);
        mapper.updateEntityFromDto(dto, existing);
        MedicalSpecialty savedEntity = service.update(id, existing);
        return ResponseEntity.ok(mapper.toDto(savedEntity));
    }

    @Override
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Void> deleteSpecialty(Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<MedicalSpecialtyDto>> getAllSpecialties() {
        List<MedicalSpecialty> entities = service.getAll();
        List<MedicalSpecialtyDto> dtos = entities.stream().map(mapper::toDto).toList();
        return ResponseEntity.ok(dtos);
    }

    @Override
    public ResponseEntity<PageResponseDto<MedicalSpecialtyDto>> getPaginatedSpecialties(
            MedicalSpecialtySearchDto searchDto, Integer pageIndex, Integer pageSize) {
        Page<MedicalSpecialty> page = service.getAll(searchDto, pageIndex, pageSize);
        List<MedicalSpecialtyDto> dtos = page.getContent().stream().map(mapper::toDto).toList();

        PageResponseDto<MedicalSpecialtyDto> response = PageResponseDto.<MedicalSpecialtyDto>builder()
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
