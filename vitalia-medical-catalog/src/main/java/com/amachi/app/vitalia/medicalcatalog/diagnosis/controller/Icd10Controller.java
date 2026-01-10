package com.amachi.app.vitalia.medicalcatalog.diagnosis.controller;

import com.amachi.app.core.common.controller.BaseController;
import com.amachi.app.core.common.dto.PageResponseDto;
import com.amachi.app.vitalia.medicalcatalog.diagnosis.dto.Icd10Dto;
import com.amachi.app.vitalia.medicalcatalog.diagnosis.dto.search.Icd10SearchDto;
import com.amachi.app.vitalia.medicalcatalog.diagnosis.entity.Icd10;
import com.amachi.app.vitalia.medicalcatalog.diagnosis.mapper.Icd10Mapper;
import com.amachi.app.vitalia.medicalcatalog.diagnosis.service.impl.Icd10ServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "ICD-10 Diagnosis", description = "Gestión del catálogo de diagnósticos CIE-10 (MDM)")
@RestController
@RequestMapping("/mdm/diagnosis")
@RequiredArgsConstructor
public class Icd10Controller extends BaseController implements Icd10Api {

    private final Icd10ServiceImpl service;
    private final Icd10Mapper mapper;

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<Icd10Dto> getIcd10ById(@PathVariable @NonNull Long id) {
        return ResponseEntity.ok(mapper.toDto(service.getById(id)));
    }

    @Override
    @PostMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Icd10Dto> createIcd10(@RequestBody @NonNull Icd10Dto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDto(service.create(mapper.toEntity(dto))));
    }

    @Override
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Icd10Dto> updateIcd10(@PathVariable @NonNull Long id, @RequestBody @NonNull Icd10Dto dto) {
        Icd10 existing = service.getById(id);
        mapper.updateEntityFromDto(dto, existing);
        return ResponseEntity.ok(mapper.toDto(service.update(id, existing)));
    }

    @Override
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Void> deleteIcd10(@PathVariable @NonNull Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    @GetMapping("/all")
    public ResponseEntity<List<Icd10Dto>> getAllIcd10() {
        return ResponseEntity.ok(service.getAll().stream().map(mapper::toDto).toList());
    }

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PageResponseDto<Icd10Dto>> getPaginatedIcd10(
            @ModelAttribute @NonNull Icd10SearchDto searchDto,
            @RequestParam @NonNull Integer pageIndex, @RequestParam @NonNull Integer pageSize) {
        Page<Icd10> page = service.getAll(searchDto, pageIndex, pageSize);
        List<Icd10Dto> dtos = page.getContent().stream().map(mapper::toDto).toList();

        PageResponseDto<Icd10Dto> response = PageResponseDto.<Icd10Dto>builder()
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
