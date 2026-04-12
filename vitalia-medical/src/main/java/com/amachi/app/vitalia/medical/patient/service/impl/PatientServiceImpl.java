package com.amachi.app.vitalia.medical.patient.service.impl;

import com.amachi.app.core.common.event.DomainEventPublisher;
import com.amachi.app.core.common.service.BaseService;
import com.amachi.app.core.common.repository.CommonRepository;
import com.amachi.app.vitalia.medical.patient.dto.search.PatientSearchDto;
import com.amachi.app.vitalia.medical.patient.entity.Patient;
import com.amachi.app.vitalia.medical.patient.repository.PatientRepository;
import com.amachi.app.vitalia.medical.patient.service.PatientService;
import com.amachi.app.vitalia.medical.patient.specification.PatientSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 * Enterprise implementation of Patient Service (SaaS Elite Tier).
 * Standardized using BaseService for automatic isolation and audit.
 */
@Service
@RequiredArgsConstructor
public class PatientServiceImpl extends BaseService<Patient, PatientSearchDto>
        implements PatientService {

    private final PatientRepository repository;
    private final DomainEventPublisher eventPublisher;

    @Override
    protected CommonRepository<Patient, Long> getRepository() {
        return repository;
    }

    @Override
    protected Specification<Patient> buildSpecification(PatientSearchDto searchDto) {
        return new PatientSpecification(searchDto);
    }

    @Override
    protected void publishCreatedEvent(Patient entity) {
        // Evento paciente creado
    }

    @Override
    protected void publishUpdatedEvent(Patient entity) {
        // Evento paciente actualizado
    }

    @Override
    protected DomainEventPublisher getEventPublisher() {
        return eventPublisher;
    }

    // Custom business logic can be added here...
}
