package com.amachi.app.vitalia.medicalcatalog.specialty.service.impl;

import com.amachi.app.core.common.event.DomainEventPublisher;
import com.amachi.app.core.common.exception.BusinessException;
import com.amachi.app.core.common.repository.CommonRepository;
import com.amachi.app.core.common.service.BaseService;
import com.amachi.app.vitalia.medicalcatalog.specialty.dto.search.MedicalSpecialtySearchDto;
import com.amachi.app.vitalia.medicalcatalog.specialty.entity.MedicalSpecialty;
import com.amachi.app.vitalia.medicalcatalog.specialty.event.MedicalSpecialtyCreatedEvent;
import com.amachi.app.vitalia.medicalcatalog.specialty.repository.MedicalSpecialtyRepository;
import com.amachi.app.vitalia.medicalcatalog.specialty.specification.MedicalSpecialtySpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MedicalSpecialtyServiceImpl extends BaseService<MedicalSpecialty, MedicalSpecialtySearchDto> {

    private final MedicalSpecialtyRepository repository;
    private final DomainEventPublisher eventPublisher;

    @Override
    protected CommonRepository<MedicalSpecialty, Long> getRepository() {
        return repository;
    }

    @Override
    protected Specification<MedicalSpecialty> buildSpecification(MedicalSpecialtySearchDto searchDto) {
        return new MedicalSpecialtySpecification(searchDto);
    }

    @Override
    protected DomainEventPublisher getEventPublisher() {
        return eventPublisher;
    }

    @Override
    protected void publishCreatedEvent(MedicalSpecialty entity) {
        eventPublisher.publish(new MedicalSpecialtyCreatedEvent(
                entity.getId(), 
                entity.getCode(), 
                entity.getName()
        ));
    }

    @Override
    protected void publishUpdatedEvent(MedicalSpecialty entity) {
        // No update event yet
    }

    @Override
    public MedicalSpecialty create(MedicalSpecialty entity) {
        // Al tratarse de una ENTIDAD GLOBAL (Catálogo), validamos unicidad transversal
        boolean exists = repository.existsByCode(entity.getCode().trim().toUpperCase());

        if (exists) {
            throw new BusinessException("Medical specialty code '" + entity.getCode() + "' already exists in Global Catalog");
        }

        // Forzamos el Tenant ID a GLOBAL para evitar asociación accidental a un hospital
        entity.setTenantId("GLOBAL");
        
        return super.create(entity);
    }

    @Override
    public MedicalSpecialty update(Long id, MedicalSpecialty entity) {
        entity.setId(id);
        return super.update(id, entity);
    }
}
