package com.amachi.app.vitalia.medical.professional.service.impl;

import com.amachi.app.core.common.event.DomainEventPublisher;
import com.amachi.app.core.common.repository.CommonRepository;
import com.amachi.app.core.common.service.BaseService;
import com.amachi.app.vitalia.medical.professional.dto.search.ProfessionalInfoSearchDto;
import com.amachi.app.vitalia.medical.professional.entity.ProfessionalInfo;
import com.amachi.app.vitalia.medical.professional.repository.ProfessionalInfoRepository;
import com.amachi.app.vitalia.medical.professional.service.ProfessionalInfoService;
import com.amachi.app.vitalia.medical.professional.specification.ProfessionalInfoSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.amachi.app.core.common.utils.AppConstants.ErrorMessages.ID_MUST_NOT_BE_NULL;
import static java.util.Objects.requireNonNull;

/**
 * Gestión táctica de trayectoria profesional y cargos.
 */
@Service
@RequiredArgsConstructor
public class ProfessionalInfoServiceImpl extends BaseService<ProfessionalInfo, ProfessionalInfoSearchDto> 
        implements ProfessionalInfoService {

    private final ProfessionalInfoRepository repository;
    private final DomainEventPublisher eventPublisher;

    @Override
    protected CommonRepository<ProfessionalInfo, Long> getRepository() {
        return repository;
    }

    @Override
    protected Specification<ProfessionalInfo> buildSpecification(ProfessionalInfoSearchDto searchDto) {
        return new ProfessionalInfoSpecification(searchDto);
    }

    @Override
    protected DomainEventPublisher getEventPublisher() {
        return eventPublisher;
    }

    @Override
    protected void publishCreatedEvent(ProfessionalInfo entity) {
        // Evento profesional creado
    }

    @Override
    protected void publishUpdatedEvent(ProfessionalInfo entity) {
        // Evento profesional actualizado
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProfessionalInfo> getByPersonId(Long personId) {
        requireNonNull(personId, ID_MUST_NOT_BE_NULL);
        return repository.findByPersonId(personId);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProfessionalInfo> getCurrentPosition(Long personId) {
        requireNonNull(personId, ID_MUST_NOT_BE_NULL);
        return repository.findByPersonIdAndIsCurrentTrue(personId);
    }
}
