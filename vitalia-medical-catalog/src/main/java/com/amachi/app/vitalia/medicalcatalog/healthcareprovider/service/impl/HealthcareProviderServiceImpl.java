package com.amachi.app.vitalia.medicalcatalog.healthcareprovider.service.impl;

import com.amachi.app.core.common.exception.ResourceNotFoundException;
import com.amachi.app.core.common.service.GenericService;
import com.amachi.app.vitalia.medicalcatalog.healthcareprovider.dto.search.HealthcareProviderSearchDto;
import com.amachi.app.vitalia.medicalcatalog.healthcareprovider.entity.HealthcareProvider;
import com.amachi.app.vitalia.medicalcatalog.healthcareprovider.repository.HealthcareProviderRepository;
import com.amachi.app.vitalia.medicalcatalog.healthcareprovider.specification.HealthcareProviderSpecification;
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
public class HealthcareProviderServiceImpl implements GenericService<HealthcareProvider, HealthcareProviderSearchDto> {

    HealthcareProviderRepository healthcareProviderRepository;

    @Override
    public List<HealthcareProvider> getAll() {
        return healthcareProviderRepository.findAll();
    }

    @Override
    public Page<HealthcareProvider> getAll(HealthcareProviderSearchDto searchDto, Integer pageIndex, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize, Sort.by(Sort.Direction.DESC, "createdDate"));
        Specification<HealthcareProvider> specification = new HealthcareProviderSpecification(searchDto);
        return healthcareProviderRepository.findAll(specification, pageable);
    }

    @Override
    public HealthcareProvider getById(Long id) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        return healthcareProviderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(HealthcareProvider.class.getName(),
                        "error.resource.not.found", id));
    }

    @Override
    public HealthcareProvider create(HealthcareProvider entity) {
        requireNonNull(entity, ENTITY_MUST_NOT_BE_NULL);
        return requireNonNull(healthcareProviderRepository.save(entity));
    }

    @Override
    public HealthcareProvider update(Long id, HealthcareProvider entity) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        requireNonNull(entity, ENTITY_MUST_NOT_BE_NULL);
        healthcareProviderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(HealthcareProvider.class.getName(),
                        "error.resource.not.found", id));
        entity.setId(id);
        return requireNonNull(healthcareProviderRepository.save(entity));
    }

    @Override
    public void delete(Long id) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        HealthcareProvider healthcareProvider = healthcareProviderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(HealthcareProvider.class.getName(),
                        "error.resource.not.found", id));
        healthcareProviderRepository.delete(healthcareProvider);
    }
}
