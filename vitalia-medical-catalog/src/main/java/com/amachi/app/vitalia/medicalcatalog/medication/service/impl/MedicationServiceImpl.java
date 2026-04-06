package com.amachi.app.vitalia.medicalcatalog.medication.service.impl;

import com.amachi.app.core.common.event.DomainEventPublisher;
import com.amachi.app.core.common.exception.BusinessException;
import com.amachi.app.core.common.exception.ResourceNotFoundException;
import com.amachi.app.core.common.repository.CommonRepository;
import com.amachi.app.core.common.service.BaseService;
import com.amachi.app.vitalia.medicalcatalog.medication.dto.search.MedicationSearchDto;
import com.amachi.app.vitalia.medicalcatalog.medication.entity.Medication;
import com.amachi.app.vitalia.medicalcatalog.medication.event.MedicationCreatedEvent;
import com.amachi.app.vitalia.medicalcatalog.medication.repository.MedicationRepository;
import com.amachi.app.vitalia.medicalcatalog.medication.specification.MedicationSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MedicationServiceImpl extends BaseService<Medication, MedicationSearchDto> {

    private final MedicationRepository repository;
    private final DomainEventPublisher eventPublisher;

    @Override
    protected CommonRepository<Medication, Long> getRepository() {
        return repository;
    }

    @Override
    protected Specification<Medication> buildSpecification(MedicationSearchDto searchDto) {
        return new MedicationSpecification(searchDto);
    }

    @Override
    protected DomainEventPublisher getEventPublisher() {
        return eventPublisher;
    }

    @Override
    protected void publishCreatedEvent(Medication entity) {
        eventPublisher.publish(new MedicationCreatedEvent(
                entity.getId(), 
                entity.getCode(), 
                entity.getGenericName()
        ));
    }

    @Override
    protected void publishUpdatedEvent(Medication entity) {
        // No update event yet
    }

    @Override
    public Medication create(Medication entity) {
        // Al tratarse de una ENTIDAD GLOBAL (Catálogo), validamos unicidad transversal
        boolean exists = repository.existsByCode(entity.getCode().trim().toUpperCase());

        if (exists) {
            throw new BusinessException("Medication code '" + entity.getCode() + "' already exists in Global Catalog");
        }

        // Forzamos el Tenant ID a GLOBAL para evitar asociación accidental a un hospital
        entity.setTenantId("GLOBAL");
        
        return super.create(entity);
    }

    @Override
    public Medication update(Long id, Medication entity) {
        entity.setId(id);
        return super.update(id, entity);
    }

    /**
     * Finds a medication by its code (Global Catalog).
     * Useful for HL7 FHIR integration and clinical history.
     */
    public Medication getByCode(String code) {
        return repository.findByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException("Medication", "error.resource.not.found", code));
    }
}
