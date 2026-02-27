package com.amachi.app.vitalia.medicalcatalog.allergy.controller;

import com.amachi.app.core.common.controller.BaseController;
import com.amachi.app.core.common.dto.PageResponseDto;
import com.amachi.app.vitalia.medicalcatalog.allergy.dto.AllergyDto;
import com.amachi.app.vitalia.medicalcatalog.allergy.dto.search.AllergySearchDto;
import com.amachi.app.vitalia.medicalcatalog.allergy.entity.Allergy;
import com.amachi.app.vitalia.medicalcatalog.allergy.mapper.AllergyMapper;
import com.amachi.app.vitalia.medicalcatalog.allergy.service.impl.AllergyServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import static java.util.Objects.requireNonNull;

@RestController
@RequestMapping("/mdm/allergy")
@RequiredArgsConstructor
public class AllergyController extends BaseController implements AllergyApi {
    private final AllergyServiceImpl service;
    private final AllergyMapper mapper;

    @Override
    public ResponseEntity<AllergyDto> getAllergyById(@NonNull Long id) {
        return ResponseEntity.ok(mapper.toDto(service.getById(id)));
    }

    @Override
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<AllergyDto> createAllergy(@NonNull AllergyDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mapper.toDto(service.create(requireNonNull(mapper.toEntity(dto)))));
    }

    @Override
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<AllergyDto> updateAllergy(@NonNull Long id, @NonNull AllergyDto dto) {
        Allergy existing = service.getById(id);
        mapper.updateEntityFromDto(dto, existing);
        return ResponseEntity.ok(mapper.toDto(service.update(id, existing)));
    }

    @Override
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Void> deleteAllergy(@NonNull Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<AllergyDto>> getAllAllergies() {
        return ResponseEntity.ok(service.getAll().stream().map(mapper::toDto).toList());
    }

    @Override
    public ResponseEntity<PageResponseDto<AllergyDto>> getPaginatedAllergies(
            @NonNull AllergySearchDto searchDto, @NonNull Integer pageIndex, @NonNull Integer pageSize) {
        Page<Allergy> page = service.getAll(searchDto, pageIndex, pageSize);
        List<AllergyDto> dtos = page.getContent().stream().map(mapper::toDto).toList();

        PageResponseDto<AllergyDto> response = PageResponseDto.<AllergyDto>builder()
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
