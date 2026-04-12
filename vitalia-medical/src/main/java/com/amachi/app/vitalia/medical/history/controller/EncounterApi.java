package com.amachi.app.vitalia.medical.history.controller;

import com.amachi.app.core.common.controller.GenericApi;
import com.amachi.app.core.common.dto.PageResponseDto;
import com.amachi.app.vitalia.medical.history.dto.ConditionDto;
import com.amachi.app.vitalia.medical.history.dto.EncounterDto;
import com.amachi.app.vitalia.medical.history.dto.ObservationDto;
import com.amachi.app.vitalia.medical.history.dto.request.ConditionRequest;
import com.amachi.app.vitalia.medical.history.dto.request.ObservationRequest;
import com.amachi.app.vitalia.medical.history.dto.request.StartEncounterRequest;
import com.amachi.app.vitalia.medical.history.dto.search.EncounterSearchDto;
import com.amachi.app.vitalia.medical.history.entity.MedicationRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.amachi.app.core.common.controller.BaseController.*;

@Tag(name = "Encounter", description = "REST API para gestionar encuentros clínicos (FHIR Encounter). Orquestación de actos médicos.")
public interface EncounterApi extends GenericApi<EncounterDto> {
    String NAME_API = "Encounter";

    @Operation(summary = "Obtener un Encuentro por ID")
    @GetMapping(value = ID, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<EncounterDto> getEncounterById(@PathVariable("id") Long id);

    @Operation(summary = "Crear un Encuentro (Uso básico CRUD)")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<EncounterDto> createEncounter(@Valid @RequestBody EncounterDto dto);

    @Operation(summary = "Actualizar un Encuentro")
    @PutMapping(value = ID, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<EncounterDto> updateEncounter(@PathVariable("id") Long id, @Valid @RequestBody EncounterDto dto);

    @Operation(summary = "Eliminar un Encuentro")
    @DeleteMapping(value = ID, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> deleteEncounter(@PathVariable("id") Long id);

    @Operation(summary = "Obtener todos los Encuentros")
    @GetMapping(value = ALL, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<EncounterDto>> getAllEncounters();

    @Operation(summary = "Búsqueda paginada de Encuentros")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<PageResponseDto<EncounterDto>> getPaginatedEncounters(EncounterSearchDto searchDto,
                                                                         @RequestParam(value = "pageIndex", defaultValue = "0") Integer pageIndex,
                                                                         @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize);

    // --- Elite Tier Clinical Operations ---

    @Operation(summary = "Iniciar Encuentro Clínico (Arribo/Atención)", description = "Transiciona del flujo logístico (Cita) al flujo clínico real.")
    @PostMapping(value = "/start", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<EncounterDto> startEncounter(@Valid @RequestBody StartEncounterRequest request);

    @Operation(summary = "Poner Encuentro en Espera/Pausa")
    @PostMapping(value = ID + "/hold", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<EncounterDto> holdEncounter(@PathVariable("id") Long id, @RequestParam String reason);

    @Operation(summary = "Reanudar Encuentro en Espera")
    @PostMapping(value = ID + "/resume", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<EncounterDto> resumeEncounter(@PathVariable("id") Long id);

    @Operation(summary = "Finalizar/Completar Encuentro Clínico", description = "Requiere al menos un diagnóstico registrado para proceder al cierre.")
    @PostMapping(value = ID + "/complete", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<EncounterDto> completeEncounter(@PathVariable("id") Long id);

    @Operation(summary = "Cancelar Encuentro")
    @PostMapping(value = ID + "/cancel", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<EncounterDto> cancelEncounter(@PathVariable("id") Long id, @RequestParam String reason);

    @Operation(summary = "Agregar Diagnóstico (Condition) al Encuentro")
    @PostMapping(value = ID + "/conditions", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ConditionDto> addCondition(@PathVariable("id") Long id, @Valid @RequestBody ConditionRequest request);

    @Operation(summary = "Agregar Medición/Observación (Signos Vitales) al Encuentro")
    @PostMapping(value = ID + "/observations", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ObservationDto> addObservation(@PathVariable("id") Long id, @Valid @RequestBody ObservationRequest request);

    @Operation(summary = "Prescribir Medicación (E-Prescribing)")
    @PostMapping(value = ID + "/medications", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> prescribeMedication(@PathVariable("id") Long id, @RequestBody MedicationRequest request);
}
