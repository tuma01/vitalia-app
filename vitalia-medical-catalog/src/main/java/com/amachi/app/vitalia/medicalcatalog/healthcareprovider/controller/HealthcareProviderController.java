package com.amachi.app.vitalia.medicalcatalog.healthcareprovider.controller;

import com.amachi.app.core.common.controller.BaseController;
import com.amachi.app.core.common.dto.PageResponseDto;
import com.amachi.app.vitalia.medicalcatalog.healthcareprovider.dto.HealthcareProviderDto;
import com.amachi.app.vitalia.medicalcatalog.healthcareprovider.dto.search.HealthcareProviderSearchDto;
import com.amachi.app.vitalia.medicalcatalog.healthcareprovider.entity.HealthcareProvider;
import com.amachi.app.vitalia.medicalcatalog.healthcareprovider.mapper.HealthcareProviderMapper;
import com.amachi.app.vitalia.medicalcatalog.healthcareprovider.service.impl.HealthcareProviderServiceImpl;
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
@RequestMapping("/mdm/healthcare-provider")
@AllArgsConstructor
public class HealthcareProviderController extends BaseController implements HealthcareProviderApi {

    HealthcareProviderServiceImpl service;
    HealthcareProviderMapper mapper;

    @Override
    public ResponseEntity<HealthcareProviderDto> getProviderById(Long id) {
        HealthcareProvider entity = service.getById(id);
        return ResponseEntity.ok(mapper.toDto(entity));
    }

    @Override
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<HealthcareProviderDto> createProvider(HealthcareProviderDto dto) {
        HealthcareProvider entity = mapper.toEntity(dto);
        HealthcareProvider savedEntity = service.create(entity);
        return new ResponseEntity<>(mapper.toDto(savedEntity), HttpStatus.CREATED);
    }

    @Override
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<HealthcareProviderDto> updateProvider(Long id, HealthcareProviderDto dto) {
        HealthcareProvider existing = service.getById(id);
        mapper.updateEntityFromDto(dto, existing);
        HealthcareProvider savedEntity = service.update(id, existing);
        return ResponseEntity.ok(mapper.toDto(savedEntity));
    }

    @Override
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Void> deleteProvider(Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<HealthcareProviderDto>> getAllProviders() {
        List<HealthcareProvider> entities = service.getAll();
        List<HealthcareProviderDto> dtos = entities.stream().map(mapper::toDto).toList();
        return ResponseEntity.ok(dtos);
    }

    @Override
    public ResponseEntity<PageResponseDto<HealthcareProviderDto>> getPaginatedProviders(
            HealthcareProviderSearchDto searchDto, Integer pageIndex, Integer pageSize) {
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
