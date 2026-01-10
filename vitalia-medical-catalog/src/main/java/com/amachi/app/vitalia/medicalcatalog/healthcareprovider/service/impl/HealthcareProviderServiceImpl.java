package com.amachi.app.vitalia.medicalcatalog.healthcareprovider.service.impl;

import com.amachi.app.core.common.exception.ResourceNotFoundException;
import com.amachi.app.core.common.service.GenericService;
import com.amachi.app.vitalia.medicalcatalog.healthcareprovider.dto.search.HealthcareProviderSearchDto;
import com.amachi.app.vitalia.medicalcatalog.healthcareprovider.entity.HealthcareProvider;
import com.amachi.app.vitalia.medicalcatalog.healthcareprovider.repository.HealthcareProviderRepository;
import com.amachi.app.vitalia.medicalcatalog.healthcareprovider.specification.HealthcareProviderSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.amachi.app.core.common.utils.AppConstants.ErrorMessages.ENTITY_MUST_NOT_BE_NULL;
import static com.amachi.app.core.common.utils.AppConstants.ErrorMessages.ID_MUST_NOT_BE_NULL;
import static java.util.Objects.requireNonNull;

@RequiredArgsConstructor
@Service
@Transactional
public class HealthcareProviderServiceImpl implements GenericService<HealthcareProvider, HealthcareProviderSearchDto> {

    private final HealthcareProviderRepository repository;

    @Override
    @Transactional(readOnly = true)
    @NonNull
    public List<HealthcareProvider> getAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    @NonNull
    public Page<HealthcareProvider> getAll(@NonNull HealthcareProviderSearchDto searchDto, @NonNull Integer pageIndex,
            @NonNull Integer pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize, Sort.by(Sort.Direction.DESC, "createdDate"));
        Specification<HealthcareProvider> specification = new HealthcareProviderSpecification(searchDto);
        return repository.findAll(specification, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    @NonNull
    public HealthcareProvider getById(@NonNull Long id) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(HealthcareProvider.class.getName(),
                        "error.resource.not.found", id));
    }

    @Override
    @NonNull
    public HealthcareProvider create(@NonNull HealthcareProvider entity) {
        requireNonNull(entity, ENTITY_MUST_NOT_BE_NULL);
        return repository.save(entity);
    }

    @Override
    @NonNull
    public HealthcareProvider update(@NonNull Long id, @NonNull HealthcareProvider entity) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        requireNonNull(entity, ENTITY_MUST_NOT_BE_NULL);
        repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(HealthcareProvider.class.getName(),
                        "error.resource.not.found", id));
        entity.setId(id);
        return repository.save(entity);
    }

    @Override
    public void delete(@NonNull Long id) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        HealthcareProvider entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(HealthcareProvider.class.getName(),
                        "error.resource.not.found", id));
        repository.delete(entity);
    }
}
