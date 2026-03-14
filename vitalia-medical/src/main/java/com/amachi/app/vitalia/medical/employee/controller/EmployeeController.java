package com.amachi.app.vitalia.medical.employee.controller;

import com.amachi.app.vitalia.medical.employee.dto.EmployeeDto;
import com.amachi.app.vitalia.medical.employee.dto.search.EmployeeSearchDto;
import com.amachi.app.vitalia.medical.employee.entity.Employee;
import com.amachi.app.vitalia.medical.employee.mapper.EmployeeMapper;
import com.amachi.app.vitalia.medical.employee.service.EmployeeService;
import com.amachi.app.core.common.controller.BaseController;
import com.amachi.app.core.common.dto.PageResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class EmployeeController extends BaseController implements EmployeeApi {

    private final EmployeeService service;
    private final EmployeeMapper mapper;

    @Override
    public ResponseEntity<EmployeeDto> getEmployeeById(Long id) {
        return ResponseEntity.ok(mapper.toDto(service.getById(id)));
    }

    @Override
    public ResponseEntity<EmployeeDto> createEmployee(EmployeeDto dto) {
        Employee entity = mapper.toEntity(dto);
        return new ResponseEntity<>(mapper.toDto(service.create(entity)), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<EmployeeDto> updateEmployee(Long id, EmployeeDto dto) {
        Employee existing = service.getById(id);
        mapper.updateEntityFromDto(dto, existing);
        return ResponseEntity.ok(mapper.toDto(service.update(id, existing)));
    }

    @Override
    public ResponseEntity<Void> deleteEmployee(Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<EmployeeDto>> getAllEmployees() {
        return ResponseEntity.ok(service.getAll().stream().map(mapper::toDto).toList());
    }

    @Override
    public ResponseEntity<PageResponseDto<EmployeeDto>> getPaginatedEmployees(
            EmployeeSearchDto searchDto, Integer pageIndex, Integer pageSize) {
        
        Page<Employee> page = service.getAll(searchDto, pageIndex, pageSize);
        List<EmployeeDto> dtos = page.getContent().stream().map(mapper::toDto).toList();

        return ResponseEntity.ok(PageResponseDto.<EmployeeDto>builder()
                .content(dtos)
                .totalElements(page.getTotalElements())
                .pageIndex(page.getNumber())
                .pageSize(page.getSize())
                .totalPages(page.getTotalPages())
                .first(page.isFirst())
                .last(page.isLast())
                .empty(page.isEmpty())
                .numberOfElements(page.getNumberOfElements())
                .build());
    }
}
