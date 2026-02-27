package com.amachi.app.vitalia.medicalcatalog.vaccine.controller;

import com.amachi.app.core.common.controller.BaseController;
import com.amachi.app.core.common.dto.PageResponseDto;
import com.amachi.app.vitalia.medicalcatalog.vaccine.dto.VaccineDto;
import com.amachi.app.vitalia.medicalcatalog.vaccine.dto.search.VaccineSearchDto;
import com.amachi.app.vitalia.medicalcatalog.vaccine.entity.Vaccine;
import com.amachi.app.vitalia.medicalcatalog.vaccine.mapper.VaccineMapper;
import com.amachi.app.vitalia.medicalcatalog.vaccine.service.impl.VaccineServiceImpl;
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
@RequestMapping("/mdm/vaccine")
@RequiredArgsConstructor
public class VaccineController extends BaseController implements VaccineApi {

    private final VaccineServiceImpl service;
    private final VaccineMapper mapper;

    @Override
    public ResponseEntity<VaccineDto> getVaccineById(@NonNull Long id) {
        return ResponseEntity.ok(mapper.toDto(service.getById(id)));
    }

    @Override
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<VaccineDto> createVaccine(@NonNull VaccineDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mapper.toDto(service.create(requireNonNull(mapper.toEntity(dto)))));
    }

    @Override
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<VaccineDto> updateVaccine(@NonNull Long id, @NonNull VaccineDto dto) {
        Vaccine existing = service.getById(id);
        mapper.updateEntityFromDto(dto, existing);
        return ResponseEntity.ok(mapper.toDto(service.update(id, existing)));
    }

    @Override
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Void> deleteVaccine(@NonNull Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<VaccineDto>> getAllVaccines() {
        return ResponseEntity.ok(service.getAll().stream().map(mapper::toDto).toList());
    }

    @Override
    public ResponseEntity<PageResponseDto<VaccineDto>> getPaginatedVaccines(@NonNull VaccineSearchDto searchDto,
            @NonNull Integer pageIndex, @NonNull Integer pageSize) {
        Page<Vaccine> page = service.getAll(searchDto, pageIndex, pageSize);
        List<VaccineDto> dtos = page.getContent().stream().map(mapper::toDto).toList();

        return ResponseEntity.ok(PageResponseDto.<VaccineDto>builder()
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
