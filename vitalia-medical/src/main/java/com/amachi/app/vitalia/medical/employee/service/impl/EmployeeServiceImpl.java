package com.amachi.app.vitalia.medical.employee.service.impl;

import com.amachi.app.core.common.event.DomainEventPublisher;
import com.amachi.app.core.common.repository.CommonRepository;
import com.amachi.app.core.common.service.BaseService;
import com.amachi.app.vitalia.medical.employee.dto.search.EmployeeSearchDto;
import com.amachi.app.vitalia.medical.employee.entity.Employee;
import com.amachi.app.vitalia.medical.employee.repository.EmployeeRepository;
import com.amachi.app.vitalia.medical.employee.service.EmployeeService;
import com.amachi.app.vitalia.medical.employee.specification.EmployeeSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 * Standard implementation of Employee Service using BaseService (SaaS Elite Tier).
 */
@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl extends BaseService<Employee, EmployeeSearchDto> implements EmployeeService {

    private final EmployeeRepository repository;
    private final DomainEventPublisher eventPublisher;

    @Override
    protected CommonRepository<Employee, Long> getRepository() {
        return repository;
    }

    @Override
    protected Specification<Employee> buildSpecification(EmployeeSearchDto searchDto) {
        return new EmployeeSpecification(searchDto);
    }

    @Override
    protected DomainEventPublisher getEventPublisher() {
        return eventPublisher;
    }

    @Override
    protected void publishCreatedEvent(Employee entity) {
        // Publish created event
    }

    @Override
    protected void publishUpdatedEvent(Employee entity) {
        // Publish updated event
    }
}
