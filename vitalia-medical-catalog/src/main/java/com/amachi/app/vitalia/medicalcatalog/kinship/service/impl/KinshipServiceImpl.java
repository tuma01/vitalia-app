package com.amachi.app.vitalia.medicalcatalog.kinship.service.impl;

import com.amachi.app.core.common.event.DomainEventPublisher;
import com.amachi.app.core.common.exception.BusinessException;
import com.amachi.app.core.common.repository.CommonRepository;
import com.amachi.app.core.common.service.BaseService;
import com.amachi.app.vitalia.medicalcatalog.kinship.dto.search.KinshipSearchDto;
import com.amachi.app.vitalia.medicalcatalog.kinship.entity.Kinship;
import com.amachi.app.vitalia.medicalcatalog.kinship.event.KinshipCreatedEvent;
import com.amachi.app.vitalia.medicalcatalog.kinship.repository.KinshipRepository;
import com.amachi.app.vitalia.medicalcatalog.kinship.specification.KinshipSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KinshipServiceImpl extends BaseService<Kinship, KinshipSearchDto> {

    private final KinshipRepository repository;
    private final DomainEventPublisher eventPublisher;

    @Override
    protected CommonRepository<Kinship, Long> getRepository() {
        return repository;
    }

    @Override
    protected Specification<Kinship> buildSpecification(KinshipSearchDto searchDto) {
        return new KinshipSpecification(searchDto);
    }

    @Override
    protected DomainEventPublisher getEventPublisher() {
        return eventPublisher;
    }

    @Override
    protected void publishCreatedEvent(Kinship entity) {
        eventPublisher.publish(new KinshipCreatedEvent(
                entity.getId(), 
                entity.getCode(), 
                entity.getName()
        ));
    }

    @Override
    protected void publishUpdatedEvent(Kinship entity) {
        // No update event yet
    }

    @Override
    public Kinship create(Kinship entity) {
        // Al tratarse de una ENTIDAD GLOBAL (Catálogo), validamos unicidad transversal
        boolean exists = repository.existsByCode(entity.getCode().trim().toUpperCase());

        if (exists) {
            throw new BusinessException("Kinship code '" + entity.getCode() + "' already exists in Global Catalog");
        }

        // Forzamos el Tenant ID a GLOBAL para evitar asociación accidental a un hospital
        entity.setTenantId("GLOBAL");
        
        return super.create(entity);
    }

    @Override
    public Kinship update(Long id, Kinship entity) {
        entity.setId(id);
        return super.update(id, entity);
    }
}
