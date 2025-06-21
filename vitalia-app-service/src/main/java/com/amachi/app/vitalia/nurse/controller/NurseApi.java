package com.amachi.app.vitalia.nurse.controller;

import com.amachi.app.vitalia.common.dto.PageResponseDto;
import com.amachi.app.vitalia.nurse.dto.NurseDto;
import com.amachi.app.vitalia.nurse.dto.search.NurseSearchDto;
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

@Tag(name = "Nurse", description = "Rest API Hospital APP to CREATE, UPDATE, FETCH and DELETE Nurse details")
public interface NurseApi {
    String nameAPi = "Nurse";

    @Operation(
            summary = nameAPi + " a obtener por ID",
            description = "Devuelve un objeto " + nameAPi + " por ID especificado.",
            responses = {
                    @ApiResponse(responseCode = "200", description = nameAPi + " encontrado con éxito."),
                    @ApiResponse(responseCode = "404", description = nameAPi + " no encontrado."),
                    @ApiResponse(responseCode = "500", description = "Error del servidor.")
            }
    )
    @GetMapping(value = ID, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<NurseDto> getNurseById(
            @Parameter(description = "ID  " + nameAPi + " a recuperar.", required = true)
            @PathVariable("id") Long id
    );

    @Operation(
            summary = nameAPi + " a crear",
            description = "Crea un nuevo " + nameAPi + " usando los datos proporcionados en el cuerpo de la solicitud.",
            responses = {
                    @ApiResponse(responseCode = "201", description = nameAPi + " creado con éxito."),
                    @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos."),
                    @ApiResponse(responseCode = "500", description = "Error del servidor.")
            }
    )
    @PostMapping( produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<NurseDto> createNurse(
            @Parameter(description = "Detalles del " + nameAPi + " a crear.", required = true)
            @Valid @RequestBody NurseDto dto
    );

    @Operation(
            summary = nameAPi + " a actualizar mediante el ID",
            description = "Actualiza " + nameAPi + " existente usando su ID y los datos proporcionados.",
            responses = {
                    @ApiResponse(responseCode = "200", description = nameAPi + " actualizado con éxito."),
                    @ApiResponse(responseCode = "404", description = nameAPi + " no encontrado."),
                    @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos."),
                    @ApiResponse(responseCode = "500", description = "Error del servidor.")
            }
    )
    @PutMapping(value = ID, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<NurseDto> updateNurse(
            @Parameter(description = "ID del " + nameAPi + " a actualizar.", required = true)
            @PathVariable("id") Long id,
            @Parameter(description = "Nuevos detalles del " +  nameAPi + ".", required = true)
            @Valid @RequestBody NurseDto dto
    );

    @Operation(
            summary = nameAPi + " a eliminar por ID",
            description = "Elimina un " + nameAPi + " existente usando su ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = nameAPi + " eliminado con éxito."),
                    @ApiResponse(responseCode = "404", description = nameAPi + " no encontrado."),
                    @ApiResponse(responseCode = "500", description = "Error del servidor.")
            }
    )
    @DeleteMapping(value = ID, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> deleteNurse(
            @Parameter(description = "ID del " + nameAPi + " a eliminar.", required = true)
            @PathVariable("id") Long id
    );

    @Operation(
            summary = "Obtener todos los " + nameAPi,
            description = "Devuelve la lista completa de " + nameAPi,
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de " + nameAPi + " recuperada con éxito."),
                    @ApiResponse(responseCode = "500", description = "Error del servidor.")
            }
    )
    @GetMapping(value = ALL, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<NurseDto>> getAllNurses();

    @Operation(
            summary = "Obtener una lista con paginación de " + nameAPi,
            description = "Devuelve una lista paginada de " + nameAPi,
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de nurseProfessionSpecialities recuperada con éxito."),
                    @ApiResponse(responseCode = "400", description = "Parámetros de paginación inválidos."),
                    @ApiResponse(responseCode = "500", description = "Error del servidor.")
            }
    )
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<PageResponseDto<NurseDto>> getPaginatedNurses(NurseSearchDto searchDto,
                                                                 @Parameter(description = "Índice de la página a recuperar.", example = "0") @RequestParam(value = "pageIndex", defaultValue = "0", required = false)final Integer pageIndex,
                                                                 @Parameter(description = "Tamaño de la página.", example = "10") @RequestParam(value = "pageSize", defaultValue = "10", required = false)final Integer pageSize
    );
}