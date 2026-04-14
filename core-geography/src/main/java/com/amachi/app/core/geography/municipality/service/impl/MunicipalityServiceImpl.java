package com.amachi.app.core.geography.municipality.service.impl;

import com.amachi.app.core.common.event.DomainEventPublisher;
import com.amachi.app.core.common.exception.ResourceNotFoundException;
import com.amachi.app.core.common.repository.CommonRepository;
import com.amachi.app.core.common.service.BaseService;
import com.amachi.app.core.geography.municipality.dto.search.MunicipalitySearchDto;
import com.amachi.app.core.geography.municipality.entity.Municipality;
import com.amachi.app.core.geography.municipality.event.MunicipalityCreatedEvent;
import com.amachi.app.core.geography.municipality.event.MunicipalityUpdatedEvent;
import com.amachi.app.core.geography.municipality.repository.MunicipalityRepository;
import com.amachi.app.core.geography.municipality.specification.MunicipalitySpecification;
import com.amachi.app.core.geography.province.entity.Province;
import com.amachi.app.core.geography.province.repository.ProvinceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.amachi.app.core.common.utils.AppConstants.ErrorMessages.ENTITY_MUST_NOT_BE_NULL;
import static com.amachi.app.core.common.utils.AppConstants.ErrorMessages.ID_MUST_NOT_BE_NULL;
import static java.util.Objects.requireNonNull;

@RequiredArgsConstructor
@Service
public class MunicipalityServiceImpl extends BaseService<Municipality, MunicipalitySearchDto> {

    private final MunicipalityRepository municipalityRepository;
    private final ProvinceRepository provinceRepository;
    private final DomainEventPublisher eventPublisher;

    @Override
    protected CommonRepository<Municipality, Long> getRepository() {
        return municipalityRepository;
    }

    @Override
    protected Specification<Municipality> buildSpecification(MunicipalitySearchDto searchDto) {
        return new MunicipalitySpecification(searchDto);
    }

    @Override
    protected DomainEventPublisher getEventPublisher() {
        return eventPublisher;
    }

    @Override
    @Transactional
    public Municipality create(Municipality entity) {
        requireNonNull(entity, ENTITY_MUST_NOT_BE_NULL);
        Long provinceId = requireNonNull(entity.getProvince(), "Province must not be null").getId();
        
        Province province = provinceRepository.findById(provinceId)
                .orElseThrow(() -> new ResourceNotFoundException(Province.class.getName(), "error.resource.not.found", provinceId));

        entity.setProvince(province);
        return super.create(entity);
    }

    @Override
    @Transactional
    public Municipality update(Long id, Municipality entity) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        requireNonNull(entity, ENTITY_MUST_NOT_BE_NULL);
        
        Long provinceId = requireNonNull(entity.getProvince(), "Province must not be null").getId();
        Province province = provinceRepository.findById(provinceId)
                .orElseThrow(() -> new ResourceNotFoundException(Province.class.getName(), "error.resource.not.found", provinceId));

        entity.setProvince(province);
        return super.update(id, entity);
    }

    @Override
    protected void publishCreatedEvent(Municipality entity) {
        eventPublisher.publish(new MunicipalityCreatedEvent(entity.getId(), entity.getName()));
    }

    @Override
    protected void publishUpdatedEvent(Municipality entity) {
        eventPublisher.publish(new MunicipalityUpdatedEvent(entity.getId(), entity.getName()));
    }
}
