package com.amachi.app.vitalia.medical.nurse.controller;

import com.amachi.app.core.common.controller.GenericApi;
import com.amachi.app.core.common.dto.PageResponseDto;
import com.amachi.app.vitalia.medical.nurse.dto.NurseDto;
import com.amachi.app.vitalia.medical.nurse.dto.search.NurseSearchDto;
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
 * Interfaz de contrato para la gestion del personal de enfermeria.
 */
@Tag(name = "Personal - Enfermeria", description = "REST API para gestionar perfiles de enfermeros, rangos y asignaciones.")
public interface NurseApi extends GenericApi<NurseDto> {
    String NAME_API = "Enfermero/a";

    @Operation(
            summary = "Obtener un " + NAME_API + " por ID",
            description = "Devuelve el perfil operativo del personal de enfermeria, incluyendo rango, habilidades y unidad departamental.",
            responses = {
                    @ApiResponse(responseCode = "200", description = NAME_API + " encontrado con exito."),
                    @ApiResponse(responseCode = "400", description = "Solicitud invalida: ID nulo o mal formado."),
                    @ApiResponse(responseCode = "404", description = NAME_API + " no registrado."),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor.")
            }
    )
    @GetMapping(value = ID, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<NurseDto> getNurseById(
            @Parameter(description = "Identificador unico de " + NAME_API, required = true, example = "3001")
            @PathVariable("id") Long id
    );

    @Operation(
            summary = "Registrar nuevo personal de enfermeria",
            description = "Crea un registro de enfermeria con rango operativo y adscripcion organizacional.",
            responses = {
                    @ApiResponse(responseCode = "201", description = NAME_API + " registrado con exito."),
                    @ApiResponse(responseCode = "400", description = "Datos de entrada no cumplen los estandares clinicos."),
                    @ApiResponse(responseCode = "500", description = "Error al intentar guardar el perfil.")
            }
    )
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<NurseDto> createNurse(
            @Parameter(description = "Detalles profesionales del " + NAME_API + " a crear.", required = true)
            @Valid @RequestBody NurseDto dto
    );

    @Operation(
            summary = "Actualizar perfil de enfermeria por ID",
            description = "Modifica los datos operativos, habilidades especializadas o turnos de trabajo.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Perfil de enfermeria actualizado con exito."),
                    @ApiResponse(responseCode = "400", description = "Solicitud mal formada."),
                    @ApiResponse(responseCode = "404", description = NAME_API + " no existe."),
                    @ApiResponse(responseCode = "500", description = "Error interno - Reintente mas tarde.")
            }
    )
    @PutMapping(value = ID, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<NurseDto> updateNurse(
            @Parameter(description = "ID del " + NAME_API + " a actualizar.", required = true)
            @PathVariable("id") Long id,
            @Parameter(description = "Nuevos datos del " + NAME_API + ".", required = true)
            @Valid @RequestBody NurseDto dto
    );

    @Operation(
            summary = "Eliminar personal de enfermeria por ID",
            description = "Baja definitiva del personal de enfermeria del sistema hospitalario.",
            responses = {
                    @ApiResponse(responseCode = "204", description = NAME_API + " dado de baja exitosamente."),
                    @ApiResponse(responseCode = "404", description = NAME_API + " no encontrado."),
                    @ApiResponse(responseCode = "500", description = "Error del sistema.")
            }
    )
    @DeleteMapping(value = ID, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> deleteNurse(
            @Parameter(description = "ID del " + NAME_API + " a eliminar.", required = true)
            @PathVariable("id") Long id
    );

    @Operation(
            summary = "Listado paginado de Personal de Enfermeria",
            description = "Consulta masiva con filtros de Rango, Unidad Colegiada y Especialidad.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Listado recuperado correctamente."),
                    @ApiResponse(responseCode = "500", description = "Error interno de consulta.")
            }
    )
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<PageResponseDto<NurseDto>> getPaginatedNurses(
            @Parameter(description = "Criterios de busqueda.") NurseSearchDto searchDto,
            @Parameter(description = "Indice de pagina.", example = "0") @RequestParam(value = "pageIndex", defaultValue = "0", required = false) Integer pageIndex,
            @Parameter(description = "Registros por pagina.", example = "10") @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize
    );
}
