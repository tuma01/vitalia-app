package com.amachi.app.vitalia.medicalcatalog.healthcareprovider.controller;

import com.amachi.app.core.common.controller.BaseController;
import com.amachi.app.core.common.dto.PageResponseDto;
import com.amachi.app.vitalia.medicalcatalog.healthcareprovider.dto.HealthcareProviderDto;
import com.amachi.app.vitalia.medicalcatalog.healthcareprovider.dto.search.HealthcareProviderSearchDto;
import com.amachi.app.vitalia.medicalcatalog.healthcareprovider.entity.HealthcareProvider;
import com.amachi.app.vitalia.medicalcatalog.healthcareprovider.mapper.HealthcareProviderMapper;
import com.amachi.app.vitalia.medicalcatalog.healthcareprovider.service.impl.HealthcareProviderServiceImpl;
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

@Tag(name = "Healthcare Provider", description = "Gestión del catálogo de aseguradoras y pagadores de salud (MDM)")
@RestController
@RequestMapping("/mdm/healthcare-provider")
@RequiredArgsConstructor
public class HealthcareProviderController extends BaseController implements HealthcareProviderApi {

    private final HealthcareProviderServiceImpl service;
    private final HealthcareProviderMapper mapper;

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<HealthcareProviderDto> getProviderById(@PathVariable @NonNull Long id) {
        return ResponseEntity.ok(mapper.toDto(service.getById(id)));
    }

    @Override
    @PostMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<HealthcareProviderDto> createProvider(@RequestBody @NonNull HealthcareProviderDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDto(service.create(mapper.toEntity(dto))));
    }

    @Override
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<HealthcareProviderDto> updateProvider(@PathVariable @NonNull Long id,
            @RequestBody @NonNull HealthcareProviderDto dto) {
        HealthcareProvider existing = service.getById(id);
        mapper.updateEntityFromDto(dto, existing);
        return ResponseEntity.ok(mapper.toDto(service.update(id, existing)));
    }

    @Override
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Void> deleteProvider(@PathVariable @NonNull Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    @GetMapping("/all")
    public ResponseEntity<List<HealthcareProviderDto>> getAllProviders() {
        return ResponseEntity.ok(service.getAll().stream().map(mapper::toDto).toList());
    }

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PageResponseDto<HealthcareProviderDto>> getPaginatedProviders(
            @ModelAttribute @NonNull HealthcareProviderSearchDto searchDto, @RequestParam @NonNull Integer pageIndex,
            @RequestParam @NonNull Integer pageSize) {
        Page<HealthcareProvider> page = service.getAll(searchDto, pageIndex, pageSize);
        List<HealthcareProviderDto> dtos = page.getContent().stream().map(mapper::toDto).toList();

        PageResponseDto<HealthcareProviderDto> response = PageResponseDto.<HealthcareProviderDto>builder()
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
