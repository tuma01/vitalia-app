package com.amachi.app.core.geography.country.service.impl;

import com.amachi.app.core.common.event.DomainEventPublisher;
import com.amachi.app.core.common.repository.CommonRepository;
import com.amachi.app.core.common.service.BaseService;
import com.amachi.app.core.geography.country.dto.search.CountrySearchDto;
import com.amachi.app.core.geography.country.entity.Country;
import com.amachi.app.core.geography.country.event.CountryCreatedEvent;
import com.amachi.app.core.geography.country.event.CountryUpdatedEvent;
import com.amachi.app.core.geography.country.repository.CountryRepository;
import com.amachi.app.core.geography.country.specification.CountrySpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CountryServiceImpl extends BaseService<Country, CountrySearchDto> {

    private final CountryRepository countryRepository;
    private final DomainEventPublisher eventPublisher;

    @Override
    protected CommonRepository<Country, Long> getRepository() {
        return countryRepository;
    }

    @Override
    protected Specification<Country> buildSpecification(CountrySearchDto searchDto) {
        return new CountrySpecification(searchDto);
    }

    @Override
    protected DomainEventPublisher getEventPublisher() {
        return eventPublisher;
    }

    @Override
    protected void publishCreatedEvent(Country entity) {
        eventPublisher.publish(new CountryCreatedEvent(entity.getId(), entity.getName()));
    }

    @Override
    protected void publishUpdatedEvent(Country entity) {
        eventPublisher.publish(new CountryUpdatedEvent(entity.getId(), entity.getName()));
    }
}
