package com.amachi.app.vitalia.medicalcatalog.allergy.service.impl;

import com.amachi.app.core.common.exception.ResourceNotFoundException;
import com.amachi.app.core.common.service.GenericService;
import com.amachi.app.vitalia.medicalcatalog.allergy.dto.search.AllergySearchDto;
import com.amachi.app.vitalia.medicalcatalog.allergy.entity.Allergy;
import com.amachi.app.vitalia.medicalcatalog.allergy.repository.AllergyRepository;
import com.amachi.app.vitalia.medicalcatalog.allergy.specification.AllergySpecification;
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
public class AllergyServiceImpl implements GenericService<Allergy, AllergySearchDto> {

    AllergyRepository allergyRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Allergy> getAll() {
        return allergyRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Allergy> getAll(AllergySearchDto searchDto, Integer pageIndex, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize, Sort.by(Sort.Direction.ASC, "name"));
        Specification<Allergy> specification = new AllergySpecification(searchDto);
        return allergyRepository.findAll(specification, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Allergy getById(Long id) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        return allergyRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(Allergy.class.getName(), "error.resource.not.found", id));
    }

    @Override
    public Allergy create(Allergy entity) {
        requireNonNull(entity, ENTITY_MUST_NOT_BE_NULL);
        return requireNonNull(allergyRepository.save(entity));
    }

    @Override
    public Allergy update(Long id, Allergy entity) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        requireNonNull(entity, ENTITY_MUST_NOT_BE_NULL);
        allergyRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(Allergy.class.getName(), "error.resource.not.found", id));
        entity.setId(id);
        return requireNonNull(allergyRepository.save(entity));
    }

    @Override
    public void delete(Long id) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        allergyRepository.delete(getById(id));
    }
}
