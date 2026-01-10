package com.amachi.app.vitalia.medicalcatalog.vaccine.controller;

import com.amachi.app.core.common.controller.BaseController;
import com.amachi.app.vitalia.medicalcatalog.vaccine.dto.VaccineDto;
import com.amachi.app.vitalia.medicalcatalog.vaccine.mapper.VaccineMapper;
import com.amachi.app.vitalia.medicalcatalog.vaccine.service.impl.VaccineServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "Vaccine", description = "MDM para catálogo de vacunas")
@RestController
@RequestMapping("/mdm/vaccine")
@RequiredArgsConstructor
public class VaccineController extends BaseController {
    private final VaccineServiceImpl service;
    private final VaccineMapper mapper;

    @GetMapping("/{id}") public ResponseEntity<VaccineDto> getById(@PathVariable Long id) { return ResponseEntity.ok(mapper.toDto(service.getById(id))); }
    @PostMapping @PreAuthorize("hasRole('SUPER_ADMIN')") public ResponseEntity<VaccineDto> create(@RequestBody VaccineDto dto) { return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDto(service.create(mapper.toEntity(dto)))); }
    @GetMapping("/all") public ResponseEntity<List<VaccineDto>> getAll() { return ResponseEntity.ok(service.getAll().stream().map(mapper::toDto).toList()); }
}
