package com.amachi.app.vitalia.medicalcatalog.diagnosis.service.impl;

import com.amachi.app.core.common.event.DomainEventPublisher;
import com.amachi.app.core.common.exception.BusinessException;
import com.amachi.app.core.common.repository.CommonRepository;
import com.amachi.app.core.common.service.BaseService;
import com.amachi.app.vitalia.medicalcatalog.diagnosis.dto.search.Icd10SearchDto;
import com.amachi.app.vitalia.medicalcatalog.diagnosis.entity.Icd10;
import com.amachi.app.vitalia.medicalcatalog.diagnosis.event.Icd10CreatedEvent;
import com.amachi.app.vitalia.medicalcatalog.diagnosis.repository.Icd10Repository;
import com.amachi.app.vitalia.medicalcatalog.diagnosis.specification.Icd10Specification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Icd10ServiceImpl extends BaseService<Icd10, Icd10SearchDto> {

    private final Icd10Repository repository;
    private final DomainEventPublisher eventPublisher;

    @Override
    protected CommonRepository<Icd10, Long> getRepository() {
        return repository;
    }

    @Override
    protected Specification<Icd10> buildSpecification(Icd10SearchDto searchDto) {
        return new Icd10Specification(searchDto);
    }

    @Override
    protected DomainEventPublisher getEventPublisher() {
        return eventPublisher;
    }

    @Override
    protected void publishCreatedEvent(Icd10 entity) {
        eventPublisher.publish(new Icd10CreatedEvent(
                entity.getId(), 
                entity.getCode(), 
                entity.getDescription()
        ));
    }

    @Override
    protected void publishUpdatedEvent(Icd10 entity) {
        // No update event yet
    }

    @Override
    public Icd10 create(Icd10 entity) {
        // Al tratarse de una ENTIDAD GLOBAL (Catálogo), validamos unicidad transversal
        boolean exists = repository.existsByCode(entity.getCode().trim().toUpperCase());

        if (exists) {
            throw new BusinessException("ICD-10 code '" + entity.getCode() + "' already exists in Global Catalog");
        }

        // Forzamos el Tenant ID a GLOBAL para evitar asociación accidental a un hospital
        entity.setTenantId("GLOBAL");
        
        return super.create(entity);
    }

    @Override
    public Icd10 update(Long id, Icd10 entity) {
        entity.setId(id);
        return super.update(id, entity);
    }
}
