package com.amachi.app.vitalia.medicalcatalog.healthcareprovider.service.impl;

import static java.util.Objects.requireNonNull;

import com.amachi.app.core.common.event.DomainEventPublisher;
import com.amachi.app.core.common.exception.BusinessException;
import com.amachi.app.core.common.repository.CommonRepository;
import com.amachi.app.core.common.service.BaseService;
import com.amachi.app.vitalia.medicalcatalog.healthcareprovider.dto.search.HealthcareProviderSearchDto;
import com.amachi.app.vitalia.medicalcatalog.healthcareprovider.entity.HealthcareProvider;
import com.amachi.app.vitalia.medicalcatalog.healthcareprovider.event.HealthcareProviderCreatedEvent;
import com.amachi.app.vitalia.medicalcatalog.healthcareprovider.repository.HealthcareProviderRepository;
import com.amachi.app.vitalia.medicalcatalog.healthcareprovider.specification.HealthcareProviderSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HealthcareProviderServiceImpl extends BaseService<HealthcareProvider, HealthcareProviderSearchDto> {

    private final HealthcareProviderRepository repository;
    private final DomainEventPublisher eventPublisher;

    @Override
    protected CommonRepository<HealthcareProvider, Long> getRepository() {
        return repository;
    }

    @Override
    protected Specification<HealthcareProvider> buildSpecification(HealthcareProviderSearchDto searchDto) {
        return new HealthcareProviderSpecification(searchDto);
    }

    @Override
    protected DomainEventPublisher getEventPublisher() {
        return eventPublisher;
    }

    @Override
    protected void publishCreatedEvent(HealthcareProvider entity) {
        requireNonNull(entity, "Entity cannot be null for event publication");
        eventPublisher.publish(new HealthcareProviderCreatedEvent(
                entity.getId(), 
                entity.getCode(), 
                entity.getName(),
                entity.getTaxId()
        ));
    }

    @Override
    protected void publishUpdatedEvent(HealthcareProvider entity) {
        // No update event yet
    }

    @Override
    public HealthcareProvider create(HealthcareProvider entity) {
        requireNonNull(entity, "HealthcareProvider entity must not be null");
        
        // Al tratarse de una ENTIDAD GLOBAL (Catálogo), validamos unicidad transversal
        
        // 1. Validar código único en el Catálogo Global
        if (repository.existsByCode(entity.getCode().trim().toUpperCase())) {
            throw new BusinessException("Provider code '" + entity.getCode() + "' already exists in Global Catalog");
        }

        // 2. Validar Tax ID único en el Catálogo Global
        if (repository.existsByTaxId(entity.getTaxId().trim().toUpperCase())) {
            throw new BusinessException("Tax ID '" + entity.getTaxId() + "' already exists in Global Catalog");
        }

        // Forzamos el Tenant ID a GLOBAL para evitar asociación accidental a un hospital
        entity.setTenantId("GLOBAL");
        
        return super.create(entity);
    }

    @Override
    public HealthcareProvider update(Long id, HealthcareProvider entity) {
        entity.setId(id);
        return super.update(id, entity);
    }
}
