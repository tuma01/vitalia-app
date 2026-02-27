package com.amachi.app.vitalia.medicalcatalog.kinship.service.impl;

import com.amachi.app.core.common.exception.ResourceNotFoundException;
import com.amachi.app.core.common.service.GenericService;
import com.amachi.app.vitalia.medicalcatalog.kinship.dto.search.KinshipSearchDto;
import com.amachi.app.vitalia.medicalcatalog.kinship.entity.Kinship;
import com.amachi.app.vitalia.medicalcatalog.kinship.repository.KinshipRepository;
import com.amachi.app.vitalia.medicalcatalog.kinship.specification.KinshipSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
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
public class KinshipServiceImpl implements GenericService<Kinship, KinshipSearchDto> {
    private final KinshipRepository repository;

    @Override
    @Transactional(readOnly = true)
    @NonNull
    public List<Kinship> getAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    @NonNull
    public Page<Kinship> getAll(@NonNull KinshipSearchDto searchDto, @NonNull Integer pageIndex,
            @NonNull Integer pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize, Sort.by(Sort.Direction.ASC, "name"));
        Specification<Kinship> specification = new KinshipSpecification(searchDto);
        return repository.findAll(specification, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    @NonNull
    public Kinship getById(@NonNull Long id) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        return repository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(Kinship.class.getName(), "error.resource.not.found", id));
    }

    @Override
    @NonNull
    public Kinship create(@NonNull Kinship entity) {
        requireNonNull(entity, ENTITY_MUST_NOT_BE_NULL);
        return requireNonNull(repository.save(entity));
    }

    @Override
    @NonNull
    public Kinship update(@NonNull Long id, @NonNull Kinship entity) {
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
