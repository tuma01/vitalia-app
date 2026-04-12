package com.amachi.app.vitalia.medicalcatalog.bloodtype.service.impl;
 
import com.amachi.app.core.common.event.DomainEventPublisher;
import com.amachi.app.core.common.exception.BusinessException;

import com.amachi.app.core.common.service.BaseService;
import com.amachi.app.vitalia.medicalcatalog.bloodtype.dto.search.BloodTypeSearchDto;
import com.amachi.app.vitalia.medicalcatalog.bloodtype.entity.BloodType;
import com.amachi.app.vitalia.medicalcatalog.bloodtype.event.BloodTypeCreatedEvent;
import com.amachi.app.vitalia.medicalcatalog.bloodtype.repository.BloodTypeRepository;
import com.amachi.app.vitalia.medicalcatalog.bloodtype.specification.BloodTypeSpecification;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
 
/**
 * Implementation of BloodType Service following SaaS Elite Tier standards.
 * Pure Global Catalog (No Tenant Isolation).
 */
@Service
@RequiredArgsConstructor
@Getter
public class BloodTypeServiceImpl extends BaseService<BloodType, BloodTypeSearchDto> {
 
    private final BloodTypeRepository repository;
    private final DomainEventPublisher eventPublisher;
 
    @Override
    protected Specification<BloodType> buildSpecification(BloodTypeSearchDto searchDto) {
        return new BloodTypeSpecification(searchDto);
    }
 
    @Override
    @Transactional
    public BloodType create(BloodType entity) {
        if (repository.existsByCode(entity.getCode().trim().toUpperCase())) {
            throw new BusinessException("Blood type code '" + entity.getCode() + "' already exists in Global Catalog");
        }
        return super.create(entity);
    }
 
    @Override
    protected void publishCreatedEvent(BloodType entity) {
        eventPublisher.publish(new BloodTypeCreatedEvent(entity.getId(), entity.getCode(), entity.getName()));
    }
 
    @Override
    protected void publishUpdatedEvent(BloodType entity) {
        // En este catálogo solo se publica evento al crear
    }
}
