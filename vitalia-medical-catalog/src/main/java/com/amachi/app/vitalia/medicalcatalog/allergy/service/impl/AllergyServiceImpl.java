package com.amachi.app.vitalia.medicalcatalog.allergy.service.impl;
 
import com.amachi.app.core.common.event.DomainEventPublisher;
import com.amachi.app.core.common.exception.BusinessException;

import com.amachi.app.core.common.service.BaseService;
import com.amachi.app.vitalia.medicalcatalog.allergy.dto.search.AllergySearchDto;
import com.amachi.app.vitalia.medicalcatalog.allergy.entity.Allergy;
import com.amachi.app.vitalia.medicalcatalog.allergy.event.AllergyCreatedEvent;
import com.amachi.app.vitalia.medicalcatalog.allergy.repository.AllergyRepository;
import com.amachi.app.vitalia.medicalcatalog.allergy.specification.AllergySpecification;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
 
/**
 * Implementation of Allergy Service following SaaS Elite Tier standards.
 */
@Service
@RequiredArgsConstructor
@Getter
public class AllergyServiceImpl extends BaseService<Allergy, AllergySearchDto> {
 
    private final AllergyRepository repository;
    private final DomainEventPublisher eventPublisher;
 
    @Override
    protected Specification<Allergy> buildSpecification(AllergySearchDto searchDto) {
        return new AllergySpecification(searchDto);
    }
 
    @Override
    @Transactional
    public Allergy create(Allergy entity) {
        if (repository.existsByCode(entity.getCode().trim().toUpperCase())) {
            throw new BusinessException("Allergy code '" + entity.getCode() + "' already exists in Global Catalog");
        }
        return super.create(entity);
    }
 
    @Override
    protected void publishCreatedEvent(Allergy entity) {
        eventPublisher.publish(new AllergyCreatedEvent(entity.getId(), entity.getCode(), entity.getName()));
    }
 
    @Override
    protected void publishUpdatedEvent(Allergy entity) {
        // En este catálogo solo se publica evento al crear, pero el estándar pide implementar el hook
    }
}
