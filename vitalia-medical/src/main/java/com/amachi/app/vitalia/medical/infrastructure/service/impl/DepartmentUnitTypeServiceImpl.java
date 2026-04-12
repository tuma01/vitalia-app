package com.amachi.app.vitalia.medical.infrastructure.service.impl;

import com.amachi.app.core.common.exception.ResourceNotFoundException;
import com.amachi.app.vitalia.medical.infrastructure.dto.search.DepartmentUnitTypeSearchDto;
import com.amachi.app.vitalia.medical.infrastructure.entity.DepartmentUnitType;
import com.amachi.app.vitalia.medical.infrastructure.repository.DepartmentUnitTypeRepository;
import com.amachi.app.vitalia.medical.infrastructure.service.DepartmentUnitTypeService;
import com.amachi.app.vitalia.medical.infrastructure.specification.DepartmentUnitTypeSpecification;
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

/**
 * Implementacion del servicio de tipos de unidades hospitalarias.
 */
@Service
@AllArgsConstructor
public class DepartmentUnitTypeServiceImpl implements DepartmentUnitTypeService {

    private final DepartmentUnitTypeRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<DepartmentUnitType> getAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DepartmentUnitType> getAll(DepartmentUnitTypeSearchDto searchDto, Integer pageIndex, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize, Sort.by(Sort.Direction.DESC, "createdDate"));
        Specification<DepartmentUnitType> specification = new DepartmentUnitTypeSpecification(searchDto);
        return repository.findAll(specification, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public DepartmentUnitType getById(Long id) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(DepartmentUnitType.class.getName(), "error.resource.not.found", id));
    }

    @Override
    @Transactional
    public DepartmentUnitType create(DepartmentUnitType entity) {
        requireNonNull(entity, ENTITY_MUST_NOT_BE_NULL);
        return repository.save(entity);
    }

    @Override
    @Transactional
    public DepartmentUnitType update(Long id, DepartmentUnitType entity) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        requireNonNull(entity, ENTITY_MUST_NOT_BE_NULL);
        DepartmentUnitType existing = getById(id);
        entity.setId(id);
        return repository.save(entity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        DepartmentUnitType entity = getById(id);
        repository.delete(entity);
    }
}
