package com.amachi.app.vitalia.medical.history.service.impl;

import com.amachi.app.core.common.context.TenantContext;
import com.amachi.app.core.common.event.DomainEventPublisher;
import com.amachi.app.core.common.exception.BusinessException;
import com.amachi.app.core.common.repository.CommonRepository;
import com.amachi.app.core.common.service.BaseService;
import com.amachi.app.vitalia.medical.history.dto.search.MedicalHistorySearchDto;
import com.amachi.app.vitalia.medical.history.entity.MedicalHistory;
import com.amachi.app.vitalia.medical.history.event.MedicalHistoryCreatedEvent;
import com.amachi.app.vitalia.medical.history.repository.MedicalHistoryRepository;
import com.amachi.app.vitalia.medical.history.service.MedicalHistoryService;
import com.amachi.app.vitalia.medical.history.specification.MedicalHistorySpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.amachi.app.core.common.utils.AppConstants.ErrorMessages.ID_MUST_NOT_BE_NULL;
import static java.util.Objects.requireNonNull;

/**
 * Enterprise implementation of Medical History Service (SaaS Elite Tier).
 * Standardized using Core-Geography "Enterprise-Grade" Pattern.
 */
@Service
@RequiredArgsConstructor
public class MedicalHistoryServiceImpl extends BaseService<MedicalHistory, MedicalHistorySearchDto> 
        implements MedicalHistoryService {

    private final MedicalHistoryRepository repository;
    private final DomainEventPublisher eventPublisher;

    @Override
    protected CommonRepository<MedicalHistory, Long> getRepository() {
        return repository;
    }

    @Override
    protected Specification<MedicalHistory> buildSpecification(MedicalHistorySearchDto searchDto) {
        return new MedicalHistorySpecification(searchDto);
    }

    @Override
    protected DomainEventPublisher getEventPublisher() {
        return eventPublisher;
    }

    @Override
    protected void publishCreatedEvent(MedicalHistory entity) {
        eventPublisher.publish(new MedicalHistoryCreatedEvent(
            entity.getId(),
            entity.getHistoryNumber(),
            entity.getPatient().getId()
        ));
    }

    @Override
    protected void publishUpdatedEvent(MedicalHistory entity) {
        // Publish history updated logic if applicable
    }

    @Override
    @Transactional
    public MedicalHistory create(MedicalHistory entity) {
        // ✅ Geographic Standard: Multi-tenant uniqueness check
        boolean exists = repository.existsByHistoryNumberAndTenantId(
                entity.getHistoryNumber().trim().toUpperCase(),
                TenantContext.getTenant()
        );

        if (exists) {
            throw new BusinessException("Medical History Number '" + entity.getHistoryNumber() + "' already exists for this tenant");
        }

        return super.create(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MedicalHistory> getByPatientId(Long patientId) {
        requireNonNull(patientId, ID_MUST_NOT_BE_NULL);
        return repository.findByPatientIdAndTenantId(patientId, TenantContext.getTenant());
    }
}
