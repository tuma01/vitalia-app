package com.amachi.app.vitalia.medicalcatalog.demographic.service.impl;

import com.amachi.app.core.common.exception.ResourceNotFoundException;
import com.amachi.app.core.common.service.GenericService;
import com.amachi.app.vitalia.medicalcatalog.demographic.dto.search.CivilStatusSearchDto;
import com.amachi.app.vitalia.medicalcatalog.demographic.entity.CivilStatus;
import com.amachi.app.vitalia.medicalcatalog.demographic.repository.CivilStatusRepository;
import com.amachi.app.vitalia.medicalcatalog.demographic.specification.CivilStatusSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.amachi.app.core.common.utils.AppConstants.ErrorMessages.ENTITY_MUST_NOT_BE_NULL;
import static com.amachi.app.core.common.utils.AppConstants.ErrorMessages.ID_MUST_NOT_BE_NULL;
import static java.util.Objects.requireNonNull;

@Service
@RequiredArgsConstructor
@Transactional
public class CivilStatusServiceImpl implements GenericService<CivilStatus, CivilStatusSearchDto> {

    private final CivilStatusRepository repository;

    @Override
    @Transactional(readOnly = true)
    @NonNull
    public List<CivilStatus> getAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    @NonNull
    public Page<CivilStatus> getAll(@NonNull CivilStatusSearchDto searchDto, @NonNull Integer pageIndex,
            @NonNull Integer pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize, Sort.by(Sort.Direction.ASC, "name"));
        return repository.findAll(CivilStatusSpecification.withFilters(searchDto), pageable);
    }

    @Override
    @Transactional(readOnly = true)
    @NonNull
    public CivilStatus getById(@NonNull Long id) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(CivilStatus.class.getName(),
                        "error.resource.not.found", id));
    }

    @Override
    @NonNull
    public CivilStatus create(@NonNull CivilStatus entity) {
        requireNonNull(entity, ENTITY_MUST_NOT_BE_NULL);
        return requireNonNull(repository.save(entity));
    }

    @Override
    @NonNull
    public CivilStatus update(@NonNull Long id, @NonNull CivilStatus entity) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        requireNonNull(entity, ENTITY_MUST_NOT_BE_NULL);
        getById(id);
        entity.setId(id);
        return requireNonNull(repository.save(entity));
    }

    @Override
    public void delete(@NonNull Long id) {
        repository.delete(getById(id));
    }
}
