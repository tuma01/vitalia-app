package com.amachi.app.vitalia.medical.history.controller;

import com.amachi.app.core.common.controller.GenericApi;
import com.amachi.app.core.common.dto.PageResponseDto;
import com.amachi.app.vitalia.medical.history.dto.MedicalHistoryDto;
import com.amachi.app.vitalia.medical.history.dto.search.MedicalHistorySearchDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.amachi.app.core.common.controller.BaseController.*;

@Tag(name = "Medical History", description = "REST API para gestionar el expediente clínico (FHIR Medical History).")
public interface MedicalHistoryApi extends GenericApi<MedicalHistoryDto> {
    
    @Operation(summary = "Obtener Expediente por ID")
    @GetMapping(value = ID, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<MedicalHistoryDto> getMedicalHistoryById(@PathVariable("id") Long id);

    @Operation(summary = "Crear Expediente")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<MedicalHistoryDto> createMedicalHistory(@Valid @RequestBody MedicalHistoryDto dto);

    @Operation(summary = "Actualizar Expediente")
    @PutMapping(value = ID, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<MedicalHistoryDto> updateMedicalHistory(@PathVariable("id") Long id, @Valid @RequestBody MedicalHistoryDto dto);

    @Operation(summary = "Eliminar Expediente")
    @DeleteMapping(value = ID, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> deleteMedicalHistory(@PathVariable("id") Long id);

    @Operation(summary = "Obtener todos los Expedientes")
    @GetMapping(value = ALL, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<MedicalHistoryDto>> getAllMedicalHistories();

    @Operation(summary = "Búsqueda paginada de Expedientes")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<PageResponseDto<MedicalHistoryDto>> getPaginatedMedicalHistories(MedicalHistorySearchDto searchDto,
                                                                                    @RequestParam(value = "pageIndex", defaultValue = "0") Integer pageIndex,
                                                                                    @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize);
}
