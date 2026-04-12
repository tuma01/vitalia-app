package com.amachi.app.vitalia.medicalcatalog.identity.service.impl;
 
import com.amachi.app.core.common.event.DomainEventPublisher;
import com.amachi.app.core.common.exception.BusinessException;

import com.amachi.app.core.common.service.BaseService;
import com.amachi.app.vitalia.medicalcatalog.identity.dto.search.IdentificationTypeSearchDto;
import com.amachi.app.vitalia.medicalcatalog.identity.entity.IdentificationType;
import com.amachi.app.vitalia.medicalcatalog.identity.event.IdentificationTypeCreatedEvent;
import com.amachi.app.vitalia.medicalcatalog.identity.repository.IdentificationTypeRepository;
import com.amachi.app.vitalia.medicalcatalog.identity.specification.IdentificationTypeSpecification;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
 
/**
 * Implementation of IdentificationType Service following SaaS Elite Tier standards.
 * Pure Global Catalog (No Tenant Isolation).
 */
@Service
@RequiredArgsConstructor
@Getter
public class IdentificationTypeServiceImpl extends BaseService<IdentificationType, IdentificationTypeSearchDto> {
 
    private final IdentificationTypeRepository repository;
    private final DomainEventPublisher eventPublisher;
 
    @Override
    protected Specification<IdentificationType> buildSpecification(IdentificationTypeSearchDto searchDto) {
        return new IdentificationTypeSpecification(searchDto);
    }
 
    @Override
    @Transactional
    public IdentificationType create(IdentificationType entity) {
        if (repository.existsByCode(entity.getCode().trim().toUpperCase())) {
            throw new BusinessException("Identification type code '" + entity.getCode() + "' already exists in Global Catalog");
        }
        return super.create(entity);
    }
 
    @Override
    protected void publishCreatedEvent(IdentificationType entity) {
        eventPublisher.publish(new IdentificationTypeCreatedEvent(entity.getId(), entity.getCode(), entity.getName()));
    }
 
    @Override
    protected void publishUpdatedEvent(IdentificationType entity) {
        // En este catálogo solo se publica evento al crear
    }
}
