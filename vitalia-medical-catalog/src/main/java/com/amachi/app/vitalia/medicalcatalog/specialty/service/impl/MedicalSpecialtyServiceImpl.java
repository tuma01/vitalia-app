package com.amachi.app.vitalia.medicalcatalog.specialty.service.impl;

import com.amachi.app.core.common.exception.ResourceNotFoundException;
import com.amachi.app.core.common.service.GenericService;
import com.amachi.app.vitalia.medicalcatalog.specialty.dto.search.MedicalSpecialtySearchDto;
import com.amachi.app.vitalia.medicalcatalog.specialty.entity.MedicalSpecialty;
import com.amachi.app.vitalia.medicalcatalog.specialty.repository.MedicalSpecialtyRepository;
import com.amachi.app.vitalia.medicalcatalog.specialty.specification.MedicalSpecialtySpecification;
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
public class MedicalSpecialtyServiceImpl implements GenericService<MedicalSpecialty, MedicalSpecialtySearchDto> {

    private final MedicalSpecialtyRepository repository;

    @Override
    @Transactional(readOnly = true)
    @NonNull
    public List<MedicalSpecialty> getAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    @NonNull
    public Page<MedicalSpecialty> getAll(@NonNull MedicalSpecialtySearchDto searchDto, @NonNull Integer pageIndex,
            @NonNull Integer pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize, Sort.by(Sort.Direction.ASC, "createdDate"));
        Specification<MedicalSpecialty> specification = new MedicalSpecialtySpecification(searchDto);
        return repository.findAll(specification, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    @NonNull
    public MedicalSpecialty getById(@NonNull Long id) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(MedicalSpecialty.class.getName(),
                        "error.resource.not.found", id));
    }

    @Override
    @NonNull
    public MedicalSpecialty create(@NonNull MedicalSpecialty entity) {
        requireNonNull(entity, ENTITY_MUST_NOT_BE_NULL);
        return repository.save(entity);
    }

    @Override
    @NonNull
    public MedicalSpecialty update(@NonNull Long id, @NonNull MedicalSpecialty entity) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        requireNonNull(entity, ENTITY_MUST_NOT_BE_NULL);
        repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(MedicalSpecialty.class.getName(),
                        "error.resource.not.found", id));
        entity.setId(id);
        return repository.save(entity);
    }

    @Override
    public void delete(@NonNull Long id) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        MedicalSpecialty entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(MedicalSpecialty.class.getName(),
                        "error.resource.not.found", id));
        repository.delete(entity);
    }
}
