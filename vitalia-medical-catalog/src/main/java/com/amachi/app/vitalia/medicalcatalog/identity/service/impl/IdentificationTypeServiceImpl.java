package com.amachi.app.vitalia.medicalcatalog.identity.service.impl;

import com.amachi.app.core.common.event.DomainEventPublisher;
import com.amachi.app.core.common.exception.BusinessException;
import com.amachi.app.core.common.repository.CommonRepository;
import com.amachi.app.core.common.service.BaseService;
import com.amachi.app.vitalia.medicalcatalog.identity.dto.search.IdentificationTypeSearchDto;
import com.amachi.app.vitalia.medicalcatalog.identity.entity.IdentificationType;
import com.amachi.app.vitalia.medicalcatalog.identity.event.IdentificationTypeCreatedEvent;
import com.amachi.app.vitalia.medicalcatalog.identity.repository.IdentificationTypeRepository;
import com.amachi.app.vitalia.medicalcatalog.identity.specification.IdentificationTypeSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IdentificationTypeServiceImpl extends BaseService<IdentificationType, IdentificationTypeSearchDto> {

    private final IdentificationTypeRepository repository;
    private final DomainEventPublisher eventPublisher;

    @Override
    protected CommonRepository<IdentificationType, Long> getRepository() {
        return repository;
    }

    @Override
    protected Specification<IdentificationType> buildSpecification(IdentificationTypeSearchDto searchDto) {
        return new IdentificationTypeSpecification(searchDto);
    }

    @Override
    protected DomainEventPublisher getEventPublisher() {
        return eventPublisher;
    }

    @Override
    protected void publishCreatedEvent(IdentificationType entity) {
        eventPublisher.publish(new IdentificationTypeCreatedEvent(
                entity.getId(), 
                entity.getCode(), 
                entity.getName()
        ));
    }

    @Override
    protected void publishUpdatedEvent(IdentificationType entity) {
        // No update event yet
    }

    @Override
    public IdentificationType create(IdentificationType entity) {
        // Al tratarse de una ENTIDAD GLOBAL (Catálogo), validamos unicidad transversal
        boolean exists = repository.existsByCode(entity.getCode().trim().toUpperCase());

        if (exists) {
            throw new BusinessException("Identification type code '" + entity.getCode() + "' already exists in Global Catalog");
        }

        // Forzamos el Tenant ID a GLOBAL para evitar asociación accidental a un hospital
        entity.setTenantId("GLOBAL");
        
        return super.create(entity);
    }

    @Override
    public IdentificationType update(Long id, IdentificationType entity) {
        entity.setId(id);
        return super.update(id, entity);
    }
}
