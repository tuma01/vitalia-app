package com.amachi.app.vitalia.medicalcatalog.specialty.service.impl;

import com.amachi.app.core.common.event.DomainEventPublisher;
import com.amachi.app.core.common.exception.BusinessException;
import com.amachi.app.core.common.service.BaseService;
import com.amachi.app.vitalia.medicalcatalog.specialty.dto.search.MedicalSpecialtySearchDto;
import com.amachi.app.vitalia.medicalcatalog.specialty.entity.MedicalSpecialty;
import com.amachi.app.vitalia.medicalcatalog.specialty.event.MedicalSpecialtyCreatedEvent;
import com.amachi.app.vitalia.medicalcatalog.specialty.repository.MedicalSpecialtyRepository;
import com.amachi.app.vitalia.medicalcatalog.specialty.specification.MedicalSpecialtySpecification;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of MedicalSpecialty Service following SaaS Elite Tier standards.
 */
@Service
@RequiredArgsConstructor
@Getter
public class MedicalSpecialtyServiceImpl extends BaseService<MedicalSpecialty, MedicalSpecialtySearchDto> {

    private final MedicalSpecialtyRepository repository;
    private final DomainEventPublisher eventPublisher;

    @Override
    protected Specification<MedicalSpecialty> buildSpecification(MedicalSpecialtySearchDto searchDto) {
        return new MedicalSpecialtySpecification(searchDto);
    }

    @Override
    @Transactional
    public MedicalSpecialty create(MedicalSpecialty entity) {
        if (repository.existsByCode(entity.getCode().trim().toUpperCase())) {
            throw new BusinessException("Medical Specialty code '" + entity.getCode() + "' already exists in Global Catalog");
        }
        return super.create(entity);
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
        // No event defined for this catalog yet
    }
}
