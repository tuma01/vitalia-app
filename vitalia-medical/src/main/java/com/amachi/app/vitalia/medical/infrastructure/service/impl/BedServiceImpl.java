package com.amachi.app.vitalia.medical.infrastructure.service.impl;

import com.amachi.app.core.common.enums.BedStatus;
import com.amachi.app.core.common.event.DomainEventPublisher;
import com.amachi.app.core.common.repository.CommonRepository;
import com.amachi.app.core.common.service.BaseService;
import com.amachi.app.vitalia.medical.infrastructure.dto.search.BedSearchDto;
import com.amachi.app.vitalia.medical.infrastructure.entity.Bed;
import com.amachi.app.vitalia.medical.infrastructure.repository.BedRepository;
import com.amachi.app.vitalia.medical.infrastructure.service.BedService;
import com.amachi.app.vitalia.medical.infrastructure.specification.BedSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.util.Objects.requireNonNull;

/**
 * Enterprise implementation of Bed Service using BaseService (SaaS Elite Tier).
 */
@Service
@RequiredArgsConstructor
public class BedServiceImpl extends BaseService<Bed, BedSearchDto> implements BedService {

    private final BedRepository repository;
    private final DomainEventPublisher eventPublisher;

    @Override
    protected CommonRepository<Bed, Long> getRepository() {
        return repository;
    }

    @Override
    protected Specification<Bed> buildSpecification(BedSearchDto searchDto) {
        return new BedSpecification(searchDto);
    }

    @Override
    protected DomainEventPublisher getEventPublisher() {
        return eventPublisher;
    }

    @Override
    protected void publishCreatedEvent(Bed entity) {
        // Publish bed created
    }

    @Override
    protected void publishUpdatedEvent(Bed entity) {
        // Publish bed updated
    }

    @Override
    @Transactional
    public Bed updateBedStatus(Long id, BedStatus status) {
        Bed bed = getById(id);
        bed.setStatus(status);
        return requireNonNull(repository.save(bed));
    }
}
