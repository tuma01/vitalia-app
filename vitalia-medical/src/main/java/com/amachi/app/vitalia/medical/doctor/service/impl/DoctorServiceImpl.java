package com.amachi.app.vitalia.medical.doctor.service.impl;

import com.amachi.app.core.common.event.DomainEventPublisher;
import com.amachi.app.core.common.repository.CommonRepository;
import com.amachi.app.core.common.service.BaseService;
import com.amachi.app.vitalia.medical.doctor.dto.search.DoctorSearchDto;
import com.amachi.app.vitalia.medical.doctor.entity.Doctor;
import com.amachi.app.vitalia.medical.doctor.repository.DoctorRepository;
import com.amachi.app.vitalia.medical.doctor.service.DoctorService;
import com.amachi.app.vitalia.medical.doctor.specification.DoctorSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 * Standard implementation of Doctor Service using BaseService (SaaS Elite Tier).
 */
@Service
@RequiredArgsConstructor
public class DoctorServiceImpl extends BaseService<Doctor, DoctorSearchDto> implements DoctorService {

    private final DoctorRepository repository;
    private final DomainEventPublisher eventPublisher;

    @Override
    protected CommonRepository<Doctor, Long> getRepository() {
        return repository;
    }

    @Override
    protected Specification<Doctor> buildSpecification(DoctorSearchDto searchDto) {
        return new DoctorSpecification(searchDto);
    }

    @Override
    protected DomainEventPublisher getEventPublisher() {
        return eventPublisher;
    }

    @Override
    protected void publishCreatedEvent(Doctor entity) {
        // Publish created event
    }

    @Override
    protected void publishUpdatedEvent(Doctor entity) {
        // Publish updated event
    }
}
