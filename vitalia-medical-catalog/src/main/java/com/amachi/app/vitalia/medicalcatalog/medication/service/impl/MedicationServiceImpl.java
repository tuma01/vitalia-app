package com.amachi.app.vitalia.medicalcatalog.medication.service.impl;

import com.amachi.app.core.common.exception.ResourceNotFoundException;
import com.amachi.app.core.common.service.GenericService;
import com.amachi.app.vitalia.medicalcatalog.medication.dto.search.MedicationSearchDto;
import com.amachi.app.vitalia.medicalcatalog.medication.entity.Medication;
import com.amachi.app.vitalia.medicalcatalog.medication.repository.MedicationRepository;
import com.amachi.app.vitalia.medicalcatalog.medication.specification.MedicationSpecification;
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
public class MedicationServiceImpl implements GenericService<Medication, MedicationSearchDto> {

    private final MedicationRepository repository;

    @Override
    @Transactional(readOnly = true)
    @NonNull
    public List<Medication> getAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    @NonNull
    public Page<Medication> getAll(@NonNull MedicationSearchDto searchDto, @NonNull Integer pageIndex,
            @NonNull Integer pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize, Sort.by(Sort.Direction.DESC, "createdDate"));
        Specification<Medication> specification = new MedicationSpecification(searchDto);
        return repository.findAll(specification, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    @NonNull
    public Medication getById(@NonNull Long id) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Medication.class.getName(), "error.resource.not.found",
                        id));
    }

    @Transactional(readOnly = true)
    @NonNull
    public Medication getByCode(@NonNull String code) {
        requireNonNull(code, "Code must not be null");
        return repository.findByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException(Medication.class.getName(), "error.resource.not.found",
                        code));
    }

    @Override
    @NonNull
    public Medication create(@NonNull Medication entity) {
        requireNonNull(entity, ENTITY_MUST_NOT_BE_NULL);
        return repository.save(entity);
    }

    @Override
    @NonNull
    public Medication update(@NonNull Long id, @NonNull Medication entity) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        requireNonNull(entity, ENTITY_MUST_NOT_BE_NULL);
        Medication existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Medication.class.getName(), "error.resource.not.found",
                        id));

        // Mantener el ID de la entidad a guardar
        entity.setId(id);

        return repository.save(entity);
    }

    @Override
    public void delete(@NonNull Long id) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        Medication entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Medication.class.getName(), "error.resource.not.found",
                        id));
        repository.delete(entity);
    }
}
