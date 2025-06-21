package com.amachi.app.vitalia.provincia.controller;

import com.amachi.app.vitalia.common.controller.GenericApi;
import com.amachi.app.vitalia.common.dto.PageResponseDto;
import com.amachi.app.vitalia.provincia.dto.ProvinciaDto;
import com.amachi.app.vitalia.provincia.dto.search.ProvinciaSearchDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.amachi.app.vitalia.common.controller.BaseController.*;

@Tag(name = "Provincia", description = "REST API para gestionar detalles de provincias: crear, actualizar, obtener y eliminar.")
public interface ProvinciaApi extends GenericApi<ProvinciaDto> {
    String nameAPi = "Provincia";

    @Operation(
            summary = "Obtener una provincia por ID",
            description = "Devuelve un objeto Provincia por ID especificado.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Provincia encontrado con éxito."),
                    @ApiResponse(responseCode = "404", description = "Provincia no encontrado."),
                    @ApiResponse(responseCode = "500", description = "Error del servidor.")
            }
    )
    @GetMapping(value = ID, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ProvinciaDto> getProvinciaById(
            @Parameter(description = "ID de la provincia a recuperar.", required = true)
            @PathVariable("id") Long id
    );

    @Operation(
            summary = "Crear una provincia",
            description = "Crea una nueva provincia usando los datos proporcionados en el cuerpo de la solicitud.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Provincia creado con éxito."),
                    @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos."),
                    @ApiResponse(responseCode = "500", description = "Error del servidor.")
            }
    )
    @PostMapping( produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ProvinciaDto> createProvincia(
            @Parameter(description = "Detalles de la provincia a crear.", required = true)
            @Valid @RequestBody ProvinciaDto dto
    );

    @Operation(
            summary = "Actualizar una provincia por ID",
            description = "Actualiza una provincia existente usando su ID y los datos proporcionados.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Provincia actualizado con éxito."),
                    @ApiResponse(responseCode = "404", description = "Provincia no encontrado."),
                    @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos."),
                    @ApiResponse(responseCode = "500", description = "Error del servidor.")
            }
    )
    @PutMapping(value = ID, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ProvinciaDto> updateProvincia(
            @Parameter(description = "ID de la provincia a actualizar.", required = true)
            @PathVariable("id") Long id,
            @Parameter(description = "Nuevos detalles de la provincia.", required = true)
            @Valid @RequestBody ProvinciaDto dto
    );

    @Operation(
            summary = "Eliminar una provincia por ID",
            description = "Elimina una provincia existente usando su ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Provincia eliminado con éxito."),
                    @ApiResponse(responseCode = "404", description = "Provincia no encontrado."),
                    @ApiResponse(responseCode = "500", description = "Error del servidor.")
            }
    )
    @DeleteMapping(value = ID, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> deleteProvincia(
            @Parameter(description = "ID de la provincia a eliminar.", required = true)
            @PathVariable("id") Long id
    );

    @Operation(
            summary = "Obtener todos las provincias",
            description = "Devuelve la lista completa de provincias.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de provincias recuperada con éxito."),
                    @ApiResponse(responseCode = "500", description = "Error del servidor.")
            }
    )
    @GetMapping(value = ALL, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<ProvinciaDto>> getAllProvincias();

    @Operation(
            summary = "Obtener provincias con paginación",
            description = "Devuelve una lista de provincias paginada.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de provincias recuperada con éxito."),
                    @ApiResponse(responseCode = "400", description = "Parámetros de paginación inválidos."),
                    @ApiResponse(responseCode = "500", description = "Error del servidor.")
            }
    )
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<PageResponseDto<ProvinciaDto>> getPaginatedProvincias(ProvinciaSearchDto searchDto,
                                                                      @Parameter(description = "Índice de la página a recuperar.", example = "0") @RequestParam(value = "pageIndex", defaultValue = "0", required = false)final Integer pageIndex,
                                                                      @Parameter(description = "Tamaño de la página.", example = "10") @RequestParam(value = "pageSize", defaultValue = "10", required = false)final Integer pageSize
    );
}