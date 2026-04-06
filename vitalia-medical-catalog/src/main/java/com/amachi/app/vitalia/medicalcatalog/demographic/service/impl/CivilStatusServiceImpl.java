package com.amachi.app.vitalia.medicalcatalog.demographic.service.impl;

import com.amachi.app.core.common.event.DomainEventPublisher;
import com.amachi.app.core.common.exception.BusinessException;
import com.amachi.app.core.common.repository.CommonRepository;
import com.amachi.app.core.common.service.BaseService;
import com.amachi.app.vitalia.medicalcatalog.demographic.dto.search.CivilStatusSearchDto;
import com.amachi.app.vitalia.medicalcatalog.demographic.entity.CivilStatus;
import com.amachi.app.vitalia.medicalcatalog.demographic.event.CivilStatusCreatedEvent;
import com.amachi.app.vitalia.medicalcatalog.demographic.repository.CivilStatusRepository;
import com.amachi.app.vitalia.medicalcatalog.demographic.specification.CivilStatusSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CivilStatusServiceImpl extends BaseService<CivilStatus, CivilStatusSearchDto> {

    private final CivilStatusRepository repository;
    private final DomainEventPublisher eventPublisher;

    @Override
    protected CommonRepository<CivilStatus, Long> getRepository() {
        return repository;
    }

    @Override
    protected Specification<CivilStatus> buildSpecification(CivilStatusSearchDto searchDto) {
        return new CivilStatusSpecification(searchDto);
    }

    @Override
    protected DomainEventPublisher getEventPublisher() {
        return eventPublisher;
    }

    @Override
    protected void publishCreatedEvent(CivilStatus entity) {
        eventPublisher.publish(new CivilStatusCreatedEvent(
                entity.getId(), 
                entity.getCode(), 
                entity.getName()
        ));
    }

    @Override
    protected void publishUpdatedEvent(CivilStatus entity) {
        // No update event yet
    }

    @Override
    public CivilStatus create(CivilStatus entity) {
        // Al tratarse de una ENTIDAD GLOBAL (Catálogo), validamos unicidad transversal
        boolean exists = repository.existsByCode(entity.getCode().trim().toUpperCase());

        if (exists) {
            throw new BusinessException("Civil status code '" + entity.getCode() + "' already exists in Global Catalog");
        }

        // Forzamos el Tenant ID a GLOBAL para evitar asociación accidental a un hospital
        entity.setTenantId("GLOBAL");
        
        return super.create(entity);
    }

    @Override
    public CivilStatus update(Long id, CivilStatus entity) {
        entity.setId(id);
        return super.update(id, entity);
    }
}
