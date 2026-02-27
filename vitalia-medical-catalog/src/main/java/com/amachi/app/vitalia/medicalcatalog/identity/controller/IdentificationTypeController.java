package com.amachi.app.vitalia.medicalcatalog.identity.controller;

import com.amachi.app.core.common.controller.BaseController;
import com.amachi.app.core.common.dto.PageResponseDto;
import com.amachi.app.vitalia.medicalcatalog.identity.dto.IdentificationTypeDto;
import com.amachi.app.vitalia.medicalcatalog.identity.dto.search.IdentificationTypeSearchDto;
import com.amachi.app.vitalia.medicalcatalog.identity.entity.IdentificationType;
import com.amachi.app.vitalia.medicalcatalog.identity.mapper.IdentificationTypeMapper;
import com.amachi.app.vitalia.medicalcatalog.identity.service.impl.IdentificationTypeServiceImpl;
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
@RequiredArgsConstructor
public class IdentificationTypeController extends BaseController implements IdentificationTypeApi {

    private final IdentificationTypeServiceImpl service;
    private final IdentificationTypeMapper mapper;

    @Override
    public ResponseEntity<IdentificationTypeDto> getIdentificationTypeById(@NonNull Long id) {
        return ResponseEntity.ok(mapper.toDto(service.getById(id)));
    }

    @Override
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<IdentificationTypeDto> createIdentificationType(@NonNull IdentificationTypeDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mapper.toDto(service.create(requireNonNull(mapper.toEntity(dto)))));
    }

    @Override
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<IdentificationTypeDto> updateIdentificationType(@NonNull Long id,
            @NonNull IdentificationTypeDto dto) {
        IdentificationType existing = service.getById(id);
        mapper.updateEntityFromDto(dto, existing);
        return ResponseEntity.ok(mapper.toDto(service.update(id, existing)));
    }

    @Override
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Void> deleteIdentificationType(@NonNull Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<IdentificationTypeDto>> getAllIdentificationTypes() {
        return ResponseEntity.ok(service.getAll().stream().map(mapper::toDto).toList());
    }

    @Override
    public ResponseEntity<PageResponseDto<IdentificationTypeDto>> getPaginatedIdentificationTypes(
            @NonNull IdentificationTypeSearchDto searchDto, @NonNull Integer pageIndex,
            @NonNull Integer pageSize) {
        Page<IdentificationType> page = service.getAll(searchDto, pageIndex, pageSize);
        List<IdentificationTypeDto> dtos = page.getContent().stream().map(mapper::toDto).toList();

        PageResponseDto<IdentificationTypeDto> response = PageResponseDto.<IdentificationTypeDto>builder()
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
