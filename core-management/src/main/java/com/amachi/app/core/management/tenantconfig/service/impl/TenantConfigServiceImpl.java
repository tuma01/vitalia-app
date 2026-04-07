package com.amachi.app.core.management.tenantconfig.service.impl;

import com.amachi.app.core.auth.config.multiTenant.TenantCache;
import com.amachi.app.core.common.context.TenantContext;
import com.amachi.app.core.common.event.DomainEventPublisher;
import com.amachi.app.core.common.exception.BadRequestException;
import com.amachi.app.core.common.exception.ResourceNotFoundException;
import com.amachi.app.core.common.repository.CommonRepository;
import com.amachi.app.core.common.service.BaseService;
import com.amachi.app.core.domain.tenant.entity.Tenant;
import com.amachi.app.core.domain.tenant.repository.TenantRepository;
import com.amachi.app.core.management.tenantconfig.dto.search.TenantConfigSearchDto;
import com.amachi.app.core.management.tenantconfig.entity.TenantConfig;
import com.amachi.app.core.management.tenantconfig.event.TenantConfigCreatedEvent;
import com.amachi.app.core.management.tenantconfig.repository.TenantConfigRepository;
import com.amachi.app.core.management.tenantconfig.service.TenantConfigService;
import com.amachi.app.core.management.tenantconfig.specification.TenantConfigSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TenantConfigServiceImpl extends BaseService<TenantConfig, TenantConfigSearchDto> implements TenantConfigService {

    private final TenantConfigRepository repository;
    private final TenantCache tenantCache;
    private final TenantRepository tenantRepository;
    private final com.amachi.app.core.domain.hospital.service.HospitalService hospitalService;
    private final DomainEventPublisher eventPublisher;

    @Override
    protected CommonRepository<TenantConfig, Long> getRepository() {
        return repository;
    }

    @Override
    protected Specification<TenantConfig> buildSpecification(TenantConfigSearchDto searchDto) {
        return new TenantConfigSpecification(searchDto);
    }

    @Override
    protected DomainEventPublisher getEventPublisher() {
        return eventPublisher;
    }

    @Override
    protected void publishCreatedEvent(TenantConfig entity) {
        eventPublisher.publish(new TenantConfigCreatedEvent(
                entity.getId(),
                entity.getTenant() != null ? entity.getTenant().getCode() : null,
                entity.getSubscriptionPlan()
        ));
    }

    @Override
    protected void publishUpdatedEvent(TenantConfig entity) {
        // Evento lanzado al actualizar una TenantConfig si aplica
    }

    public TenantConfig getByCurrentTenant() {
        String tenantCode = TenantContext.getTenant();
        if (tenantCode == null) {
            throw new ResourceNotFoundException("TenantConfig", "error.tenant.context_missing", "UNKNOWN");
        }

        return repository.findByTenant_Code(tenantCode)
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

                    return this.create(newConfig);
                });
    }

    @Override
    @Transactional
    public TenantConfig create(TenantConfig entity) {
        if (entity.getTenant() != null && repository.existsByTenant_Id(entity.getTenant().getId())) {
            throw new BadRequestException("Configuration for this tenant already exists.");
        }
        return super.create(entity);
    }

    @Override
    @Transactional
    public TenantConfig update(Long id, TenantConfig entity) {
        TenantConfig existingConfig = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TenantConfig", "error.resource.not.found", id));

        var tenant = existingConfig.getTenant();
        if (tenant == null) {
            throw new BadRequestException("error.tenant.not_found");
        }

        // Specialized Validation for Hospitals
        if (tenant instanceof com.amachi.app.core.domain.hospital.entity.Hospital hospital) {
            if (hospital.getMedicalLicense() == null || hospital.getMedicalLicense().isBlank()) {
                throw new BadRequestException("error.hospital.medical_license_required");
            }
            hospitalService.update(hospital.getId(), hospital);
        } else {
            tenantRepository.save(tenant);
        }

        entity.setId(id);
        return super.update(id, entity);
    }
}
