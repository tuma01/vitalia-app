package com.amachi.app.vitalia.medical.employee.controller;

import com.amachi.app.vitalia.medical.employee.dto.EmployeeDto;
import com.amachi.app.vitalia.medical.employee.dto.search.EmployeeSearchDto;
import com.amachi.app.core.common.dto.PageResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Employee", description = "Gestión de personal médico y administrativo (Employees)")
@RequestMapping("/employees")
public interface EmployeeApi {

    @Operation(summary = "Obtener empleado por ID")
    @GetMapping("/{id}")
    ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable Long id);

    @Operation(summary = "Crear nuevo empleado")
    @PostMapping
    ResponseEntity<EmployeeDto> createEmployee(@Valid @RequestBody EmployeeDto dto);

    @Operation(summary = "Actualizar empleado existente")
    @PutMapping("/{id}")
    ResponseEntity<EmployeeDto> updateEmployee(@PathVariable Long id, @Valid @RequestBody EmployeeDto dto);

    @Operation(summary = "Eliminar empleado (borrado lógico)")
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteEmployee(@PathVariable Long id);

    @Operation(summary = "Obtener todos los empleados")
    @GetMapping("/all")
    ResponseEntity<List<EmployeeDto>> getAllEmployees();

    @Operation(summary = "Obtener empleados paginados con filtros")
    @GetMapping
    ResponseEntity<PageResponseDto<EmployeeDto>> getPaginatedEmployees(
            EmployeeSearchDto searchDto,
            @RequestParam(defaultValue = "0") Integer pageIndex,
            @RequestParam(defaultValue = "10") Integer pageSize);
}
