package com.amachi.app.vitalia.medicalcatalog.demographic.controller;

import com.amachi.app.core.common.controller.BaseController;
import com.amachi.app.core.common.dto.PageResponseDto;
import com.amachi.app.vitalia.medicalcatalog.demographic.dto.GenderDto;
import com.amachi.app.vitalia.medicalcatalog.demographic.dto.search.GenderSearchDto;
import com.amachi.app.vitalia.medicalcatalog.demographic.entity.Gender;
import com.amachi.app.vitalia.medicalcatalog.demographic.mapper.GenderMapper;
import com.amachi.app.vitalia.medicalcatalog.demographic.service.impl.GenderServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Gender", description = "Gestión del catálogo maestro de géneros (MDM)")
@RestController
@RequestMapping("/mdm/demographic/gender")
@RequiredArgsConstructor
public class GenderController extends BaseController {

    private final GenderServiceImpl service;
    private final GenderMapper mapper;

    @GetMapping("/{id}")
    public ResponseEntity<GenderDto> getById(@PathVariable @NonNull Long id) {
        return ResponseEntity.ok(mapper.toDto(service.getById(id)));
    }

    @PostMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<GenderDto> create(@RequestBody @NonNull GenderDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDto(service.create(mapper.toEntity(dto))));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<GenderDto> update(@PathVariable @NonNull Long id, @RequestBody @NonNull GenderDto dto) {
        Gender existing = service.getById(id);
        mapper.updateEntity(dto, existing);
        return ResponseEntity.ok(mapper.toDto(service.update(id, existing)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable @NonNull Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<GenderDto>> getAll() {
        return ResponseEntity.ok(service.getAll().stream().map(mapper::toDto).toList());
    }

    @GetMapping
    public ResponseEntity<PageResponseDto<GenderDto>> getPaginated(@NonNull GenderSearchDto searchDto,
            @RequestParam(defaultValue = "0") @NonNull Integer pageIndex,
            @RequestParam(defaultValue = "10") @NonNull Integer pageSize) {
        Page<Gender> page = service.getAll(searchDto, pageIndex, pageSize);
        return ResponseEntity.ok(PageResponseDto.<GenderDto>builder()
                .content(page.getContent().stream().map(mapper::toDto).toList())
                .totalElements(page.getTotalElements())
                .pageIndex(page.getNumber())
                .pageSize(page.getSize())
                .totalPages(page.getTotalPages())
                .build());
    }
}
