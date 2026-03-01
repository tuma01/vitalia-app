package com.amachi.app.vitalia.medicalcatalog.identity.controller;

import com.amachi.app.core.common.controller.BaseController;
import com.amachi.app.core.common.dto.PageResponseDto;
import com.amachi.app.vitalia.medicalcatalog.identity.dto.IdentificationTypeDto;
import com.amachi.app.vitalia.medicalcatalog.identity.dto.search.IdentificationTypeSearchDto;
import com.amachi.app.vitalia.medicalcatalog.identity.entity.IdentificationType;
import com.amachi.app.vitalia.medicalcatalog.identity.mapper.IdentificationTypeMapper;
import com.amachi.app.vitalia.medicalcatalog.identity.service.impl.IdentificationTypeServiceImpl;
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
@RequestMapping("/mdm/identification-type")
@AllArgsConstructor
public class IdentificationTypeController extends BaseController implements IdentificationTypeApi {

    IdentificationTypeServiceImpl service;
    IdentificationTypeMapper mapper;

    @Override
    public ResponseEntity<IdentificationTypeDto> getIdentificationTypeById(Long id) {
        IdentificationType entity = service.getById(id);
        return ResponseEntity.ok(mapper.toDto(entity));
    }

    @Override
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<IdentificationTypeDto> createIdentificationType(IdentificationTypeDto dto) {
        IdentificationType entity = mapper.toEntity(dto);
        IdentificationType savedEntity = service.create(entity);
        return new ResponseEntity<>(mapper.toDto(savedEntity), HttpStatus.CREATED);
    }

    @Override
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<IdentificationTypeDto> updateIdentificationType(Long id, IdentificationTypeDto dto) {
        IdentificationType existing = service.getById(id);
        mapper.updateEntityFromDto(dto, existing);
        IdentificationType savedEntity = service.update(id, existing);
        return ResponseEntity.ok(mapper.toDto(savedEntity));
    }

    @Override
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Void> deleteIdentificationType(Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<IdentificationTypeDto>> getAllIdentificationTypes() {
        List<IdentificationType> entities = service.getAll();
        List<IdentificationTypeDto> dtos = entities.stream().map(mapper::toDto).toList();
        return ResponseEntity.ok(dtos);
    }

    @Override
    public ResponseEntity<PageResponseDto<IdentificationTypeDto>> getPaginatedIdentificationTypes(
            IdentificationTypeSearchDto searchDto, Integer pageIndex, Integer pageSize) {
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
