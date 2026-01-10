package com.amachi.app.vitalia.management.tenant.controller;

import com.amachi.app.core.common.controller.BaseController;
import com.amachi.app.core.common.dto.PageResponseDto;
import com.amachi.app.core.geography.address.mapper.AddressMapper;
import com.amachi.app.core.geography.address.service.impl.AddressServiceImpl;
import com.amachi.app.core.domain.tenant.dto.TenantDto;
import com.amachi.app.core.domain.tenant.dto.search.TenantSearchDto;
import com.amachi.app.core.domain.tenant.entity.Tenant;

import com.amachi.app.core.domain.tenant.mapper.TenantMapper;
import com.amachi.app.vitalia.management.tenant.service.impl.TenantDomainServiceImpl;
import com.amachi.app.vitalia.management.tenant.service.impl.TenantServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class TenantController extends BaseController implements TenantApi {

    private final TenantServiceImpl service;
    private final TenantMapper mapper;
    private final AddressServiceImpl addressService;
    private final AddressMapper addressMapper;
    private final TenantDomainServiceImpl tenantDomainService;


    // --- SUPER ADMIN ENDPOINTS ---
    @Override
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<TenantDto> getTenantById(Long id) {
        Tenant entity = service.getById(id);
        return ResponseEntity.ok(mapper.toDto(entity));
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<TenantDto> createTenant(TenantDto dto) {
        Tenant entity = mapper.toEntity(dto);
        tenantDomainService.handleTenantAddress(entity, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDto(service.create(entity)));
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<TenantDto> updateTenant(Long id, TenantDto dto) {
        Tenant existingTenant = service.getById(id);
        mapper.updateEntityFromDto(dto, existingTenant);
        tenantDomainService.handleTenantAddress(existingTenant, dto);
        return ResponseEntity.ok(mapper.toDto(service.update(id, existingTenant)));
    }

    @Override
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Void> deleteTenant(Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<List<TenantDto>> getAllTenants() {
        List<Tenant> entities = service.getAll();
        List<TenantDto> dtos =  entities.stream()
                .map(entity ->  {
                    TenantDto dto = mapper.toDto(entity);
                    return tenantDomainService.enrichTenantDto(entity, dto);
                }).toList();
        return ResponseEntity.ok(dtos);
    }

    @Override
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<PageResponseDto<TenantDto>> getPaginatedTenants(TenantSearchDto searchDto, Integer pageIndex, Integer pageSize) {
        Page<Tenant> page = service.getAll(searchDto, pageIndex, pageSize);
        List<TenantDto> dtos = page.getContent()
                .stream()
                .map(entity -> {
                    TenantDto dto = mapper.toDto(entity);
                    return tenantDomainService.enrichTenantDto(entity, dto);
                })
                .toList();

        PageResponseDto<TenantDto> response = PageResponseDto.<TenantDto>builder()
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

    // --- PUBLIC ENDPOINTS ---
    @Override
    public ResponseEntity<List<TenantDto>> getPublicAllTenants() {
        List<Tenant> entities = service.getAll();
        return ResponseEntity.ok(entities.stream().map(mapper::toDto).toList());
    }

    // --- SUPER ADMIN ENDPOINTS EXTRAS ---
    @Override
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Void> enable(Long id) {
        service.enableTenant(id);
        return ResponseEntity.ok().build();
    }

    @Override
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Void> disable(Long id) {
        service.disableTenant(id);
        return ResponseEntity.ok().build();
    }
}