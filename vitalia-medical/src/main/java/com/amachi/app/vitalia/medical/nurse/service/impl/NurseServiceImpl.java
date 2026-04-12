package com.amachi.app.vitalia.medical.nurse.service.impl;

import com.amachi.app.core.common.event.DomainEventPublisher;
import com.amachi.app.core.common.repository.CommonRepository;
import com.amachi.app.core.common.service.BaseService;
import com.amachi.app.vitalia.medical.nurse.dto.search.NurseSearchDto;
import com.amachi.app.vitalia.medical.nurse.entity.Nurse;
import com.amachi.app.vitalia.medical.nurse.repository.NurseRepository;
import com.amachi.app.vitalia.medical.nurse.service.NurseService;
import com.amachi.app.vitalia.medical.nurse.specification.NurseSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 * Standard implementation of Nurse Service using BaseService (SaaS Elite Tier).
 */
@Service
@RequiredArgsConstructor
public class NurseServiceImpl extends BaseService<Nurse, NurseSearchDto> implements NurseService {

    private final NurseRepository repository;
    private final DomainEventPublisher eventPublisher;

    @Override
    protected CommonRepository<Nurse, Long> getRepository() {
        return repository;
    }

    @Override
    protected Specification<Nurse> buildSpecification(NurseSearchDto searchDto) {
        return new NurseSpecification(searchDto);
    }

    @Override
    protected DomainEventPublisher getEventPublisher() {
        return eventPublisher;
    }

    @Override
    protected void publishCreatedEvent(Nurse entity) {
        // Publish created event
    }

    @Override
    protected void publishUpdatedEvent(Nurse entity) {
        // Publish updated event
    }
}
