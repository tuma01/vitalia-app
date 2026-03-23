package com.amachi.app.core.management.tenantconfig.service.impl;

import com.amachi.app.core.auth.config.multiTenant.TenantCache;
import com.amachi.app.core.common.context.TenantContext;
import com.amachi.app.core.common.exception.BadRequestException;
import com.amachi.app.core.common.exception.ResourceNotFoundException;
import com.amachi.app.core.common.service.GenericService;
import com.amachi.app.core.domain.tenant.entity.Tenant;
import com.amachi.app.core.management.tenantconfig.dto.search.TenantConfigSearchDto;
import com.amachi.app.core.management.tenantconfig.entity.TenantConfig;
import com.amachi.app.core.management.tenantconfig.repository.TenantConfigRepository;
import com.amachi.app.core.management.tenantconfig.specification.TenantConfigSpecification;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.amachi.app.core.common.utils.AppConstants.ErrorMessages.ENTITY_MUST_NOT_BE_NULL;
import static com.amachi.app.core.common.utils.AppConstants.ErrorMessages.ID_MUST_NOT_BE_NULL;
import static java.util.Objects.requireNonNull;

@AllArgsConstructor
@Service
public class TenantConfigServiceImpl implements GenericService<TenantConfig, TenantConfigSearchDto> {

    private final TenantConfigRepository tenantConfigRepository;
    private final com.amachi.app.core.auth.config.multiTenant.TenantCache tenantCache;
    private final com.amachi.app.core.domain.tenant.repository.TenantRepository tenantRepository;
    private final com.amachi.app.core.domain.hospital.service.HospitalService hospitalService;

    @Override
    public List<TenantConfig> getAll() {
        return tenantConfigRepository.findAll();
    }

    @Override
    public Page<TenantConfig> getAll(TenantConfigSearchDto searchDto, Integer pageIndex, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize, Sort.by(Sort.Direction.DESC,"createdDate"));
        Specification<TenantConfig> specification = new TenantConfigSpecification(searchDto);
        return tenantConfigRepository.findAll(specification, pageable);
    }

    @Override
    public TenantConfig getById(Long id) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        return tenantConfigRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(TenantConfig.class.getName(), "error.resource.not.found", id));
    }

    public TenantConfig getByCurrentTenant() {
        String tenantCode = TenantContext.getTenantCode()
                .orElseThrow(() -> new ResourceNotFoundException(TenantConfig.class.getName(), "error.tenant.context_missing", "UNKNOWN"));

        return tenantConfigRepository.findByTenant_Code(tenantCode)
                .orElseGet(() -> {
                    Tenant tenant = tenantCache.getTenant(tenantCode);
                    if (tenant == null) {
                        throw new ResourceNotFoundException("Tenant", "error.tenant.not_found", tenantCode);
                    }

                    TenantConfig newConfig = TenantConfig.builder()
                            .tenant(tenant)
                            .defaultDomain(tenantCode + ".vitalia.com")
                            .locale("es-ES")
                            .timezone("UTC")
                            .allowLocal(true)
                            .maxUsers(50)
                            .storageQuotaBytes(1073741824L) // 1GB
                            .requireEmailVerification(false)
                            .build();

                    return tenantConfigRepository.save(newConfig);
                });
    }

    @Override
    public TenantConfig create(TenantConfig entity) {
        requireNonNull(entity, ENTITY_MUST_NOT_BE_NULL);
        return tenantConfigRepository.save(entity);
    }

    @Override
    @org.springframework.transaction.annotation.Transactional
    public TenantConfig update(Long id, TenantConfig entity) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        requireNonNull(entity, ENTITY_MUST_NOT_BE_NULL);

        // 1. Load Existing Config & Tenant
        TenantConfig existingConfig = tenantConfigRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(TenantConfig.class.getName(), "error.resource.not.found", id));
        
        var tenant = existingConfig.getTenant();
        if (tenant == null) {
            throw new BadRequestException("error.tenant.not_found");
        }

        // 2. Specialized Validation & Save (Senior Approach)
        if (tenant instanceof com.amachi.app.core.domain.hospital.entity.Hospital hospital) {
            // Business Rule: Medical License is mandatory for Hospitals
            if (hospital.getMedicalLicense() == null || hospital.getMedicalLicense().isBlank()) {
                throw new BadRequestException("error.hospital.medical_license_required");
            }
            hospitalService.update(hospital.getId(), hospital);
        } else {
            tenantRepository.save(tenant);
        }

        // 3. Address/Geo synchronization could go here if needed per implementation-guide.md

        // 4. Save Technical Config
        entity.setId(id);
        return tenantConfigRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        TenantConfig country = tenantConfigRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(TenantConfig.class.getName(), "error.resource.not.found", id));
        tenantConfigRepository.delete(country);
    }
}
