package com.amachi.app.vitalia.medicalcatalog.procedure.service.impl;
 
import com.amachi.app.core.common.event.DomainEventPublisher;
import com.amachi.app.core.common.exception.BusinessException;

import com.amachi.app.core.common.service.BaseService;
import com.amachi.app.vitalia.medicalcatalog.procedure.dto.search.MedicalProcedureSearchDto;
import com.amachi.app.vitalia.medicalcatalog.procedure.entity.MedicalProcedure;
import com.amachi.app.vitalia.medicalcatalog.procedure.event.MedicalProcedureCreatedEvent;
import com.amachi.app.vitalia.medicalcatalog.procedure.repository.MedicalProcedureRepository;
import com.amachi.app.vitalia.medicalcatalog.procedure.specification.MedicalProcedureSpecification;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
 
/**
 * Implementation of MedicalProcedure Service following SaaS Elite Tier standards.
 * Pure Global Catalog (No Tenant Isolation).
 */
@Service
@RequiredArgsConstructor
@Getter
public class MedicalProcedureServiceImpl extends BaseService<MedicalProcedure, MedicalProcedureSearchDto> {
 
    private final MedicalProcedureRepository repository;
    private final DomainEventPublisher eventPublisher;
 
    @Override
    protected Specification<MedicalProcedure> buildSpecification(MedicalProcedureSearchDto searchDto) {
        return new MedicalProcedureSpecification(searchDto);
    }
 
    @Override
    @Transactional
    public MedicalProcedure create(MedicalProcedure entity) {
        if (repository.existsByCode(entity.getCode().trim().toUpperCase())) {
            throw new BusinessException("Medical procedure code '" + entity.getCode() + "' already exists in Global Catalog");
        }
        return super.create(entity);
    }
 
    @Override
    protected void publishCreatedEvent(MedicalProcedure entity) {
        eventPublisher.publish(new MedicalProcedureCreatedEvent(entity.getId(), entity.getCode(), entity.getName()));
    }
 
    @Override
    protected void publishUpdatedEvent(MedicalProcedure entity) {
        // En este catálogo solo se publica evento al crear
    }
}
