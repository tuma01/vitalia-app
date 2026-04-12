package com.amachi.app.vitalia.medical.history.controller;

import com.amachi.app.core.common.controller.GenericApi;
import com.amachi.app.core.common.dto.PageResponseDto;
import com.amachi.app.vitalia.medical.history.dto.ConditionDto;
import com.amachi.app.vitalia.medical.history.dto.search.ConditionSearchDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.amachi.app.core.common.controller.BaseController.*;

@Tag(name = "Condition", description = "REST API para gestionar condiciones de salud y diagnósticos (FHIR Condition).")
public interface ConditionApi extends GenericApi<ConditionDto> {
    
    @Operation(summary = "Obtener Diagnóstico por ID")
    @GetMapping(value = ID, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ConditionDto> getConditionById(@PathVariable("id") Long id);

    @Operation(summary = "Crear Diagnóstico")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ConditionDto> createCondition(@Valid @RequestBody ConditionDto dto);

    @Operation(summary = "Actualizar Diagnóstico")
    @PutMapping(value = ID, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ConditionDto> updateCondition(@PathVariable("id") Long id, @Valid @RequestBody ConditionDto dto);

    @Operation(summary = "Eliminar Diagnóstico")
    @DeleteMapping(value = ID, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> deleteCondition(@PathVariable("id") Long id);

    @Operation(summary = "Obtener todos los Diagnósticos")
    @GetMapping(value = ALL, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<ConditionDto>> getAllConditions();

    @Operation(summary = "Búsqueda paginada de Diagnósticos")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<PageResponseDto<ConditionDto>> getPaginatedConditions(ConditionSearchDto searchDto,
                                                                         @RequestParam(value = "pageIndex", defaultValue = "0") Integer pageIndex,
                                                                         @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize);
}
