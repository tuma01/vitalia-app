package com.amachi.app.vitalia.medical.consultation.service.impl;

import com.amachi.app.core.common.event.DomainEventPublisher;
import com.amachi.app.core.common.repository.CommonRepository;
import com.amachi.app.core.common.service.BaseService;
import com.amachi.app.vitalia.medical.consultation.dto.search.ConsultationSearchDto;
import com.amachi.app.vitalia.medical.consultation.entity.Consultation;
import com.amachi.app.vitalia.medical.consultation.repository.ConsultationRepository;
import com.amachi.app.vitalia.medical.consultation.service.ConsultationService;
import com.amachi.app.vitalia.medical.consultation.specification.ConsultationSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.amachi.app.core.common.utils.AppConstants.ErrorMessages.ID_MUST_NOT_BE_NULL;
import static java.util.Objects.requireNonNull;

/**
 * Servicio de Consultas Médicas (SaaS Elite Tier).
 */
@Service
@RequiredArgsConstructor
@Transactional
public class ConsultationServiceImpl extends BaseService<Consultation, ConsultationSearchDto> 
        implements ConsultationService {

    private final ConsultationRepository repository;
    private final DomainEventPublisher eventPublisher;

    @Override
    protected CommonRepository<Consultation, Long> getRepository() {
        return repository;
    }

    @Override
    protected Specification<Consultation> buildSpecification(ConsultationSearchDto searchDto) {
        return new ConsultationSpecification(searchDto);
    }

    @Override
    protected DomainEventPublisher getEventPublisher() {
        return eventPublisher;
    }

    @Override
    protected void publishCreatedEvent(Consultation entity) {
        if (eventPublisher != null) {
            // eventPublisher.publish(new ConsultationCreatedEvent(entity));
        }
    }

    @Override
    protected void publishUpdatedEvent(Consultation entity) {
        if (eventPublisher != null) {
            // eventPublisher.publish(new ConsultationUpdatedEvent(entity));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Consultation> getByMedicalHistoryId(Long medicalHistoryId) {
        requireNonNull(medicalHistoryId, ID_MUST_NOT_BE_NULL);
        // El aislamiento multi-tenant aquí se garantiza vía el uso de findByMedicalHistoryId 
        // asumiendo que el repositorio hereda de CommonRepository y tiene filtros activos.
        return repository.findByMedicalHistoryId(medicalHistoryId);
    }
}
