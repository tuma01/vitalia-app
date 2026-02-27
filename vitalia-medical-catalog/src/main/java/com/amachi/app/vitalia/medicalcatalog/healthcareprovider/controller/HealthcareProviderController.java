package com.amachi.app.vitalia.medicalcatalog.healthcareprovider.controller;

import com.amachi.app.core.common.controller.BaseController;
import com.amachi.app.core.common.dto.PageResponseDto;
import com.amachi.app.vitalia.medicalcatalog.healthcareprovider.dto.HealthcareProviderDto;
import com.amachi.app.vitalia.medicalcatalog.healthcareprovider.dto.search.HealthcareProviderSearchDto;
import com.amachi.app.vitalia.medicalcatalog.healthcareprovider.entity.HealthcareProvider;
import com.amachi.app.vitalia.medicalcatalog.healthcareprovider.mapper.HealthcareProviderMapper;
import com.amachi.app.vitalia.medicalcatalog.healthcareprovider.service.impl.HealthcareProviderServiceImpl;
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
@RequestMapping("/mdm/healthcare-provider")
@RequiredArgsConstructor
public class HealthcareProviderController extends BaseController implements HealthcareProviderApi {

    private final HealthcareProviderServiceImpl service;
    private final HealthcareProviderMapper mapper;

    @Override
    public ResponseEntity<HealthcareProviderDto> getProviderById(@NonNull Long id) {
        return ResponseEntity.ok(mapper.toDto(service.getById(id)));
    }

    @Override
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<HealthcareProviderDto> createProvider(@NonNull HealthcareProviderDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mapper.toDto(service.create(requireNonNull(mapper.toEntity(dto)))));
    }

    @Override
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<HealthcareProviderDto> updateProvider(@NonNull Long id,
            @NonNull HealthcareProviderDto dto) {
        HealthcareProvider existing = service.getById(id);
        mapper.updateEntityFromDto(dto, existing);
        return ResponseEntity.ok(mapper.toDto(service.update(id, existing)));
    }

    @Override
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Void> deleteProvider(@NonNull Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<HealthcareProviderDto>> getAllProviders() {
        return ResponseEntity.ok(service.getAll().stream().map(mapper::toDto).toList());
    }

    @Override
    public ResponseEntity<PageResponseDto<HealthcareProviderDto>> getPaginatedProviders(
            @NonNull HealthcareProviderSearchDto searchDto, @NonNull Integer pageIndex,
            @NonNull Integer pageSize) {
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
