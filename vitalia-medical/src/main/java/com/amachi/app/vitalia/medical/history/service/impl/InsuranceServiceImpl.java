package com.amachi.app.vitalia.medical.history.service.impl;

import com.amachi.app.core.common.event.DomainEventPublisher;
import com.amachi.app.core.common.repository.CommonRepository;
import com.amachi.app.core.common.service.BaseService;
import com.amachi.app.vitalia.medical.history.dto.search.InsuranceSearchDto;
import com.amachi.app.vitalia.medical.history.entity.Insurance;
import com.amachi.app.vitalia.medical.history.repository.InsuranceRepository;
import com.amachi.app.vitalia.medical.history.service.InsuranceService;
import com.amachi.app.vitalia.medical.history.specification.InsuranceSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.amachi.app.core.common.utils.AppConstants.ErrorMessages.ID_MUST_NOT_BE_NULL;
import static java.util.Objects.requireNonNull;

/**
 * Gestión táctica de coberturas y pólizas.
 */
@Service
@RequiredArgsConstructor
public class InsuranceServiceImpl extends BaseService<Insurance, InsuranceSearchDto> implements InsuranceService {

    private final InsuranceRepository repository;
    private final DomainEventPublisher eventPublisher;

    @Override
    protected CommonRepository<Insurance, Long> getRepository() {
        return repository;
    }

    @Override
    protected Specification<Insurance> buildSpecification(InsuranceSearchDto searchDto) {
        return new InsuranceSpecification(searchDto);
    }

    @Override
    protected DomainEventPublisher getEventPublisher() {
        return eventPublisher;
    }

    @Override
    protected void publishCreatedEvent(Insurance entity) {
        // Evento seguro creado
    }

    @Override
    protected void publishUpdatedEvent(Insurance entity) {
        // Evento seguro actualizado
    }

    @Override
    @Transactional(readOnly = true)
    public List<Insurance> getByMedicalHistoryId(Long medicalHistoryId) {
        requireNonNull(medicalHistoryId, ID_MUST_NOT_BE_NULL);
        return repository.findByMedicalHistoryId(medicalHistoryId);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Insurance> getActiveInsurance(Long medicalHistoryId) {
        requireNonNull(medicalHistoryId, ID_MUST_NOT_BE_NULL);
        return repository.findByMedicalHistoryIdAndIsCurrentTrue(medicalHistoryId);
    }
}
