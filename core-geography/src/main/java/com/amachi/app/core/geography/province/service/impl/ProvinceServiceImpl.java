package com.amachi.app.core.geography.province.service.impl;

import com.amachi.app.core.common.event.DomainEventPublisher;
import com.amachi.app.core.common.exception.ResourceNotFoundException;
import com.amachi.app.core.common.repository.CommonRepository;
import com.amachi.app.core.common.service.BaseService;
import com.amachi.app.core.geography.province.dto.search.ProvinceSearchDto;
import com.amachi.app.core.geography.province.entity.Province;
import com.amachi.app.core.geography.province.event.ProvinceCreatedEvent;
import com.amachi.app.core.geography.province.event.ProvinceUpdatedEvent;
import com.amachi.app.core.geography.province.repository.ProvinceRepository;
import com.amachi.app.core.geography.province.specification.ProvinceSpecification;
import com.amachi.app.core.geography.state.entity.State;
import com.amachi.app.core.geography.state.repository.StateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.amachi.app.core.common.utils.AppConstants.ErrorMessages.ENTITY_MUST_NOT_BE_NULL;
import static com.amachi.app.core.common.utils.AppConstants.ErrorMessages.ID_MUST_NOT_BE_NULL;
import static java.util.Objects.requireNonNull;

@RequiredArgsConstructor
@Service
public class ProvinceServiceImpl extends BaseService<Province, ProvinceSearchDto> {

    private final ProvinceRepository provinceRepository;
    private final StateRepository stateRepository;
    private final DomainEventPublisher eventPublisher;

    @Override
    protected CommonRepository<Province, Long> getRepository() {
        return provinceRepository;
    }

    @Override
    protected Specification<Province> buildSpecification(ProvinceSearchDto searchDto) {
        return new ProvinceSpecification(searchDto);
    }

    @Override
    protected DomainEventPublisher getEventPublisher() {
        return eventPublisher;
    }

    @Override
    @Transactional
    public Province create(Province entity) {
        requireNonNull(entity, ENTITY_MUST_NOT_BE_NULL);
        Long stateId = requireNonNull(entity.getState(), "State must not be null").getId();
        
        State state = stateRepository.findById(stateId)
                .orElseThrow(() -> new ResourceNotFoundException(State.class.getName(), "error.resource.not.found", stateId));

        entity.setState(state);
        return super.create(entity);
    }

    @Override
    @Transactional
    public Province update(Long id, Province entity) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        requireNonNull(entity, ENTITY_MUST_NOT_BE_NULL);
        
        Long stateId = requireNonNull(entity.getState(), "State must not be null").getId();
        State state = stateRepository.findById(stateId)
                .orElseThrow(() -> new ResourceNotFoundException(State.class.getName(), "error.resource.not.found", stateId));

        entity.setState(state);
        return super.update(id, entity);
    }

    @Override
    protected void publishCreatedEvent(Province entity) {
        eventPublisher.publish(new ProvinceCreatedEvent(entity.getId(), entity.getName()));
    }

    @Override
    protected void publishUpdatedEvent(Province entity) {
        eventPublisher.publish(new ProvinceUpdatedEvent(entity.getId(), entity.getName()));
    }
}
