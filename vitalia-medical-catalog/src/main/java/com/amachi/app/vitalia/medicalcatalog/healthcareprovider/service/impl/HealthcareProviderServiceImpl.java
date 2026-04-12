package com.amachi.app.vitalia.medicalcatalog.healthcareprovider.service.impl;
 
import com.amachi.app.core.common.event.DomainEventPublisher;
import com.amachi.app.core.common.exception.BusinessException;

import com.amachi.app.core.common.service.BaseService;
import com.amachi.app.vitalia.medicalcatalog.healthcareprovider.dto.search.HealthcareProviderSearchDto;
import com.amachi.app.vitalia.medicalcatalog.healthcareprovider.entity.HealthcareProvider;
import com.amachi.app.vitalia.medicalcatalog.healthcareprovider.event.HealthcareProviderCreatedEvent;
import com.amachi.app.vitalia.medicalcatalog.healthcareprovider.repository.HealthcareProviderRepository;
import com.amachi.app.vitalia.medicalcatalog.healthcareprovider.specification.HealthcareProviderSpecification;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
 
import static java.util.Objects.requireNonNull;
 
/**
 * Implementation of HealthcareProvider Service following SaaS Elite Tier standards.
 * Pure Global Catalog (No Tenant Isolation).
 */
@Service
@RequiredArgsConstructor
@Getter
public class HealthcareProviderServiceImpl extends BaseService<HealthcareProvider, HealthcareProviderSearchDto> {
 
    private final HealthcareProviderRepository repository;
    private final DomainEventPublisher eventPublisher;
 
    @Override
    protected Specification<HealthcareProvider> buildSpecification(HealthcareProviderSearchDto searchDto) {
        return new HealthcareProviderSpecification(searchDto);
    }
 
    @Override
    @Transactional
    public HealthcareProvider create(HealthcareProvider entity) {
        requireNonNull(entity, "HealthcareProvider entity must not be null");
        
        if (repository.existsByCode(entity.getCode().trim().toUpperCase())) {
            throw new BusinessException("Provider code '" + entity.getCode() + "' already exists in Global Catalog");
        }
 
        if (repository.existsByTaxId(entity.getTaxId().trim().toUpperCase())) {
            throw new BusinessException("Tax ID '" + entity.getTaxId() + "' already exists in Global Catalog");
        }
 
        return super.create(entity);
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
        // En este catálogo solo se publica evento al crear
    }
}
