package com.amachi.app.core.geography.state.service.impl;

import com.amachi.app.core.common.event.DomainEventPublisher;
import com.amachi.app.core.common.exception.ResourceNotFoundException;
import com.amachi.app.core.common.repository.CommonRepository;
import com.amachi.app.core.common.service.BaseService;
import com.amachi.app.core.geography.country.entity.Country;
import com.amachi.app.core.geography.country.repository.CountryRepository;
import com.amachi.app.core.geography.state.dto.search.StateSearchDto;
import com.amachi.app.core.geography.state.entity.State;
import com.amachi.app.core.geography.state.event.StateCreatedEvent;
import com.amachi.app.core.geography.state.event.StateUpdatedEvent;
import com.amachi.app.core.geography.state.repository.StateRepository;
import com.amachi.app.core.geography.state.specification.StateSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.amachi.app.core.common.utils.AppConstants.ErrorMessages.ENTITY_MUST_NOT_BE_NULL;
import static com.amachi.app.core.common.utils.AppConstants.ErrorMessages.ID_MUST_NOT_BE_NULL;
import static java.util.Objects.requireNonNull;

@RequiredArgsConstructor
@Service
public class StateServiceImpl extends BaseService<State, StateSearchDto> {

    private final StateRepository stateRepository;
    private final CountryRepository countryRepository;
    private final DomainEventPublisher eventPublisher;

    @Override
    protected CommonRepository<State, Long> getRepository() {
        return stateRepository;
    }

    @Override
    protected Specification<State> buildSpecification(StateSearchDto searchDto) {
        return new StateSpecification(searchDto);
    }

    @Override
    protected DomainEventPublisher getEventPublisher() {
        return eventPublisher;
    }

    @Override
    @Transactional
    public State create(State entity) {
        requireNonNull(entity, ENTITY_MUST_NOT_BE_NULL);
        Long countryId = requireNonNull(entity.getCountry(), "Country must not be null").getId();
        
        Country country = countryRepository.findById(countryId)
                .orElseThrow(() -> new ResourceNotFoundException(Country.class.getName(), "error.resource.not.found", countryId));

        entity.setCountry(country);
        return super.create(entity);
    }

    @Override
    @Transactional
    public State update(Long id, State entity) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        requireNonNull(entity, ENTITY_MUST_NOT_BE_NULL);
        
        Long countryId = requireNonNull(entity.getCountry(), "Country must not be null").getId();
        Country country = countryRepository.findById(countryId)
                .orElseThrow(() -> new ResourceNotFoundException(Country.class.getName(), "error.resource.not.found", countryId));

        entity.setCountry(country);
        return super.update(id, entity);
    }

    @Override
    protected void publishCreatedEvent(State entity) {
        eventPublisher.publish(new StateCreatedEvent(entity.getId(), entity.getName()));
    }

    @Override
    protected void publishUpdatedEvent(State entity) {
        eventPublisher.publish(new StateUpdatedEvent(entity.getId(), entity.getName()));
    }
}
