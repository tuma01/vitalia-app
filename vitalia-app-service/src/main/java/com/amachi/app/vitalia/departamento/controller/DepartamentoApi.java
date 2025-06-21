package com.amachi.app.vitalia.departamento.controller;

import com.amachi.app.vitalia.common.controller.GenericApi;
import com.amachi.app.vitalia.common.dto.PageResponseDto;
import com.amachi.app.vitalia.departamento.dto.DepartamentoDto;
import com.amachi.app.vitalia.departamento.dto.search.DepartamentoSearchDto;
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

@Tag(name = "Departamento", description = "REST API para gestionar detalles de departamentos: crear, actualizar, obtener y eliminar.")
public interface DepartamentoApi extends GenericApi<DepartamentoDto> {

    @Operation(
            summary = "Obtener un departamento por ID",
            description = "Devuelve un objeto Departamento por ID especificado.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Departamento encontrado con éxito."),
                    @ApiResponse(responseCode = "404", description = "Departamento no encontrado."),
                    @ApiResponse(responseCode = "500", description = "Error del servidor.")
            }
    )
    @GetMapping(value = ID, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<DepartamentoDto> getDepartamentoById(
            @Parameter(description = "ID del departamento a recuperar.", required = true)
            @PathVariable("id") Long id
    );

    @Operation(
            summary = "Crear un departamento",
            description = "Crea un nuevo departamento usando los datos proporcionados en el cuerpo de la solicitud.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Departamento creado con éxito."),
                    @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos."),
                    @ApiResponse(responseCode = "500", description = "Error del servidor.")
            }
    )
    @PostMapping( produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<DepartamentoDto> createDepartamento(
            @Parameter(description = "Detalles del departamento a crear.", required = true)
            @Valid @RequestBody DepartamentoDto dto
    );

    @Operation(
            summary = "Actualizar un departamento por ID",
            description = "Actualiza un departamento existente usando su ID y los datos proporcionados.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Departamento actualizado con éxito."),
                    @ApiResponse(responseCode = "404", description = "Departamento no encontrado."),
                    @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos."),
                    @ApiResponse(responseCode = "500", description = "Error del servidor.")
            }
    )
    @PutMapping(value = ID, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<DepartamentoDto> updateDepartamento(
            @Parameter(description = "ID del departamento a actualizar.", required = true)
            @PathVariable("id") Long id,
            @Parameter(description = "Nuevos detalles del departamento.", required = true)
            @Valid @RequestBody DepartamentoDto dto
    );

    @Operation(
            summary = "Eliminar un departamento por ID",
            description = "Elimina un departamento existente usando su ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Departamento eliminado con éxito."),
                    @ApiResponse(responseCode = "404", description = "Departamento no encontrado."),
                    @ApiResponse(responseCode = "500", description = "Error del servidor.")
            }
    )
    @DeleteMapping(value = ID, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> deleteDepartamento(
            @Parameter(description = "ID del departamento a eliminar.", required = true)
            @PathVariable("id") Long id
    );

    @Operation(
            summary = "Obtener todos los departamentos",
            description = "Devuelve la lista completa de departamentos.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de departamentos recuperada con éxito."),
                    @ApiResponse(responseCode = "500", description = "Error del servidor.")
            }
    )
    @GetMapping(value = ALL, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<DepartamentoDto>> getAllDepartamentos();

    @Operation(
            summary = "Obtener departamentos con paginación",
            description = "Devuelve una lista de departamentos paginada.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de departamentos recuperada con éxito."),
                    @ApiResponse(responseCode = "400", description = "Parámetros de paginación inválidos."),
                    @ApiResponse(responseCode = "500", description = "Error del servidor.")
            }
    )
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<PageResponseDto<DepartamentoDto>> getPaginatedDepartamentos(DepartamentoSearchDto searchDto,
                                                                     @Parameter(description = "Índice de la página a recuperar.", example = "0") @RequestParam(value = "pageIndex", defaultValue = "0", required = false)final Integer pageIndex,
                                                                     @Parameter(description = "Tamaño de la página.", example = "10") @RequestParam(value = "pageSize", defaultValue = "10", required = false)final Integer pageSize
    );
}