package com.amachi.app.vitalia.medical.infrastructure.service.impl;

import com.amachi.app.core.common.exception.ResourceNotFoundException;
import com.amachi.app.vitalia.medical.infrastructure.dto.search.DepartmentUnitSearchDto;
import com.amachi.app.vitalia.medical.infrastructure.entity.DepartmentUnit;
import com.amachi.app.vitalia.medical.infrastructure.repository.DepartmentUnitRepository;
import com.amachi.app.vitalia.medical.infrastructure.service.DepartmentUnitService;
import com.amachi.app.vitalia.medical.infrastructure.specification.DepartmentUnitSpecification;
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
 * Implementacion del servicio de unidades hospitalarias.
 */
@Service
@AllArgsConstructor
public class DepartmentUnitServiceImpl implements DepartmentUnitService {

    private final DepartmentUnitRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<DepartmentUnit> getAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DepartmentUnit> getAll(DepartmentUnitSearchDto searchDto, Integer pageIndex, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize, Sort.by(Sort.Direction.DESC, "createdDate"));
        Specification<DepartmentUnit> specification = new DepartmentUnitSpecification(searchDto);
        return repository.findAll(specification, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public DepartmentUnit getById(Long id) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(DepartmentUnit.class.getName(), "error.resource.not.found", id));
    }

    @Override
    @Transactional
    public DepartmentUnit create(DepartmentUnit entity) {
        requireNonNull(entity, ENTITY_MUST_NOT_BE_NULL);
        return repository.save(entity);
    }

    @Override
    @Transactional
    public DepartmentUnit update(Long id, DepartmentUnit entity) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        requireNonNull(entity, ENTITY_MUST_NOT_BE_NULL);
        DepartmentUnit existing = getById(id);
        entity.setId(id);
        return repository.save(entity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        DepartmentUnit entity = getById(id);
        repository.delete(entity);
    }
}
