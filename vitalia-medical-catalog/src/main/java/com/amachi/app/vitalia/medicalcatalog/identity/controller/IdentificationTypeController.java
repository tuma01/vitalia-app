package com.amachi.app.vitalia.medicalcatalog.identity.controller;

import com.amachi.app.core.common.controller.BaseController;
import com.amachi.app.core.common.dto.PageResponseDto;
import com.amachi.app.vitalia.medicalcatalog.identity.dto.IdentificationTypeDto;
import com.amachi.app.vitalia.medicalcatalog.identity.dto.search.IdentificationTypeSearchDto;
import com.amachi.app.vitalia.medicalcatalog.identity.mapper.IdentificationTypeMapper;
import com.amachi.app.vitalia.medicalcatalog.identity.service.impl.IdentificationTypeServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "Identification Type", description = "MDM para tipos de identificación")
@RestController
@RequestMapping("/mdm/identification-type")
@RequiredArgsConstructor
public class IdentificationTypeController extends BaseController {
    private final IdentificationTypeServiceImpl service;
    private final IdentificationTypeMapper mapper;

    @GetMapping("/{id}") public ResponseEntity<IdentificationTypeDto> getById(@PathVariable Long id) { return ResponseEntity.ok(mapper.toDto(service.getById(id))); }
    @PostMapping @PreAuthorize("hasRole('SUPER_ADMIN')") public ResponseEntity<IdentificationTypeDto> create(@RequestBody IdentificationTypeDto dto) { return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDto(service.create(mapper.toEntity(dto)))); }
    @DeleteMapping("/{id}") @PreAuthorize("hasRole('SUPER_ADMIN')") public ResponseEntity<Void> delete(@PathVariable Long id) { service.delete(id); return ResponseEntity.noContent().build(); }
    @GetMapping("/all") public ResponseEntity<List<IdentificationTypeDto>> getAll() { return ResponseEntity.ok(service.getAll().stream().map(mapper::toDto).toList()); }
}
