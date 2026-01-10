package com.amachi.app.vitalia.medicalcatalog.procedure.service.impl;

import com.amachi.app.core.common.exception.ResourceNotFoundException;
import com.amachi.app.core.common.service.GenericService;
import com.amachi.app.vitalia.medicalcatalog.procedure.dto.search.MedicalProcedureSearchDto;
import com.amachi.app.vitalia.medicalcatalog.procedure.entity.MedicalProcedure;
import com.amachi.app.vitalia.medicalcatalog.procedure.repository.MedicalProcedureRepository;
import com.amachi.app.vitalia.medicalcatalog.procedure.specification.MedicalProcedureSpecification;
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
public class MedicalProcedureServiceImpl implements GenericService<MedicalProcedure, MedicalProcedureSearchDto> {

    private final MedicalProcedureRepository repository;

    @Override
    @Transactional(readOnly = true)
    @NonNull
    public List<MedicalProcedure> getAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    @NonNull
    public Page<MedicalProcedure> getAll(@NonNull MedicalProcedureSearchDto searchDto, @NonNull Integer pageIndex,
            @NonNull Integer pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize, Sort.by(Sort.Direction.DESC, "createdDate"));
        Specification<MedicalProcedure> specification = new MedicalProcedureSpecification(searchDto);
        return repository.findAll(specification, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    @NonNull
    public MedicalProcedure getById(@NonNull Long id) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(MedicalProcedure.class.getName(),
                        "error.resource.not.found", id));
    }

    @Override
    @NonNull
    public MedicalProcedure create(@NonNull MedicalProcedure entity) {
        requireNonNull(entity, ENTITY_MUST_NOT_BE_NULL);
        return repository.save(entity);
    }

    @Override
    @NonNull
    public MedicalProcedure update(@NonNull Long id, @NonNull MedicalProcedure entity) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        requireNonNull(entity, ENTITY_MUST_NOT_BE_NULL);
        repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(MedicalProcedure.class.getName(),
                        "error.resource.not.found", id));
        entity.setId(id);
        return repository.save(entity);
    }

    @Override
    public void delete(@NonNull Long id) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        MedicalProcedure entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(MedicalProcedure.class.getName(),
                        "error.resource.not.found", id));
        repository.delete(entity);
    }
}
