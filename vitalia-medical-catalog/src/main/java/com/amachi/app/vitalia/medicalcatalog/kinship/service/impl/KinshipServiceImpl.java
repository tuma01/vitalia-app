package com.amachi.app.vitalia.medicalcatalog.kinship.service.impl;
 
import com.amachi.app.core.common.event.DomainEventPublisher;
import com.amachi.app.core.common.exception.BusinessException;

import com.amachi.app.core.common.service.BaseService;
import com.amachi.app.vitalia.medicalcatalog.kinship.dto.search.KinshipSearchDto;
import com.amachi.app.vitalia.medicalcatalog.kinship.entity.Kinship;
import com.amachi.app.vitalia.medicalcatalog.kinship.event.KinshipCreatedEvent;
import com.amachi.app.vitalia.medicalcatalog.kinship.repository.KinshipRepository;
import com.amachi.app.vitalia.medicalcatalog.kinship.specification.KinshipSpecification;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
 
/**
 * Implementation of Kinship Service following SaaS Elite Tier standards.
 * Pure Global Catalog (No Tenant Isolation).
 */
@Service
@RequiredArgsConstructor
@Getter
public class KinshipServiceImpl extends BaseService<Kinship, KinshipSearchDto> {
 
    private final KinshipRepository repository;
    private final DomainEventPublisher eventPublisher;
 
    @Override
    protected Specification<Kinship> buildSpecification(KinshipSearchDto searchDto) {
        return new KinshipSpecification(searchDto);
    }
 
    @Override
    @Transactional
    public Kinship create(Kinship entity) {
        if (repository.existsByCode(entity.getCode().trim().toUpperCase())) {
            throw new BusinessException("Kinship code '" + entity.getCode() + "' already exists in Global Catalog");
        }
        return super.create(entity);
    }
 
    @Override
    protected void publishCreatedEvent(Kinship entity) {
        eventPublisher.publish(new KinshipCreatedEvent(entity.getId(), entity.getCode(), entity.getName()));
    }
 
    @Override
    protected void publishUpdatedEvent(Kinship entity) {
        // En este catálogo solo se publica evento al crear
    }
}
