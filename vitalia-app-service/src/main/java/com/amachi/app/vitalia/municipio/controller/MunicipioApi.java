package com.amachi.app.vitalia.municipio.controller;

import com.amachi.app.vitalia.common.controller.GenericApi;
import com.amachi.app.vitalia.common.dto.PageResponseDto;
import com.amachi.app.vitalia.municipio.dto.MunicipioDto;
import com.amachi.app.vitalia.municipio.dto.search.MunicipioSearchDto;
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

@Tag(name = "Municipio", description = "REST API para gestionar detalles del municipio: crear, actualizar, obtener y eliminar.")
public interface MunicipioApi extends GenericApi<MunicipioDto> {
    String NAME_API = "Municipio";

    @Operation(
            summary = "Obtener una municipio por ID",
            description = "Devuelve un objeto Municipio por ID especificado.")
    @ApiResponse(responseCode = "200", description = "Municipio encontrado con éxito.")
    @ApiResponse(responseCode = "404", description = "Municipio no encontrado.")
    @ApiResponse(responseCode = "500", description = "Error del servidor.")
    @GetMapping(value = ID, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<MunicipioDto> getMunicipioById(
            @Parameter(description = "ID del municipio a recuperar.", required = true)
            @PathVariable("id") Long id
    );

    @Operation(
            summary = "Crear un municipio",
            description = "Crea un nuevo municipio usando los datos proporcionados en el cuerpo de la solicitud."
    )
    @ApiResponse(responseCode = "201", description = "Municipio creado con éxito.")
    @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos.")
    @ApiResponse(responseCode = "500", description = "Error del servidor.")
    @PostMapping( produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<MunicipioDto> createMunicipio(
            @Parameter(description = "Detalles del municipio a crear.", required = true)
            @Valid @RequestBody MunicipioDto dto
    );

    @Operation(
            summary = "Actualizar un municipio por ID",
            description = "Actualiza un municipio existente usando su ID y los datos proporcionados.")
    @ApiResponse(responseCode = "200", description = "Municipio actualizado con éxito.")
    @ApiResponse(responseCode = "404", description = "Municipio no encontrado.")
    @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos.")
    @ApiResponse(responseCode = "500", description = "Error del servidor.")
    @PutMapping(value = ID, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<MunicipioDto> updateMunicipio(
            @Parameter(description = "ID del municipio a actualizar.", required = true)
            @PathVariable("id") Long id,
            @Parameter(description = "Nuevos detalles del municipio.", required = true)
            @Valid @RequestBody MunicipioDto dto
    );

    @Operation(
            summary = "Eliminar un municipio por ID",
            description = "Elimina un municipio existente usando su ID.")
    @ApiResponse(responseCode = "200", description = "Municipio eliminado con éxito.")
    @ApiResponse(responseCode = "404", description = "Municipio no encontrado.")
    @ApiResponse(responseCode = "500", description = "Error del servidor.")
    @DeleteMapping(value = ID, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> deleteMunicipio(
            @Parameter(description = "ID del municipio a eliminar.", required = true)
            @PathVariable("id") Long id
    );

    @Operation(
            summary = "Obtener todos los municipios",
            description = "Devuelve la lista completa de municipios.")
    @ApiResponse(responseCode = "200", description = "Lista de municipios recuperada con éxito.")
    @ApiResponse(responseCode = "500", description = "Error del servidor.")
    @GetMapping(value = ALL, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<MunicipioDto>> getAllMunicipios();

    @Operation(
            summary = "Obtener municipios con paginación",
            description = "Devuelve una lista de municipios paginada.")
    @ApiResponse(responseCode = "200", description = "Lista de municipios recuperada con éxito.")
    @ApiResponse(responseCode = "400", description = "Parámetros de paginación inválidos.")
    @ApiResponse(responseCode = "500", description = "Error del servidor.")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<PageResponseDto<MunicipioDto>> getPaginatedMunicipios(MunicipioSearchDto searchDto,
                                                                        @Parameter(description = "Índice de la página a recuperar.", example = "0") @RequestParam(value = "pageIndex", defaultValue = "0", required = false)final Integer pageIndex,
                                                                        @Parameter(description = "Tamaño de la página.", example = "10") @RequestParam(value = "pageSize", defaultValue = "10", required = false)final Integer pageSize
    );
}