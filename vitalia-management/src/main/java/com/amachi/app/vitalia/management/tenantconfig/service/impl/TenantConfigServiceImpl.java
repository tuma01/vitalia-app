package com.amachi.app.vitalia.management.tenantconfig.service.impl;

import com.amachi.app.core.auth.config.multiTenant.TenantCache;
import com.amachi.app.core.common.context.TenantContext;
import com.amachi.app.core.common.exception.ResourceNotFoundException;
import com.amachi.app.core.common.service.GenericService;
import com.amachi.app.core.domain.tenant.entity.Tenant;
import com.amachi.app.vitalia.management.tenantconfig.dto.search.TenantConfigSearchDto;
import com.amachi.app.vitalia.management.tenantconfig.entity.TenantConfig;
import com.amachi.app.vitalia.management.tenantconfig.repository.TenantConfigRepository;
import com.amachi.app.vitalia.management.tenantconfig.specification.TenantConfigSpecification;
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

    TenantConfigRepository tenantConfigRepository;
    TenantCache tenantCache;

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

    /**
     * Obtiene la configuración para el tenant actual del contexto.
     * Si no existe, crea una por defecto para no romper el flujo del frontend.
     */
    public TenantConfig getByCurrentTenant() {
        String tenantCode = TenantContext.getTenantCode()
                .orElseThrow(() -> new ResourceNotFoundException(TenantConfig.class.getName(), "error.tenant.context_missing", "UNKNOWN"));

        return tenantConfigRepository.findByTenant_Code(tenantCode)
                .orElseGet(() -> {
                    // Si no existe, inicializamos uno proactivamente (Patrón Professional Resilience)
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
    public TenantConfig update(Long id, TenantConfig entity) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        requireNonNull(entity, ENTITY_MUST_NOT_BE_NULL);
        // Verificar que el TenantConfig exista
        tenantConfigRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(TenantConfig.class.getName(), "error.resource.not.found", id));
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
