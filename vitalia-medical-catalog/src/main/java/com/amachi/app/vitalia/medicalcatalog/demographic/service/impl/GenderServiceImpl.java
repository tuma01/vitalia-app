package com.amachi.app.vitalia.medicalcatalog.demographic.service.impl;
 
import com.amachi.app.core.common.event.DomainEventPublisher;
import com.amachi.app.core.common.exception.BusinessException;

import com.amachi.app.core.common.service.BaseService;
import com.amachi.app.vitalia.medicalcatalog.demographic.dto.search.GenderSearchDto;
import com.amachi.app.vitalia.medicalcatalog.demographic.entity.Gender;
import com.amachi.app.vitalia.medicalcatalog.demographic.event.GenderCreatedEvent;
import com.amachi.app.vitalia.medicalcatalog.demographic.repository.GenderRepository;
import com.amachi.app.vitalia.medicalcatalog.demographic.specification.GenderSpecification;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
 
/**
 * Implementation of Gender Service following SaaS Elite Tier standards.
 * Pure Global Catalog (No Tenant Isolation).
 */
@Service
@RequiredArgsConstructor
@Getter
public class GenderServiceImpl extends BaseService<Gender, GenderSearchDto> {
 
    private final GenderRepository repository;
    private final DomainEventPublisher eventPublisher;
 
    @Override
    protected Specification<Gender> buildSpecification(GenderSearchDto searchDto) {
        return new GenderSpecification(searchDto);
    }
 
    @Override
    @Transactional
    public Gender create(Gender entity) {
        if (repository.existsByCode(entity.getCode().trim().toUpperCase())) {
            throw new BusinessException("Gender code '" + entity.getCode() + "' already exists in Global Catalog");
        }
        return super.create(entity);
    }
 
    @Override
    protected void publishCreatedEvent(Gender entity) {
        eventPublisher.publish(new GenderCreatedEvent(entity.getId(), entity.getCode(), entity.getName()));
    }
 
    @Override
    protected void publishUpdatedEvent(Gender entity) {
        // En este catálogo solo se publica evento al crear
    }
}
