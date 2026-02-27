package com.amachi.app.vitalia.medicalcatalog.demographic.controller;

import com.amachi.app.core.common.controller.BaseController;
import com.amachi.app.core.common.dto.PageResponseDto;
import com.amachi.app.vitalia.medicalcatalog.demographic.dto.CivilStatusDto;
import com.amachi.app.vitalia.medicalcatalog.demographic.dto.search.CivilStatusSearchDto;
import com.amachi.app.vitalia.medicalcatalog.demographic.entity.CivilStatus;
import com.amachi.app.vitalia.medicalcatalog.demographic.mapper.CivilStatusMapper;
import com.amachi.app.vitalia.medicalcatalog.demographic.service.impl.CivilStatusServiceImpl;
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
@RequestMapping("/mdm/demographic/civil-status")
@RequiredArgsConstructor
public class CivilStatusController extends BaseController implements CivilStatusApi {

    private final CivilStatusServiceImpl service;
    private final CivilStatusMapper mapper;

    @Override
    public ResponseEntity<CivilStatusDto> getCivilStatusById(@NonNull Long id) {
        return ResponseEntity.ok(mapper.toDto(service.getById(id)));
    }

    @Override
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<CivilStatusDto> createCivilStatus(@NonNull CivilStatusDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mapper.toDto(service.create(requireNonNull(mapper.toEntity(dto)))));
    }

    @Override
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<CivilStatusDto> updateCivilStatus(@NonNull Long id, @NonNull CivilStatusDto dto) {
        CivilStatus existing = service.getById(id);
        mapper.updateEntity(dto, existing);
        return ResponseEntity.ok(mapper.toDto(service.update(id, existing)));
    }

    @Override
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Void> deleteCivilStatus(@NonNull Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<CivilStatusDto>> getAllCivilStatuses() {
        return ResponseEntity.ok(service.getAll().stream().map(mapper::toDto).toList());
    }

    @Override
    public ResponseEntity<PageResponseDto<CivilStatusDto>> getPaginatedCivilStatuses(
            @NonNull CivilStatusSearchDto searchDto,
            @NonNull Integer pageIndex, @NonNull Integer pageSize) {
        Page<CivilStatus> page = service.getAll(searchDto, pageIndex, pageSize);

        return ResponseEntity.ok(PageResponseDto.<CivilStatusDto>builder()
                .content(page.getContent().stream().map(mapper::toDto).toList())
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
