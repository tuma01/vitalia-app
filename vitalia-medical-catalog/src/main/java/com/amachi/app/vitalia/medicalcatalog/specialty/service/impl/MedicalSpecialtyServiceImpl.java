package com.amachi.app.vitalia.medicalcatalog.specialty.service.impl;

import com.amachi.app.core.common.exception.ResourceNotFoundException;
import com.amachi.app.core.common.service.GenericService;
import com.amachi.app.vitalia.medicalcatalog.specialty.dto.search.MedicalSpecialtySearchDto;
import com.amachi.app.vitalia.medicalcatalog.specialty.entity.MedicalSpecialty;
import com.amachi.app.vitalia.medicalcatalog.specialty.repository.MedicalSpecialtyRepository;
import com.amachi.app.vitalia.medicalcatalog.specialty.specification.MedicalSpecialtySpecification;
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
public class MedicalSpecialtyServiceImpl implements GenericService<MedicalSpecialty, MedicalSpecialtySearchDto> {

    MedicalSpecialtyRepository medicalSpecialtyRepository;

    @Override
    @Transactional(readOnly = true)
    public List<MedicalSpecialty> getAll() {
        return medicalSpecialtyRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MedicalSpecialty> getAll(MedicalSpecialtySearchDto searchDto, Integer pageIndex, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize, Sort.by(Sort.Direction.DESC, "createdDate"));
        Specification<MedicalSpecialty> specification = new MedicalSpecialtySpecification(searchDto);
        return medicalSpecialtyRepository.findAll(specification, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public MedicalSpecialty getById(Long id) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        return medicalSpecialtyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(MedicalSpecialty.class.getName(),
                        "error.resource.not.found", id));
    }

    @Override
    public MedicalSpecialty create(MedicalSpecialty entity) {
        requireNonNull(entity, ENTITY_MUST_NOT_BE_NULL);
        return requireNonNull(medicalSpecialtyRepository.save(entity));
    }

    @Override
    public MedicalSpecialty update(Long id, MedicalSpecialty entity) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        requireNonNull(entity, ENTITY_MUST_NOT_BE_NULL);
        medicalSpecialtyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(MedicalSpecialty.class.getName(),
                        "error.resource.not.found", id));
        entity.setId(id);
        return requireNonNull(medicalSpecialtyRepository.save(entity));
    }

    @Override
    public void delete(Long id) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        medicalSpecialtyRepository.delete(getById(id));
    }
}
