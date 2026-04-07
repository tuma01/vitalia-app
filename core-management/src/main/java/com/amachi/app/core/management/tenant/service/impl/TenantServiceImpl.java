package com.amachi.app.core.management.tenant.service.impl;

import com.amachi.app.core.common.event.DomainEventPublisher;
import com.amachi.app.core.common.exception.ResourceNotFoundException;
import com.amachi.app.core.common.repository.CommonRepository;
import com.amachi.app.core.common.service.BaseService;
import com.amachi.app.core.domain.tenant.dto.TenantDto;
import com.amachi.app.core.domain.tenant.dto.search.TenantSearchDto;
import com.amachi.app.core.domain.tenant.entity.Tenant;
import com.amachi.app.core.domain.tenant.repository.TenantRepository;
import com.amachi.app.core.management.tenant.event.TenantCreatedEvent;
import com.amachi.app.core.management.tenant.specification.TenantSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TenantServiceImpl extends BaseService<Tenant, TenantSearchDto> {

    private final TenantRepository repository;
    private final TenantDomainServiceImpl tenantDomainService;
    private final DomainEventPublisher eventPublisher;

    @Override
    protected CommonRepository<Tenant, Long> getRepository() {
        return repository;
    }

    @Override
    protected Specification<Tenant> buildSpecification(TenantSearchDto searchDto) {
        return new TenantSpecification(searchDto);
    }

    @Override
    protected DomainEventPublisher getEventPublisher() {
        return eventPublisher;
    }

    @Override
    protected void publishCreatedEvent(Tenant entity) {
        eventPublisher.publish(new TenantCreatedEvent(
                entity.getId(),
                entity.getCode(),
                entity.getName()
        ));
    }

    @Override
    protected void publishUpdatedEvent(Tenant entity) {
        // Evento lanzado al actualizar un Tenant si aplica
    }

    @Transactional(readOnly = true)
    public Tenant getByCode(String code) {
        return repository.findByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant", "error.tenant_not_found_by_code", code));
    }

    @Transactional
    public Tenant createWithDetails(Tenant entity, TenantDto dto) {
        tenantDomainService.handleTenantAddress(entity, dto);
        tenantDomainService.handleTenantTheme(entity, dto);
        return create(entity);
    }

    @Transactional
    public Tenant updateWithDetails(Long id, Tenant entity, TenantDto dto) {
        tenantDomainService.handleTenantAddress(entity, dto);
        tenantDomainService.handleTenantTheme(entity, dto);
        entity.setId(id);
        return super.update(id, entity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        disableTenant(id);
    }

    @Transactional
    public void enableTenant(Long id) {
        Tenant t = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant", "error.resource.not.found", id));
        t.setIsActive(true);
        repository.save(t);
    }

    @Transactional
    public void disableTenant(Long id) {
        Tenant t = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant", "error.resource.not.found", id));
        t.setIsActive(false);
        repository.save(t);
    }
}
