package com.amachi.app.vitalia.doctor.controller;

import com.amachi.app.vitalia.common.dto.PageResponseDto;

import com.amachi.app.vitalia.doctor.dto.DoctorDto;
import com.amachi.app.vitalia.doctor.dto.search.DoctorSearchDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.amachi.app.vitalia.common.controller.BaseController.ALL;
import static com.amachi.app.vitalia.common.controller.BaseController.ID;

@Tag(name = "Doctor", description = "Rest Hospital APP to CREATE, UPDATE, FETCH and DELETE Doctor details")
public interface DoctorApi {

    String API_NAME = "Doctor";
    String API_NAME_PLURAL = "Doctores";

    /**
     * Retrieves a doctor by ID.
     */
    @Operation(
            summary = "Obtener " + API_NAME + " por ID",
            description = "Devuelve un objeto " + API_NAME + " por ID especificado.",
            responses = {
                    @ApiResponse(responseCode = "200", description = API_NAME + " encontrado con éxito."),
                    @ApiResponse(responseCode = "404", description = API_NAME + " no encontrado."),
                    @ApiResponse(responseCode = "500", description = "Error del servidor.")
            }
    )
    @GetMapping(value = ID, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<DoctorDto> getDoctorById(
            @Parameter(description = "ID del " + API_NAME + " a recuperar.", required = true)
            @PathVariable("id") Long id
    );

    /**
     * Creates a new doctor.
     */
    @Operation(
            summary = "Crear nuevo " + API_NAME,
            description = "Crea un nuevo " + API_NAME + " usando los datos proporcionados.",
            responses = {
                    @ApiResponse(responseCode = "201", description = API_NAME + " creado con éxito."),
                    @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos."),
                    @ApiResponse(responseCode = "500", description = "Error del servidor.")
            }
    )
    @PostMapping( produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<DoctorDto> createDoctor(
            @Parameter(description = "Detalles del " + API_NAME + " a crear.", required = true)
            @Valid @RequestBody DoctorDto dto
    );

    /**
     * Updates an existing doctor.
     */
    @Operation(
            summary = "Actualizar " + API_NAME + " existente",
            description = "Actualiza un " + API_NAME + " existente por ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = API_NAME + " actualizado con éxito."),
                    @ApiResponse(responseCode = "404", description = API_NAME + " no encontrado."),
                    @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos."),
                    @ApiResponse(responseCode = "500", description = "Error del servidor.")
            }
    )
    @PutMapping(value = ID, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<DoctorDto> updateDoctor(
            @Parameter(description = "ID del " + API_NAME + " a actualizar.", required = true)
            @PathVariable("id") Long id,
            @Parameter(description = "Nuevos datos del " + API_NAME + ".", required = true)
            @Valid @RequestBody DoctorDto dto
    );

    /**
     * Deletes a doctor by ID.
     */
    @Operation(
            summary = "Eliminar " + API_NAME + " por ID",
            description = "Elimina un " + API_NAME + " existente por su ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = API_NAME + " eliminado con éxito."),
                    @ApiResponse(responseCode = "404", description = API_NAME + " no encontrado."),
                    @ApiResponse(responseCode = "500", description = "Error del servidor.")
            }
    )
    @DeleteMapping(value = ID, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> deleteDoctor(
            @Parameter(description = "ID del " + API_NAME + " a eliminar.", required = true)
            @PathVariable("id") Long id
    );

    /**
     * Retrieves all doctors without pagination.
     */
    @Operation(
            summary = "Obtener todos los " + API_NAME_PLURAL,
            description = "Devuelve la lista completa de " + API_NAME_PLURAL + ".",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de " + API_NAME_PLURAL + " recuperada con éxito."),
                    @ApiResponse(responseCode = "500", description = "Error del servidor.")
            }
    )
    @GetMapping(value = ALL, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<DoctorDto>> getAllDoctors();

    /**
     * Retrieves a paginated list of doctors based on filters.
     */
    @Operation(
            summary = "Obtener " + API_NAME_PLURAL + " con paginación",
            description = "Devuelve una lista paginada de " + API_NAME_PLURAL + " basada en criterios de búsqueda.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista paginada de " + API_NAME_PLURAL + " recuperada con éxito."),
                    @ApiResponse(responseCode = "400", description = "Parámetros de paginación inválidos."),
                    @ApiResponse(responseCode = "500", description = "Error del servidor.")
            }
    )
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<PageResponseDto<DoctorDto>> getPaginatedDoctors(
                                                                   @Parameter(description = "Criterios de búsqueda") DoctorSearchDto searchDto,
                                                                   @Parameter(description = "Índice de página", example = "0") @RequestParam(value = "pageIndex", defaultValue = "0") Integer pageIndex,
                                                                   @Parameter(description = "Tamaño de página", example = "10") @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    );
}