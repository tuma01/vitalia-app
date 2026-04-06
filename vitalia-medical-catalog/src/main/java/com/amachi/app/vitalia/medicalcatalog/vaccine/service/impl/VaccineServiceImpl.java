package com.amachi.app.vitalia.medicalcatalog.vaccine.service.impl;

import com.amachi.app.core.common.event.DomainEventPublisher;
import com.amachi.app.core.common.exception.BusinessException;
import com.amachi.app.core.common.repository.CommonRepository;
import com.amachi.app.core.common.service.BaseService;
import com.amachi.app.vitalia.medicalcatalog.vaccine.dto.search.VaccineSearchDto;
import com.amachi.app.vitalia.medicalcatalog.vaccine.entity.Vaccine;
import com.amachi.app.vitalia.medicalcatalog.vaccine.event.VaccineCreatedEvent;
import com.amachi.app.vitalia.medicalcatalog.vaccine.repository.VaccineRepository;
import com.amachi.app.vitalia.medicalcatalog.vaccine.specification.VaccineSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VaccineServiceImpl extends BaseService<Vaccine, VaccineSearchDto> {

    private final VaccineRepository repository;
    private final DomainEventPublisher eventPublisher;

    @Override
    protected CommonRepository<Vaccine, Long> getRepository() {
        return repository;
    }

    @Override
    protected Specification<Vaccine> buildSpecification(VaccineSearchDto searchDto) {
        return new VaccineSpecification(searchDto);
    }

    @Override
    protected DomainEventPublisher getEventPublisher() {
        return eventPublisher;
    }

    @Override
    protected void publishCreatedEvent(Vaccine entity) {
        eventPublisher.publish(new VaccineCreatedEvent(
                entity.getId(), 
                entity.getCode(), 
                entity.getName()
        ));
    }

    @Override
    protected void publishUpdatedEvent(Vaccine entity) {
        // No update event yet
    }

    @Override
    public Vaccine create(Vaccine entity) {
        // Al tratarse de una ENTIDAD GLOBAL (Catálogo), validamos unicidad transversal
        boolean exists = repository.existsByCode(entity.getCode().trim().toUpperCase());

        if (exists) {
            throw new BusinessException("Vaccine code '" + entity.getCode() + "' already exists in Global Catalog");
        }

        // Forzamos el Tenant ID a GLOBAL para evitar asociación accidental a un hospital
        entity.setTenantId("GLOBAL");
        
        return super.create(entity);
    }

    @Override
    public Vaccine update(Long id, Vaccine entity) {
        entity.setId(id);
        return super.update(id, entity);
    }
}
