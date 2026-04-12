package com.amachi.app.vitalia.medical.history.controller;

import com.amachi.app.core.common.dto.PageResponseDto;
import com.amachi.app.vitalia.medical.history.dto.ConditionDto;
import com.amachi.app.vitalia.medical.history.dto.EncounterDto;
import com.amachi.app.vitalia.medical.history.dto.ObservationDto;
import com.amachi.app.vitalia.medical.history.dto.search.ConditionSearchDto;
import com.amachi.app.vitalia.medical.history.dto.search.EncounterSearchDto;
import com.amachi.app.vitalia.medical.history.dto.search.ObservationSearchDto;
import com.amachi.app.vitalia.medical.history.dto.timeline.ClinicalEventDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "Clinical Analytics", description = "REST API para el motor de búsqueda clínica avanzada y análisis longitudinal (Analytics Tier).")
public interface ClinicalSearchApi {

    @Operation(summary = "Línea de Vida Clínica (360° View)",
               description = "Agrupa cronológicamente todos los encuentros, diagnósticos y mediciones de un paciente en un flujo unificado.")
    @GetMapping(value = "/patients/{id}/clinical-history-stream", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<ClinicalEventDto>> getClinicalHistoryStream(
            @Parameter(description = "ID del paciente para el cual reconstruir la historia clínica.") @PathVariable("id") Long id);

    @Operation(summary = "Búsqueda Cross-Entidad de Encuentros")
    @GetMapping(value = "/search/encounters", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<PageResponseDto<EncounterDto>> searchEncounters(
            EncounterSearchDto criteria,
            @RequestParam(defaultValue = "0") Integer pageIndex,
            @RequestParam(defaultValue = "10") Integer pageSize);

    @Operation(summary = "Búsqueda Cross-Entidad de Diagnósticos")
    @GetMapping(value = "/search/conditions", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<PageResponseDto<ConditionDto>> searchConditions(
            ConditionSearchDto criteria,
            @RequestParam(defaultValue = "0") Integer pageIndex,
            @RequestParam(defaultValue = "10") Integer pageSize);

    @Operation(summary = "Búsqueda Cross-Entidad de Observaciones")
    @GetMapping(value = "/search/observations", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<PageResponseDto<ObservationDto>> searchObservations(
            ObservationSearchDto criteria,
            @RequestParam(defaultValue = "0") Integer pageIndex,
            @RequestParam(defaultValue = "10") Integer pageSize);
}
