package com.amachi.app.vitalia.medicalcatalog.diagnosis.service.impl;
 
import com.amachi.app.core.common.event.DomainEventPublisher;
import com.amachi.app.core.common.exception.BusinessException;

import com.amachi.app.core.common.service.BaseService;
import com.amachi.app.vitalia.medicalcatalog.diagnosis.dto.search.Icd10SearchDto;
import com.amachi.app.vitalia.medicalcatalog.diagnosis.entity.Icd10;
import com.amachi.app.vitalia.medicalcatalog.diagnosis.event.Icd10CreatedEvent;
import com.amachi.app.vitalia.medicalcatalog.diagnosis.repository.Icd10Repository;
import com.amachi.app.vitalia.medicalcatalog.diagnosis.specification.Icd10Specification;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
 
/**
 * Implementation of ICD-10 Service following SaaS Elite Tier standards.
 * Pure Global Catalog (No Tenant Isolation).
 */
@Service
@RequiredArgsConstructor
@Getter
public class Icd10ServiceImpl extends BaseService<Icd10, Icd10SearchDto> {
 
    private final Icd10Repository repository;
    private final DomainEventPublisher eventPublisher;
 
    @Override
    protected Specification<Icd10> buildSpecification(Icd10SearchDto searchDto) {
        return new Icd10Specification(searchDto);
    }
 
    @Override
    @Transactional
    public Icd10 create(Icd10 entity) {
        if (repository.existsByCode(entity.getCode().trim().toUpperCase())) {
            throw new BusinessException("ICD-10 code '" + entity.getCode() + "' already exists in Global Catalog");
        }
        return super.create(entity);
    }
 
    @Override
    protected void publishCreatedEvent(Icd10 entity) {
        eventPublisher.publish(new Icd10CreatedEvent(entity.getId(), entity.getCode(), entity.getDescription()));
    }
 
    @Override
    protected void publishUpdatedEvent(Icd10 entity) {
        // En este catálogo solo se publica evento al crear
    }
}
