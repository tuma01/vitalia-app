package com.amachi.app.vitalia.medicalcatalog.allergy.service.impl;

import com.amachi.app.core.common.exception.ResourceNotFoundException;
import com.amachi.app.core.common.service.GenericService;
import com.amachi.app.vitalia.medicalcatalog.allergy.dto.search.AllergySearchDto;
import com.amachi.app.vitalia.medicalcatalog.allergy.entity.Allergy;
import com.amachi.app.vitalia.medicalcatalog.allergy.repository.AllergyRepository;
import com.amachi.app.vitalia.medicalcatalog.allergy.specification.AllergySpecification;
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
public class AllergyServiceImpl implements GenericService<Allergy, AllergySearchDto> {
    private final AllergyRepository repository;

    @Override
    @Transactional(readOnly = true)
    @NonNull
    public List<Allergy> getAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    @NonNull
    public Page<Allergy> getAll(@NonNull AllergySearchDto searchDto, @NonNull Integer pageIndex,
            @NonNull Integer pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize, Sort.by(Sort.Direction.ASC, "name"));
        Specification<Allergy> specification = new AllergySpecification(searchDto);
        return repository.findAll(specification, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    @NonNull
    public Allergy getById(@NonNull Long id) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        return repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(Allergy.class.getName(), "error.resource.not.found", id));
    }

    @Override
    @NonNull
    public Allergy create(@NonNull Allergy entity) {
        requireNonNull(entity, ENTITY_MUST_NOT_BE_NULL);
        return repository.save(entity);
    }

    @Override
    @NonNull
    public Allergy update(@NonNull Long id, @NonNull Allergy entity) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        requireNonNull(entity, ENTITY_MUST_NOT_BE_NULL);
        repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(Allergy.class.getName(), "error.resource.not.found", id));
        entity.setId(id);
        return repository.save(entity);
    }

    @Override
    public void delete(@NonNull Long id) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        Allergy entity = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(Allergy.class.getName(), "error.resource.not.found", id));
        repository.delete(entity);
    }
}
