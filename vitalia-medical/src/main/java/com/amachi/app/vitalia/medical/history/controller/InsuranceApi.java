package com.amachi.app.vitalia.medical.history.controller;

import com.amachi.app.core.common.controller.GenericApi;
import com.amachi.app.core.common.dto.PageResponseDto;
import com.amachi.app.vitalia.medical.history.dto.InsuranceDto;
import com.amachi.app.vitalia.medical.history.dto.search.InsuranceSearchDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.amachi.app.core.common.controller.BaseController.*;

@Tag(name = "Insurance", description = "REST API para gestionar pólizas de seguro del paciente.")
public interface InsuranceApi extends GenericApi<InsuranceDto> {
    
    @Operation(summary = "Obtener Seguro por ID")
    @GetMapping(value = ID, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<InsuranceDto> getInsuranceById(@PathVariable("id") Long id);

    @Operation(summary = "Crear Seguro")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<InsuranceDto> createInsurance(@Valid @RequestBody InsuranceDto dto);

    @Operation(summary = "Actualizar Seguro")
    @PutMapping(value = ID, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<InsuranceDto> updateInsurance(@PathVariable("id") Long id, @Valid @RequestBody InsuranceDto dto);

    @Operation(summary = "Eliminar Seguro")
    @DeleteMapping(value = ID, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> deleteInsurance(@PathVariable("id") Long id);

    @Operation(summary = "Obtener todos los Seguros")
    @GetMapping(value = ALL, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<InsuranceDto>> getAllInsurances();

    @Operation(summary = "Búsqueda paginada de Seguros")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<PageResponseDto<InsuranceDto>> getPaginatedInsurances(InsuranceSearchDto searchDto,
                                                                         @RequestParam(value = "pageIndex", defaultValue = "0") Integer pageIndex,
                                                                         @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize);
}
