package com.amachi.app.vitalia.medicalcatalog.medication.service.impl;
 
import com.amachi.app.core.common.event.DomainEventPublisher;
import com.amachi.app.core.common.exception.BusinessException;

import com.amachi.app.core.common.service.BaseService;
import com.amachi.app.vitalia.medicalcatalog.medication.dto.search.MedicationSearchDto;
import com.amachi.app.vitalia.medicalcatalog.medication.entity.Medication;
import com.amachi.app.vitalia.medicalcatalog.medication.event.MedicationCreatedEvent;
import com.amachi.app.vitalia.medicalcatalog.medication.repository.MedicationRepository;
import com.amachi.app.vitalia.medicalcatalog.medication.specification.MedicationSpecification;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
 
/**
 * Implementation of Medication Service following SaaS Elite Tier standards.
 */
@Service
@RequiredArgsConstructor
@Getter
public class MedicationServiceImpl extends BaseService<Medication, MedicationSearchDto> {
 
    private final MedicationRepository repository;
    private final DomainEventPublisher eventPublisher;
 
    @Override
    protected Specification<Medication> buildSpecification(MedicationSearchDto searchDto) {
        return new MedicationSpecification(searchDto);
    }
 
    @Override
    @Transactional
    public Medication create(Medication entity) {
        if (repository.existsByCode(entity.getCode().trim().toUpperCase())) {
            throw new BusinessException("Medication code '" + entity.getCode() + "' already exists in Global Catalog");
        }
        return super.create(entity);
    }
 
    @Override
    protected void publishCreatedEvent(Medication entity) {
        eventPublisher.publish(new MedicationCreatedEvent(entity.getId(), entity.getCode(), entity.getGenericName()));
    }
 
    @Override
    protected void publishUpdatedEvent(Medication entity) {
        // En este catálogo solo se publica evento al crear
    }
}
