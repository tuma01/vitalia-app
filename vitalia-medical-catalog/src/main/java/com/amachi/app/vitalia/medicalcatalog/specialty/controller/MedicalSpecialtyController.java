package com.amachi.app.vitalia.medicalcatalog.specialty.controller;

import com.amachi.app.core.common.controller.BaseController;
import com.amachi.app.core.common.dto.PageResponseDto;
import com.amachi.app.vitalia.medicalcatalog.specialty.dto.MedicalSpecialtyDto;
import com.amachi.app.vitalia.medicalcatalog.specialty.dto.search.MedicalSpecialtySearchDto;
import com.amachi.app.vitalia.medicalcatalog.specialty.entity.MedicalSpecialty;
import com.amachi.app.vitalia.medicalcatalog.specialty.mapper.MedicalSpecialtyMapper;
import com.amachi.app.vitalia.medicalcatalog.specialty.service.impl.MedicalSpecialtyServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.Objects.requireNonNull;

@RestController
@RequestMapping("/mdm/medical-specialty")
@RequiredArgsConstructor
public class MedicalSpecialtyController extends BaseController implements MedicalSpecialtyApi {

    private final MedicalSpecialtyServiceImpl service;
    private final MedicalSpecialtyMapper mapper;

    @Override
    public ResponseEntity<MedicalSpecialtyDto> getSpecialtyById(@NonNull Long id) {
        return ResponseEntity.ok(mapper.toDto(service.getById(id)));
    }

    @Override
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<MedicalSpecialtyDto> createSpecialty(@NonNull MedicalSpecialtyDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mapper.toDto(service.create(requireNonNull(mapper.toEntity(dto)))));
    }

    @Override
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<MedicalSpecialtyDto> updateSpecialty(@NonNull Long id, @NonNull MedicalSpecialtyDto dto) {
        MedicalSpecialty existing = service.getById(id);
        mapper.updateEntityFromDto(dto, existing);
        return ResponseEntity.ok(mapper.toDto(service.update(id, existing)));
    }

    @Override
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Void> deleteSpecialty(@NonNull Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<MedicalSpecialtyDto>> getAllSpecialties() {
        return ResponseEntity.ok(service.getAll().stream().map(mapper::toDto).toList());
    }

    @Override
    public ResponseEntity<PageResponseDto<MedicalSpecialtyDto>> getPaginatedSpecialties(
            @NonNull MedicalSpecialtySearchDto searchDto,
            @NonNull Integer pageIndex, @NonNull Integer pageSize) {
        Page<MedicalSpecialty> page = service.getAll(searchDto, pageIndex, pageSize);
        List<MedicalSpecialtyDto> dtos = page.getContent().stream().map(mapper::toDto).toList();

        return ResponseEntity.ok(PageResponseDto.<MedicalSpecialtyDto>builder()
                .content(dtos)
                .totalElements(page.getTotalElements())
                .pageIndex(page.getNumber())
                .pageSize(page.getSize())
                .totalPages(page.getTotalPages())
                .first(page.isFirst())
                .last(page.isLast())
                .empty(page.isEmpty())
                .numberOfElements(page.getNumberOfElements())
                .build());
    }
}
