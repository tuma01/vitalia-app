package com.amachi.app.vitalia.medicalcatalog.vaccine.service.impl;
 
import com.amachi.app.core.common.event.DomainEventPublisher;
import com.amachi.app.core.common.exception.BusinessException;

import com.amachi.app.core.common.service.BaseService;
import com.amachi.app.vitalia.medicalcatalog.vaccine.dto.search.VaccineSearchDto;
import com.amachi.app.vitalia.medicalcatalog.vaccine.entity.Vaccine;
import com.amachi.app.vitalia.medicalcatalog.vaccine.event.VaccineCreatedEvent;
import com.amachi.app.vitalia.medicalcatalog.vaccine.repository.VaccineRepository;
import com.amachi.app.vitalia.medicalcatalog.vaccine.specification.VaccineSpecification;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
 
/**
 * Implementation of Vaccine Service following SaaS Elite Tier standards.
 * Pure Global Catalog (No Tenant Isolation).
 */
@Service
@RequiredArgsConstructor
@Getter
public class VaccineServiceImpl extends BaseService<Vaccine, VaccineSearchDto> {
 
    private final VaccineRepository repository;
    private final DomainEventPublisher eventPublisher;
 
    @Override
    protected Specification<Vaccine> buildSpecification(VaccineSearchDto searchDto) {
        return new VaccineSpecification(searchDto);
    }
 
    @Override
    @Transactional
    public Vaccine create(Vaccine entity) {
        if (repository.existsByCode(entity.getCode().trim().toUpperCase())) {
            throw new BusinessException("Vaccine code '" + entity.getCode() + "' already exists in Global Catalog");
        }
        return super.create(entity);
    }
 
    @Override
    protected void publishCreatedEvent(Vaccine entity) {
        eventPublisher.publish(new VaccineCreatedEvent(entity.getId(), entity.getCode(), entity.getName()));
    }
 
    @Override
    protected void publishUpdatedEvent(Vaccine entity) {
        // En este catálogo solo se publica evento al crear
    }
}
