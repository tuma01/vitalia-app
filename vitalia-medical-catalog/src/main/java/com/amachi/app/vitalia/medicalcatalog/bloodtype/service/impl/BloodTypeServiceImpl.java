package com.amachi.app.vitalia.medicalcatalog.bloodtype.service.impl;

import com.amachi.app.core.common.event.DomainEventPublisher;
import com.amachi.app.core.common.exception.BusinessException;
import com.amachi.app.core.common.repository.CommonRepository;
import com.amachi.app.core.common.service.BaseService;
import com.amachi.app.vitalia.medicalcatalog.bloodtype.dto.search.BloodTypeSearchDto;
import com.amachi.app.vitalia.medicalcatalog.bloodtype.entity.BloodType;
import com.amachi.app.vitalia.medicalcatalog.bloodtype.event.BloodTypeCreatedEvent;
import com.amachi.app.vitalia.medicalcatalog.bloodtype.repository.BloodTypeRepository;
import com.amachi.app.vitalia.medicalcatalog.bloodtype.specification.BloodTypeSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BloodTypeServiceImpl extends BaseService<BloodType, BloodTypeSearchDto> {

    private final BloodTypeRepository repository;
    private final DomainEventPublisher eventPublisher;

    @Override
    protected CommonRepository<BloodType, Long> getRepository() {
        return repository;
    }

    @Override
    protected Specification<BloodType> buildSpecification(BloodTypeSearchDto searchDto) {
        return new BloodTypeSpecification(searchDto);
    }

    @Override
    protected DomainEventPublisher getEventPublisher() {
        return eventPublisher;
    }

    @Override
    protected void publishCreatedEvent(BloodType entity) {
        eventPublisher.publish(new BloodTypeCreatedEvent(
                entity.getId(), 
                entity.getCode(), 
                entity.getName()
        ));
    }

    @Override
    protected void publishUpdatedEvent(BloodType entity) {
        // No update event yet
    }

    @Override
    public BloodType create(BloodType entity) {
        // Al tratarse de una ENTIDAD GLOBAL (Catálogo), validamos unicidad transversal
        boolean exists = repository.existsByCode(entity.getCode().trim().toUpperCase());

        if (exists) {
            throw new BusinessException("Blood type code '" + entity.getCode() + "' already exists in Global Catalog");
        }

        // Forzamos el Tenant ID a GLOBAL para evitar asociación accidental a un hospital
        entity.setTenantId("GLOBAL");
        
        return super.create(entity);
    }

    @Override
    public BloodType update(Long id, BloodType entity) {
        entity.setId(id);
        return super.update(id, entity);
    }
}
