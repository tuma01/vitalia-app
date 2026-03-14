package com.amachi.app.vitalia.medical.employee.service.impl;

import com.amachi.app.core.common.exception.ResourceNotFoundException;
import com.amachi.app.vitalia.medical.employee.dto.search.EmployeeSearchDto;
import com.amachi.app.vitalia.medical.employee.entity.Employee;
import com.amachi.app.vitalia.medical.employee.repository.EmployeeRepository;
import com.amachi.app.vitalia.medical.employee.service.EmployeeService;
import com.amachi.app.vitalia.medical.employee.specification.EmployeeSpecification;
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

@Service
@RequiredArgsConstructor
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<Employee> getAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Employee> getAll(EmployeeSearchDto searchDto, Integer pageIndex, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize, Sort.by(Sort.Direction.DESC, "createdDate"));
        Specification<Employee> specification = new EmployeeSpecification(searchDto);
        return repository.findAll(specification, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Employee getById(Long id) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Employee.class.getName(), "error.resource.not.found", id));
    }

    @Override
    public Employee create(Employee entity) {
        requireNonNull(entity, ENTITY_MUST_NOT_BE_NULL);
        return repository.save(entity);
    }

    @Override
    public Employee update(Long id, Employee entity) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        requireNonNull(entity, ENTITY_MUST_NOT_BE_NULL);
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException(Employee.class.getName(), "error.resource.not.found", id);
        }
        entity.setId(id);
        return repository.save(entity);
    }

    @Override
    public void delete(Long id) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        Employee employee = getById(id);
        employee.setDeleted(true);
        repository.save(employee);
    }
}
