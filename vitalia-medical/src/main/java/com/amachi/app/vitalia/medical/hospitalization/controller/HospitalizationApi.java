package com.amachi.app.vitalia.medical.hospitalization.controller;

import com.amachi.app.core.common.controller.GenericApi;
import com.amachi.app.core.common.dto.PageResponseDto;
import com.amachi.app.vitalia.medical.hospitalization.dto.HospitalizationDto;
import com.amachi.app.vitalia.medical.hospitalization.dto.search.HospitalizationSearchDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.amachi.app.core.common.controller.BaseController.ID;

/**
 * Enterprise API for Hospitalization control.
 */
@Tag(name = "Operations - Hospitalization", description = "REST API for hospital inpatient life cycle: Admission and Discharge.")
public interface HospitalizationApi extends GenericApi<HospitalizationDto> {
    String NAME_API = "Hospitalization";

    @Operation(
            summary = "Get hospitalization details by ID",
            description = "Returns the full hospital stay record, including bed, doctor, and diagnoses.",
            responses = {
                    @ApiResponse(responseCode = "200", description = NAME_API + " successfully located."),
                    @ApiResponse(responseCode = "404", description = NAME_API + " not found."),
                    @ApiResponse(responseCode = "500", description = "Internal server error.")
            }
    )
    @GetMapping(value = ID, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<HospitalizationDto> getHospitalizationById(
            @Parameter(description = "Unique ID of the " + NAME_API + " episode", required = true, example = "9001")
            @PathVariable("id") Long id
    );

    @Operation(
            summary = "Register hospital admission",
            description = "Starts the hospitalization process by assigning a unit, room, and bed to the patient.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Hospital admission registered successfully."),
                    @ApiResponse(responseCode = "400", description = "Invalid admission data or bed occupied."),
                    @ApiResponse(responseCode = "500", description = "Critical error processing admission.")
            }
    )
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<HospitalizationDto> createHospitalization(
            @Parameter(description = "Patient admission details.", required = true)
            @Valid @RequestBody HospitalizationDto dto
    );

    @Operation(
            summary = "Update hospitalization data or bed transfer",
            description = "Allows modifying the therapeutic scheme or managing the transfer of the patient to another bed/unit.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Hospitalization data updated."),
                    @ApiResponse(responseCode = "400", description = "Request conflict."),
                    @ApiResponse(responseCode = "404", description = "Hospitalization episode not found."),
                    @ApiResponse(responseCode = "500", description = "System error.")
            }
    )
    @PutMapping(value = ID, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<HospitalizationDto> updateHospitalization(
            @Parameter(description = "ID of the hospitalization to modify.", required = true)
            @PathVariable("id") Long id,
            @Parameter(description = "New stay parameters.", required = true)
            @Valid @RequestBody HospitalizationDto dto
    );

    @Operation(
            summary = "Expert Search for Hospital Internments",
            description = "Query with advanced filters for Patient, Unit, Status (ACTIVE, DISCHARGED) and Dates.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Hospitalizations list retrieved."),
                    @ApiResponse(responseCode = "500", description = "Internal query error.")
            }
    )
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<PageResponseDto<HospitalizationDto>> getPaginatedHospitalizations(
            @Parameter(description = "Search criteria for interned census.") HospitalizationSearchDto searchDto,
            @Parameter(description = "Result index.", example = "0") @RequestParam(value = "pageIndex", defaultValue = "0", required = false) Integer pageIndex,
            @Parameter(description = "Records per block.", example = "10") @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize
    );

    @Operation(
            summary = "Confirm medical discharge",
            description = "Formally ends the hospitalization process, releases the bed, and records final treatment summary.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Discharge processed and bed released successfully."),
                    @ApiResponse(responseCode = "400", description = "Invalid discharge request."),
                    @ApiResponse(responseCode = "404", description = "Internment episode not found."),
                    @ApiResponse(responseCode = "500", description = "Internal server error.")
            }
    )
    @PostMapping(value = ID + "/discharge", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<HospitalizationDto> dischargePatient(
            @Parameter(description = "Unique ID of the internment.", required = true) @PathVariable("id") Long id,
            @Parameter(description = "Professional summary of discharge treatment and epicrisis.", required = true) @RequestBody String dischargeSummary
    );
}
