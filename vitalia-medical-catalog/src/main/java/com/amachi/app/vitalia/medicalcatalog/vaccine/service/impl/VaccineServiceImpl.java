package com.amachi.app.vitalia.medicalcatalog.vaccine.service.impl;

import com.amachi.app.core.common.exception.ResourceNotFoundException;
import com.amachi.app.core.common.service.GenericService;
import com.amachi.app.vitalia.medicalcatalog.vaccine.dto.search.VaccineSearchDto;
import com.amachi.app.vitalia.medicalcatalog.vaccine.entity.Vaccine;
import com.amachi.app.vitalia.medicalcatalog.vaccine.repository.VaccineRepository;
import com.amachi.app.vitalia.medicalcatalog.vaccine.specification.VaccineSpecification;
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
public class VaccineServiceImpl implements GenericService<Vaccine, VaccineSearchDto> {

    private final VaccineRepository repository;

    @Override
    @Transactional(readOnly = true)
    @NonNull
    public List<Vaccine> getAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    @NonNull
    public Page<Vaccine> getAll(@NonNull VaccineSearchDto searchDto, @NonNull Integer pageIndex,
            @NonNull Integer pageSize) {
        return repository.findAll(new VaccineSpecification(searchDto),
                PageRequest.of(pageIndex, pageSize, Sort.by("name")));
    }

    @Override
    @Transactional(readOnly = true)
    @NonNull
    public Vaccine getById(@NonNull Long id) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        return repository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(Vaccine.class.getName(), "error.resource.not.found", id));
    }

    @Override
    @NonNull
    public Vaccine create(@NonNull Vaccine entity) {
        requireNonNull(entity, ENTITY_MUST_NOT_BE_NULL);
        return repository.save(entity);
    }

    @Override
    @NonNull
    public Vaccine update(@NonNull Long id, @NonNull Vaccine entity) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        requireNonNull(entity, ENTITY_MUST_NOT_BE_NULL);
        getById(id);
        entity.setId(id);
        return repository.save(entity);
    }

    @Override
    public void delete(@NonNull Long id) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        repository.delete(getById(id));
    }
}
