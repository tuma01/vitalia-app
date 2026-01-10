package com.amachi.app.vitalia.medicalcatalog.allergy.controller;

import com.amachi.app.core.common.controller.BaseController;
import com.amachi.app.vitalia.medicalcatalog.allergy.dto.AllergyDto;
import com.amachi.app.vitalia.medicalcatalog.allergy.mapper.AllergyMapper;
import com.amachi.app.vitalia.medicalcatalog.allergy.service.impl.AllergyServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "Allergy", description = "MDM para catálogo de alergias")
@RestController
@RequestMapping("/mdm/allergy")
@RequiredArgsConstructor
public class AllergyController extends BaseController {
    private final AllergyServiceImpl service;
    private final AllergyMapper mapper;

    @GetMapping("/{id}")
    public ResponseEntity<AllergyDto> getById(@PathVariable @NonNull Long id) {
        return ResponseEntity.ok(mapper.toDto(service.getById(id)));
    }

    @PostMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<AllergyDto> create(@RequestBody @NonNull AllergyDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDto(service.create(mapper.toEntity(dto))));
    }

    @GetMapping("/all")
    public ResponseEntity<List<AllergyDto>> getAll() {
        return ResponseEntity.ok(service.getAll().stream().map(mapper::toDto).toList());
    }
}
