package com.amachi.app.vitalia.medical.infrastructure.controller;

import com.amachi.app.core.common.dto.PageResponseDto;
import com.amachi.app.vitalia.medical.infrastructure.dto.DepartmentUnitTypeDto;
import com.amachi.app.vitalia.medical.infrastructure.dto.search.DepartmentUnitTypeSearchDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Infraestructura - Tipos de Unidad", description = "Gestion del catalogo de especialidades de unidades hospitalarias")
public interface DepartmentUnitTypeApi {
    String NAME_API = "Especialidad de Unidad";

    @Operation(
            summary = "Obtener " + NAME_API + " por ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = NAME_API + " localizada."),
                    @ApiResponse(responseCode = "404", description = NAME_API + " no encontrada.")
            }
    )
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<DepartmentUnitTypeDto> getUnitTypeById(@PathVariable Long id);

    @Operation(summary = "Crear nueva " + NAME_API)
    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<DepartmentUnitTypeDto> createUnitType(@Valid @RequestBody DepartmentUnitTypeDto dto);

    @Operation(summary = "Actualizar " + NAME_API + " existente")
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<DepartmentUnitTypeDto> updateUnitType(@PathVariable Long id, @Valid @RequestBody DepartmentUnitTypeDto dto);

    @Operation(summary = "Eliminar " + NAME_API)
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> deleteUnitType(@PathVariable Long id);

    @Operation(summary = "Listar todas las " + NAME_API + "s")
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<DepartmentUnitTypeDto>> getAllUnitTypes();

    @Operation(summary = "Buscador de " + NAME_API + "s paginado")
    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<PageResponseDto<DepartmentUnitTypeDto>> getPaginatedUnitTypes(
            @Parameter(description = "Criterios de busqueda") DepartmentUnitTypeSearchDto searchDto,
            @RequestParam(defaultValue = "0") Integer pageIndex,
            @RequestParam(defaultValue = "10") Integer pageSize);
}
