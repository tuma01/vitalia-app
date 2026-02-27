package com.amachi.app.vitalia.medicalcatalog.medication.controller;

import com.amachi.app.core.common.controller.BaseController;
import com.amachi.app.core.common.dto.PageResponseDto;
import com.amachi.app.vitalia.medicalcatalog.medication.dto.MedicationDto;
import com.amachi.app.vitalia.medicalcatalog.medication.dto.search.MedicationSearchDto;
import com.amachi.app.vitalia.medicalcatalog.medication.entity.Medication;
import com.amachi.app.vitalia.medicalcatalog.medication.mapper.MedicationMapper;
import com.amachi.app.vitalia.medicalcatalog.medication.service.impl.MedicationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.Objects.requireNonNull;

@RestController
@RequestMapping("/mdm/medication")
@RequiredArgsConstructor
public class MedicationController extends BaseController implements MedicationApi {

    private final MedicationServiceImpl service;
    private final MedicationMapper mapper;

    @Override
    public ResponseEntity<MedicationDto> getMedicationById(@NonNull Long id) {
        return ResponseEntity.ok(mapper.toDto(service.getById(id)));
    }

    @Override
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<MedicationDto> createMedication(@NonNull MedicationDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mapper.toDto(service.create(requireNonNull(mapper.toEntity(dto)))));
    }

    @Override
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<MedicationDto> updateMedication(@NonNull Long id, @NonNull MedicationDto dto) {
        Medication existing = service.getById(id);
        mapper.updateEntityFromDto(dto, existing);
        return ResponseEntity.ok(mapper.toDto(service.update(id, existing)));
    }

    @Override
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Void> deleteMedication(@NonNull Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<MedicationDto>> getAllMedications() {
        return ResponseEntity.ok(service.getAll().stream().map(mapper::toDto).toList());
    }

    @Override
    public ResponseEntity<PageResponseDto<MedicationDto>> getPaginatedMedications(
            @NonNull MedicationSearchDto searchDto,
            @NonNull Integer pageIndex, @NonNull Integer pageSize) {
        Page<Medication> page = service.getAll(searchDto, pageIndex, pageSize);
        List<MedicationDto> dtos = page.getContent().stream().map(mapper::toDto).toList();

        return ResponseEntity.ok(PageResponseDto.<MedicationDto>builder()
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
