package com.amachi.app.core.geography.address.service.impl;

import com.amachi.app.core.common.event.DomainEventPublisher;
import com.amachi.app.core.common.exception.ResourceNotFoundException;
import com.amachi.app.core.common.repository.CommonRepository;
import com.amachi.app.core.common.service.BaseService;
import com.amachi.app.core.geography.address.dto.search.AddressSearchDto;
import com.amachi.app.core.geography.address.entity.Address;
import com.amachi.app.core.geography.address.event.AddressCreatedEvent;
import com.amachi.app.core.geography.address.event.AddressUpdatedEvent;
import com.amachi.app.core.geography.address.repository.AddressRepository;
import com.amachi.app.core.geography.address.specification.AddressSpecification;
import com.amachi.app.core.geography.country.entity.Country;
import com.amachi.app.core.geography.country.repository.CountryRepository;
import com.amachi.app.core.geography.municipality.entity.Municipality;
import com.amachi.app.core.geography.municipality.repository.MunicipalityRepository;
import com.amachi.app.core.geography.province.entity.Province;
import com.amachi.app.core.geography.province.repository.ProvinceRepository;
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
public class AddressServiceImpl extends BaseService<Address, AddressSearchDto> {

    private final AddressRepository addressRepository;
    private final CountryRepository countryRepository;
    private final StateRepository stateRepository;
    private final ProvinceRepository provinceRepository;
    private final MunicipalityRepository municipalityRepository;
    private final DomainEventPublisher eventPublisher;

    @Override
    protected CommonRepository<Address, Long> getRepository() {
        return addressRepository;
    }

    @Override
    protected Specification<Address> buildSpecification(AddressSearchDto searchDto) {
        return new AddressSpecification(searchDto);
    }

    @Override
    protected DomainEventPublisher getEventPublisher() {
        return eventPublisher;
    }

    @Override
    @Transactional
    public Address create(Address entity) {
        requireNonNull(entity, ENTITY_MUST_NOT_BE_NULL);
        validateRelationships(entity);
        return super.create(entity);
    }

    @Override
    @Transactional
    public Address update(Long id, Address entity) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        requireNonNull(entity, ENTITY_MUST_NOT_BE_NULL);
        validateRelationships(entity);
        return super.update(id, entity);
    }

    private void validateRelationships(Address entity) {
        if (entity.getCountry() != null && entity.getCountry().getId() != null) {
            Country country = countryRepository.findById(entity.getCountry().getId())
                    .orElseThrow(() -> new ResourceNotFoundException(Country.class.getName(), "error.resource.not.found", entity.getCountry().getId()));
            entity.setCountry(country);
        }
        if (entity.getState() != null && entity.getState().getId() != null) {
            State state = stateRepository.findById(entity.getState().getId())
                    .orElseThrow(() -> new ResourceNotFoundException(State.class.getName(), "error.resource.not.found", entity.getState().getId()));
            entity.setState(state);
        }
        if (entity.getProvince() != null && entity.getProvince().getId() != null) {
            Province province = provinceRepository.findById(entity.getProvince().getId())
                    .orElseThrow(() -> new ResourceNotFoundException(Province.class.getName(), "error.resource.not.found", entity.getProvince().getId()));
            entity.setProvince(province);
        }
        if (entity.getMunicipality() != null && entity.getMunicipality().getId() != null) {
            Municipality municipality = municipalityRepository.findById(entity.getMunicipality().getId())
                    .orElseThrow(() -> new ResourceNotFoundException(Municipality.class.getName(), "error.resource.not.found", entity.getMunicipality().getId()));
            entity.setMunicipality(municipality);
        }
    }

    @Override
    protected void publishCreatedEvent(Address entity) {
        eventPublisher.publish(new AddressCreatedEvent(entity.getId(), entity.getCity()));
    }

    @Override
    protected void publishUpdatedEvent(Address entity) {
        eventPublisher.publish(new AddressUpdatedEvent(entity.getId(), entity.getCity()));
    }
}
