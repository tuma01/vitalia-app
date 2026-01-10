package com.amachi.app.core.geography.departamento.service.impl;

import com.amachi.app.core.common.exception.ResourceNotFoundException;
import com.amachi.app.core.common.service.GenericService;
import com.amachi.app.core.geography.country.entity.Country;
import com.amachi.app.core.geography.country.repository.CountryRepository;
import com.amachi.app.core.geography.departamento.dto.search.DepartamentoSearchDto;
import com.amachi.app.core.geography.departamento.entity.Departamento;
import com.amachi.app.core.geography.departamento.repository.DepartamentoRepository;
import com.amachi.app.core.geography.departamento.specification.DepartamentoSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.amachi.app.core.common.utils.AppConstants.ErrorMessages.ENTITY_MUST_NOT_BE_NULL;
import static com.amachi.app.core.common.utils.AppConstants.ErrorMessages.ID_MUST_NOT_BE_NULL;
import static java.util.Objects.requireNonNull;

@RequiredArgsConstructor
@Service
public class DepartamentoServiceImpl implements GenericService<Departamento, DepartamentoSearchDto> {

    private final DepartamentoRepository departamentoRepository;
    private final CountryRepository countryRepository;

    @Override
    public List<Departamento> getAll() {
        return departamentoRepository.findAll();
    }


    @Override
    public Page<Departamento> getAll(DepartamentoSearchDto searchDto, Integer pageIndex, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize, Sort.by("createdDate").descending());
        Specification<Departamento> specification = new DepartamentoSpecification(searchDto);
        return departamentoRepository.findAll(specification, pageable);
    }

    @Transactional
    @Override
    public Departamento getById(Long id) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        return departamentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Departamento.class.getName(), "error.resource.not.found", id));
    }

    @Override
    public Departamento create(Departamento entity) {
        requireNonNull(entity, ENTITY_MUST_NOT_BE_NULL);

        Long countryId = requireNonNull(entity.getCountry(), ENTITY_MUST_NOT_BE_NULL).getId();
        requireNonNull(countryId, ID_MUST_NOT_BE_NULL);

        Country country = countryRepository.findById(countryId)
                .orElseThrow(() -> new ResourceNotFoundException(Country.class.getName(), "error.resource.not.found", countryId));

        entity.setCountry(country);
        return departamentoRepository.save(entity);
    }

    @Override
    public Departamento update(Long id, Departamento entity) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        requireNonNull(entity, ENTITY_MUST_NOT_BE_NULL);

        // Verificar existencia del país asociado
        Long countryId = requireNonNull(entity.getCountry(), ENTITY_MUST_NOT_BE_NULL).getId();
        requireNonNull(countryId, ID_MUST_NOT_BE_NULL);

        // Verificar existencia del departamento
        departamentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Departamento.class.getName(), "error.resource.not.found", id));

        Country country = countryRepository.findById(countryId)
                .orElseThrow(() -> new ResourceNotFoundException(Country.class.getName(), "error.resource.not.found", countryId));

        entity.setId(id);
        entity.setCountry(country);
        return departamentoRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        Departamento departamento = departamentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Departamento.class.getName(), "error.resource.not.found", id));
        departamentoRepository.delete(departamento);
    }
}
