package com.amachi.app.vitalia.medicalcatalog.bloodtype.service.impl;

import com.amachi.app.core.common.exception.ResourceNotFoundException;
import com.amachi.app.core.common.service.GenericService;
import com.amachi.app.vitalia.medicalcatalog.bloodtype.dto.search.BloodTypeSearchDto;
import com.amachi.app.vitalia.medicalcatalog.bloodtype.entity.BloodType;
import com.amachi.app.vitalia.medicalcatalog.bloodtype.repository.BloodTypeRepository;
import com.amachi.app.vitalia.medicalcatalog.bloodtype.specification.BloodTypeSpecification;
import lombok.RequiredArgsConstructor;
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

@RequiredArgsConstructor
@Service
@Transactional
public class BloodTypeServiceImpl implements GenericService<BloodType, BloodTypeSearchDto> {
    private final BloodTypeRepository repository;

    @Override @Transactional(readOnly = true) public List<BloodType> getAll() { return repository.findAll(); }

    @Override @Transactional(readOnly = true) public Page<BloodType> getAll(BloodTypeSearchDto searchDto, Integer pageIndex, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize, Sort.by(Sort.Direction.ASC, "name"));
        Specification<BloodType> specification = new BloodTypeSpecification(searchDto);
        return repository.findAll(specification, pageable);
    }

    @Override @Transactional(readOnly = true) public BloodType getById(Long id) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(BloodType.class.getName(), "error.resource.not.found", id));
    }

    @Override public BloodType create(BloodType entity) {
        requireNonNull(entity, ENTITY_MUST_NOT_BE_NULL);
        return repository.save(entity);
    }

    @Override public BloodType update(Long id, BloodType entity) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        requireNonNull(entity, ENTITY_MUST_NOT_BE_NULL);
        repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(BloodType.class.getName(), "error.resource.not.found", id));
        entity.setId(id);
        return repository.save(entity);
    }

    @Override public void delete(Long id) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        BloodType entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(BloodType.class.getName(), "error.resource.not.found", id));
        repository.delete(entity);
    }
}
