package com.amachi.app.vitalia.country.service.impl;

import com.amachi.app.vitalia.common.exception.ResourceNotFoundException;
import com.amachi.app.vitalia.common.service.GenericService;
import com.amachi.app.vitalia.country.dto.search.CountrySearchDto;
import com.amachi.app.vitalia.country.entity.Country;
import com.amachi.app.vitalia.country.specification.CountrySpecification;
import com.amachi.app.vitalia.country.repository.CountryRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;

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
        Pageable pageable = PageRequest.of(pageIndex, pageSize, Sort.by("createdDate").descending());
        Specification<Country> specification = new CountrySpecification(searchDto);
        return countryRepository.findAll(specification, pageable);
    }

    @Override
    public Optional<Country> getById(Long id) {
        checkNotNull(id);
        Optional<Country> countryOptional = countryRepository.findById(id);
        if (countryOptional.isEmpty()) {
            throw new ResourceNotFoundException("error.resource.not.found", id);
        }
        return countryOptional;
    }

    @Override
    public Country create(Country entity) {
        checkNotNull(entity);
        return countryRepository.save(entity);
    }

    @Override
    public Country update(Long id, Country entity) {
        checkNotNull(id);
        if (countryRepository.findById(id).isEmpty()) {
            throw new ResourceNotFoundException("error.resource.not.found", id);
        }
        entity.setId(id);
        return countryRepository.save(entity);
    }

    @Override
    public boolean delete(Long id) {
        checkNotNull(id);
        return countryRepository.findById(id)
                .map(country -> {
                    countryRepository.delete(country);
                    return true;
                })
                .orElse(false);
    }
}
