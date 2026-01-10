package com.amachi.app.core.geography.country.service.impl;

import com.amachi.app.core.common.exception.ResourceNotFoundException;
import com.amachi.app.core.common.service.GenericService;
import com.amachi.app.core.geography.country.dto.search.CountrySearchDto;
import com.amachi.app.core.geography.country.entity.Country;
import com.amachi.app.core.geography.country.repository.CountryRepository;
import com.amachi.app.core.geography.country.specification.CountrySpecification;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.amachi.app.core.common.utils.AppConstants.ErrorMessages.ENTITY_MUST_NOT_BE_NULL;
import static com.amachi.app.core.common.utils.AppConstants.ErrorMessages.ID_MUST_NOT_BE_NULL;
import static java.util.Objects.requireNonNull;

@AllArgsConstructor
@Service
public class CountryServiceImpl implements GenericService<Country, CountrySearchDto> {

    CountryRepository countryRepository;

    @Override
    public List<Country> getAll() {
        return countryRepository.findAll();
    }

    @Override
    public Page<Country> getAll(CountrySearchDto searchDto, Integer pageIndex, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize, Sort.by(Sort.Direction.DESC,"createdDate"));
        Specification<Country> specification = new CountrySpecification(searchDto);
        return countryRepository.findAll(specification, pageable);
    }

    @Override
    public Country getById(Long id) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        return countryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Country.class.getName(), "error.resource.not.found", id));
    }

    @Override
    public Country create(Country entity) {
        requireNonNull(entity, ENTITY_MUST_NOT_BE_NULL);
        return countryRepository.save(entity);
    }

    @Override
    public Country update(Long id, Country entity) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        requireNonNull(entity, ENTITY_MUST_NOT_BE_NULL);
        // Verificar que el Country exista
        Country existing = countryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Country.class.getName(), "error.resource.not.found", id));
        entity.setId(id);
        return countryRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        Country country = countryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Country.class.getName(), "error.resource.not.found", id));
        countryRepository.delete(country);
    }
}
