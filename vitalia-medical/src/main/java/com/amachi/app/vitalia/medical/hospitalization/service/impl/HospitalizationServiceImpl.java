package com.amachi.app.vitalia.medical.hospitalization.service.impl;

import com.amachi.app.core.common.enums.BedStatus;
import com.amachi.app.core.common.event.DomainEventPublisher;
import com.amachi.app.core.common.repository.CommonRepository;
import com.amachi.app.core.common.service.BaseService;
import com.amachi.app.vitalia.medical.hospitalization.dto.search.HospitalizationSearchDto;
import com.amachi.app.vitalia.medical.hospitalization.entity.Hospitalization;
import com.amachi.app.vitalia.medical.hospitalization.repository.HospitalizationRepository;
import com.amachi.app.vitalia.medical.hospitalization.service.HospitalizationService;
import com.amachi.app.vitalia.medical.hospitalization.specification.HospitalizationSpecification;
import com.amachi.app.vitalia.medical.infrastructure.service.BedService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static com.amachi.app.core.common.utils.AppConstants.ErrorMessages.ENTITY_MUST_NOT_BE_NULL;
import static com.amachi.app.core.common.utils.AppConstants.ErrorMessages.ID_MUST_NOT_BE_NULL;
import static java.util.Objects.requireNonNull;

/**
 * Implementation of Hospitalization Service (SaaS Elite Tier).
 */
@Service
@Transactional
public class HospitalizationServiceImpl extends BaseService<Hospitalization, HospitalizationSearchDto> 
        implements HospitalizationService {

    private final HospitalizationRepository repository;
    private final BedService bedService;
    private final DomainEventPublisher eventPublisher;

    public HospitalizationServiceImpl(HospitalizationRepository repository, BedService bedService, DomainEventPublisher eventPublisher) {
        this.repository = repository;
        this.bedService = bedService;
        this.eventPublisher = eventPublisher;
    }

    @Override
    protected CommonRepository<Hospitalization, Long> getRepository() {
        return repository;
    }

    @Override
    protected Specification<Hospitalization> buildSpecification(HospitalizationSearchDto searchDto) {
        return new HospitalizationSpecification(searchDto);
    }

    @Override
    protected DomainEventPublisher getEventPublisher() {
        return eventPublisher;
    }

    @Override
    protected void publishCreatedEvent(Hospitalization entity) {
    }

    @Override
    protected void publishUpdatedEvent(Hospitalization entity) {
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Hospitalization> getAll(HospitalizationSearchDto searchDto, Integer pageIndex, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize, Sort.by(Sort.Direction.DESC, "admissionDate"));
        return repository.findAll(buildSpecification(searchDto), pageable);
    }

    @Override
    @Transactional
    public Hospitalization create(Hospitalization entity) {
        requireNonNull(entity, ENTITY_MUST_NOT_BE_NULL);
        if (entity.getBed() != null) {
            bedService.updateBedStatus(entity.getBed().getId(), BedStatus.OCCUPIED);
        }
        return super.create(entity);
    }

    @Override
    @Transactional
    public Hospitalization update(Long id, Hospitalization entity) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        requireNonNull(entity, ENTITY_MUST_NOT_BE_NULL);
        Hospitalization existing = getById(id);
        
        if (existing.getBed() != null && (entity.getBed() == null || !existing.getBed().getId().equals(entity.getBed().getId()))) {
            bedService.updateBedStatus(existing.getBed().getId(), BedStatus.AVAILABLE);
        }
        if (entity.getBed() != null && (existing.getBed() == null || !existing.getBed().getId().equals(entity.getBed().getId()))) {
            bedService.updateBedStatus(entity.getBed().getId(), BedStatus.OCCUPIED);
        }

        return super.update(id, entity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Hospitalization hosp = getById(id);
        if (hosp.getBed() != null) {
            bedService.updateBedStatus(hosp.getBed().getId(), BedStatus.AVAILABLE);
        }
        super.delete(id);
    }

    @Override
    @Transactional
    public Hospitalization dischargePatient(Long id, String dischargeSummary) {
        Hospitalization hosp = getById(id);
        hosp.setDischargeDate(LocalDateTime.now());
        hosp.setStatus(com.amachi.app.vitalia.medical.common.enums.HospitalizationStatus.DISCHARGED);
        hosp.setDischargeReason(dischargeSummary);

        if (hosp.getBed() != null) {
            bedService.updateBedStatus(hosp.getBed().getId(), BedStatus.AVAILABLE);
        }

        return requireNonNull(repository.save(hosp));
    }
}
