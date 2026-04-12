package com.amachi.app.vitalia.medical.consultation.controller;

import com.amachi.app.core.common.controller.GenericApi;
import com.amachi.app.core.common.dto.PageResponseDto;
import com.amachi.app.vitalia.medical.consultation.dto.ConsultationDto;
import com.amachi.app.vitalia.medical.consultation.dto.search.ConsultationSearchDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.amachi.app.core.common.controller.BaseController.ID;

/**
 * Medical Consultation API definition (SaaS Elite Tier).
 */
@Tag(name = "Clinical - Consultations", description = "REST API for managing clinical encounters and consultations")
public interface ConsultationApi extends GenericApi<ConsultationDto> {
    String NAME_API = "Consultation";

    @Operation(summary = "Get consultation by ID")
    @GetMapping(value = ID, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ConsultationDto> getById(@PathVariable("id") Long id);

    @Operation(summary = "Create medical consultation")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ConsultationDto> create(@Valid @RequestBody ConsultationDto dto);

    @Operation(summary = "Update consultation details")
    @PutMapping(value = ID, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ConsultationDto> update(@PathVariable("id") Long id, @Valid @RequestBody ConsultationDto dto);

    @Operation(summary = "Search consultations with expert filters")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<PageResponseDto<ConsultationDto>> search(
            ConsultationSearchDto searchDto,
            @RequestParam(value = "pageIndex", defaultValue = "0") Integer pageIndex,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    );

    @Operation(summary = "Get consultations by medical history ID")
    @GetMapping(value = "/history/{historyId}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<ConsultationDto>> getByHistoryId(@PathVariable("historyId") Long historyId);
}
