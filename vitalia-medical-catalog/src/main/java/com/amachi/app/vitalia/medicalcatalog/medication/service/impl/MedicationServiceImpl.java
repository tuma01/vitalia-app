package com.amachi.app.vitalia.medicalcatalog.medication.service.impl;

import com.amachi.app.core.common.exception.ResourceNotFoundException;
import com.amachi.app.core.common.service.GenericService;
import com.amachi.app.vitalia.medicalcatalog.medication.dto.search.MedicationSearchDto;
import com.amachi.app.vitalia.medicalcatalog.medication.entity.Medication;
import com.amachi.app.vitalia.medicalcatalog.medication.repository.MedicationRepository;
import com.amachi.app.vitalia.medicalcatalog.medication.specification.MedicationSpecification;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.amachi.app.core.common.utils.AppConstants.ErrorMessages.ENTITY_MUST_NOT_BE_NULL;
import static com.amachi.app.core.common.utils.AppConstants.ErrorMessages.ID_MUST_NOT_BE_NULL;
import static java.util.Objects.requireNonNull;

@AllArgsConstructor
@Service
public class MedicationServiceImpl implements GenericService<Medication, MedicationSearchDto> {

    MedicationRepository medicationRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Medication> getAll() {
        return medicationRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Medication> getAll(MedicationSearchDto searchDto, Integer pageIndex, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize, Sort.by(Sort.Direction.DESC, "createdDate"));
        Specification<Medication> specification = new MedicationSpecification(searchDto);
        return medicationRepository.findAll(specification, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Medication getById(Long id) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        return medicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Medication.class.getName(), "error.resource.not.found",
                        id));
    }

    @Transactional(readOnly = true)
    public Medication getByCode(String code) {
        requireNonNull(code, "Code must not be null");
        return medicationRepository.findByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException(Medication.class.getName(), "error.resource.not.found",
                        code));
    }

    @Override
    public Medication create(Medication entity) {
        requireNonNull(entity, ENTITY_MUST_NOT_BE_NULL);
        return requireNonNull(medicationRepository.save(entity));
    }

    @Override
    public Medication update(Long id, Medication entity) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        requireNonNull(entity, ENTITY_MUST_NOT_BE_NULL);
        medicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Medication.class.getName(), "error.resource.not.found",
                        id));
        entity.setId(id);
        return requireNonNull(medicationRepository.save(entity));
    }

    @Override
    public void delete(Long id) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        medicationRepository.delete(getById(id));
    }
}
