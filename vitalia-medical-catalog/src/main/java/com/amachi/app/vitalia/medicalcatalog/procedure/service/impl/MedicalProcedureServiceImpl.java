package com.amachi.app.vitalia.medicalcatalog.procedure.service.impl;

import com.amachi.app.core.common.event.DomainEventPublisher;
import com.amachi.app.core.common.exception.BusinessException;
import com.amachi.app.core.common.repository.CommonRepository;
import com.amachi.app.core.common.service.BaseService;
import com.amachi.app.vitalia.medicalcatalog.procedure.dto.search.MedicalProcedureSearchDto;
import com.amachi.app.vitalia.medicalcatalog.procedure.entity.MedicalProcedure;
import com.amachi.app.vitalia.medicalcatalog.procedure.event.MedicalProcedureCreatedEvent;
import com.amachi.app.vitalia.medicalcatalog.procedure.repository.MedicalProcedureRepository;
import com.amachi.app.vitalia.medicalcatalog.procedure.specification.MedicalProcedureSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MedicalProcedureServiceImpl extends BaseService<MedicalProcedure, MedicalProcedureSearchDto> {

    private final MedicalProcedureRepository repository;
    private final DomainEventPublisher eventPublisher;

    @Override
    protected CommonRepository<MedicalProcedure, Long> getRepository() {
        return repository;
    }

    @Override
    protected Specification<MedicalProcedure> buildSpecification(MedicalProcedureSearchDto searchDto) {
        return new MedicalProcedureSpecification(searchDto);
    }

    @Override
    protected DomainEventPublisher getEventPublisher() {
        return eventPublisher;
    }

    @Override
    protected void publishCreatedEvent(MedicalProcedure entity) {
        eventPublisher.publish(new MedicalProcedureCreatedEvent(
                entity.getId(), 
                entity.getCode(), 
                entity.getName()
        ));
    }

    @Override
    protected void publishUpdatedEvent(MedicalProcedure entity) {
        // No update event yet
    }

    @Override
    public MedicalProcedure create(MedicalProcedure entity) {
        // Al tratarse de una ENTIDAD GLOBAL (Catálogo), validamos unicidad transversal
        boolean exists = repository.existsByCode(entity.getCode().trim().toUpperCase());

        if (exists) {
            throw new BusinessException("Medical procedure code '" + entity.getCode() + "' already exists in Global Catalog");
        }

        // Forzamos el Tenant ID a GLOBAL para evitar asociación accidental a un hospital
        entity.setTenantId("GLOBAL");
        
        return super.create(entity);
    }

    @Override
    public MedicalProcedure update(Long id, MedicalProcedure entity) {
        entity.setId(id);
        return super.update(id, entity);
    }
}
