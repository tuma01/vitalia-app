package com.amachi.app.vitalia.medicalcatalog.identity.service.impl;

import com.amachi.app.core.common.exception.ResourceNotFoundException;
import com.amachi.app.core.common.service.GenericService;
import com.amachi.app.vitalia.medicalcatalog.identity.dto.search.IdentificationTypeSearchDto;
import com.amachi.app.vitalia.medicalcatalog.identity.entity.IdentificationType;
import com.amachi.app.vitalia.medicalcatalog.identity.repository.IdentificationTypeRepository;
import com.amachi.app.vitalia.medicalcatalog.identity.specification.IdentificationTypeSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.amachi.app.core.common.utils.AppConstants.ErrorMessages.ENTITY_MUST_NOT_BE_NULL;
import static com.amachi.app.core.common.utils.AppConstants.ErrorMessages.ID_MUST_NOT_BE_NULL;
import static java.util.Objects.requireNonNull;

@RequiredArgsConstructor
@Service
@Transactional
public class IdentificationTypeServiceImpl implements GenericService<IdentificationType, IdentificationTypeSearchDto> {

    private final IdentificationTypeRepository repository;

    @Override
    @Transactional(readOnly = true)
    @NonNull
    public List<IdentificationType> getAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    @NonNull
    public Page<IdentificationType> getAll(@NonNull IdentificationTypeSearchDto searchDto, @NonNull Integer pageIndex,
            @NonNull Integer pageSize) {
        return repository.findAll(new IdentificationTypeSpecification(searchDto),
                PageRequest.of(pageIndex, pageSize, Sort.by("name")));
    }

    @Override
    @Transactional(readOnly = true)
    @NonNull
    public IdentificationType getById(@NonNull Long id) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(IdentificationType.class.getName(),
                        "error.resource.not.found", id));
    }

    @Override
    @NonNull
    public IdentificationType create(@NonNull IdentificationType entity) {
        requireNonNull(entity, ENTITY_MUST_NOT_BE_NULL);
        return requireNonNull(repository.save(entity));
    }

    @Override
    @NonNull
    public IdentificationType update(@NonNull Long id, @NonNull IdentificationType entity) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        requireNonNull(entity, ENTITY_MUST_NOT_BE_NULL);
        getById(id);
        entity.setId(id);
        return requireNonNull(repository.save(entity));
    }

    @Override
    public void delete(@NonNull Long id) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        repository.delete(getById(id));
    }
}
