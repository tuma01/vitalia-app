package com.amachi.app.vitalia.medicalcatalog.medication.controller;

import com.amachi.app.core.common.controller.BaseController;
import com.amachi.app.core.common.dto.PageResponseDto;
import com.amachi.app.vitalia.medicalcatalog.medication.dto.MedicationDto;
import com.amachi.app.vitalia.medicalcatalog.medication.dto.search.MedicationSearchDto;
import com.amachi.app.vitalia.medicalcatalog.medication.entity.Medication;
import com.amachi.app.vitalia.medicalcatalog.medication.mapper.MedicationMapper;
import com.amachi.app.vitalia.medicalcatalog.medication.service.impl.MedicationServiceImpl;
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

@Tag(name = "Medication", description = "Gestión del catálogo de medicamentos (Vademécum - MDM)")
@RestController
@RequestMapping("/mdm/medication")
@RequiredArgsConstructor
public class MedicationController extends BaseController implements MedicationApi {

    private final MedicationServiceImpl service;
    private final MedicationMapper mapper;

    @Override
    public ResponseEntity<MedicationDto> getMedicationById(Long id) {
        return ResponseEntity.ok(mapper.toDto(service.getById(id)));
    }

    @Override
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<MedicationDto> createMedication(MedicationDto dto) {
        Medication entity = mapper.toEntity(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDto(service.create(entity)));
    }

    @Override
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<MedicationDto> updateMedication(Long id, MedicationDto dto) {
        Medication existing = service.getById(id);
        mapper.updateEntityFromDto(dto, existing);
        return ResponseEntity.ok(mapper.toDto(service.update(id, existing)));
    }

    @Override
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Void> deleteMedication(Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<MedicationDto>> getAllMedications() {
        return ResponseEntity.ok(service.getAll().stream().map(mapper::toDto).toList());
    }

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PageResponseDto<MedicationDto>> getPaginatedMedications(MedicationSearchDto searchDto, Integer pageIndex, Integer pageSize) {
        Page<Medication> page = service.getAll(searchDto, pageIndex, pageSize);
        List<MedicationDto> dtos = page.getContent().stream().map(mapper::toDto).toList();

        PageResponseDto<MedicationDto> response = PageResponseDto.<MedicationDto>builder()
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
