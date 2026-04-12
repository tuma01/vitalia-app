package com.amachi.app.vitalia.medicalcatalog.demographic.service.impl;
 
import com.amachi.app.core.common.event.DomainEventPublisher;
import com.amachi.app.core.common.exception.BusinessException;

import com.amachi.app.core.common.service.BaseService;
import com.amachi.app.vitalia.medicalcatalog.demographic.dto.search.CivilStatusSearchDto;
import com.amachi.app.vitalia.medicalcatalog.demographic.entity.CivilStatus;
import com.amachi.app.vitalia.medicalcatalog.demographic.event.CivilStatusCreatedEvent;
import com.amachi.app.vitalia.medicalcatalog.demographic.repository.CivilStatusRepository;
import com.amachi.app.vitalia.medicalcatalog.demographic.specification.CivilStatusSpecification;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
 
/**
 * Implementation of CivilStatus Service following SaaS Elite Tier standards.
 * Pure Global Catalog (No Tenant Isolation).
 */
@Service
@RequiredArgsConstructor
@Getter
public class CivilStatusServiceImpl extends BaseService<CivilStatus, CivilStatusSearchDto> {
 
    private final CivilStatusRepository repository;
    private final DomainEventPublisher eventPublisher;
 
    @Override
    protected Specification<CivilStatus> buildSpecification(CivilStatusSearchDto searchDto) {
        return new CivilStatusSpecification(searchDto);
    }
 
    @Override
    @Transactional
    public CivilStatus create(CivilStatus entity) {
        if (repository.existsByCode(entity.getCode().trim().toUpperCase())) {
            throw new BusinessException("Civil status code '" + entity.getCode() + "' already exists in Global Catalog");
        }
        return super.create(entity);
    }
 
    @Override
    protected void publishCreatedEvent(CivilStatus entity) {
        eventPublisher.publish(new CivilStatusCreatedEvent(entity.getId(), entity.getCode(), entity.getName()));
    }
 
    @Override
    protected void publishUpdatedEvent(CivilStatus entity) {
        // En este catálogo solo se publica evento al crear
    }
}
