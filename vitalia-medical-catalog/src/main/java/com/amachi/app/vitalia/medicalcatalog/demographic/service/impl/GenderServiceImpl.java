package com.amachi.app.vitalia.medicalcatalog.demographic.service.impl;

import com.amachi.app.core.common.event.DomainEventPublisher;
import com.amachi.app.core.common.exception.BusinessException;
import com.amachi.app.core.common.repository.CommonRepository;
import com.amachi.app.core.common.service.BaseService;
import com.amachi.app.vitalia.medicalcatalog.demographic.dto.search.GenderSearchDto;
import com.amachi.app.vitalia.medicalcatalog.demographic.entity.Gender;
import com.amachi.app.vitalia.medicalcatalog.demographic.event.GenderCreatedEvent;
import com.amachi.app.vitalia.medicalcatalog.demographic.repository.GenderRepository;
import com.amachi.app.vitalia.medicalcatalog.demographic.specification.GenderSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GenderServiceImpl extends BaseService<Gender, GenderSearchDto> {

    private final GenderRepository repository;
    private final DomainEventPublisher eventPublisher;

    @Override
    protected CommonRepository<Gender, Long> getRepository() {
        return repository;
    }

    @Override
    protected Specification<Gender> buildSpecification(GenderSearchDto searchDto) {
        return new GenderSpecification(searchDto);
    }

    @Override
    protected DomainEventPublisher getEventPublisher() {
        return eventPublisher;
    }

    @Override
    protected void publishCreatedEvent(Gender entity) {
        eventPublisher.publish(new GenderCreatedEvent(
                entity.getId(), 
                entity.getCode(), 
                entity.getName()
        ));
    }

    @Override
    protected void publishUpdatedEvent(Gender entity) {
        // No update event yet
    }

    @Override
    public Gender create(Gender entity) {
        // Al tratarse de una ENTIDAD GLOBAL (Catálogo), validamos unicidad transversal
        boolean exists = repository.existsByCode(entity.getCode().trim().toUpperCase());

        if (exists) {
            throw new BusinessException("Gender code '" + entity.getCode() + "' already exists in Global Catalog");
        }

        // Forzamos el Tenant ID a GLOBAL para evitar asociación accidental a un hospital
        entity.setTenantId("GLOBAL");
        
        return super.create(entity);
    }

    @Override
    public Gender update(Long id, Gender entity) {
        entity.setId(id);
        return super.update(id, entity);
    }
}
