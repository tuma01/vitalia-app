package com.amachi.app.vitalia.medical.employee.controller;

import com.amachi.app.core.common.controller.GenericApi;
import com.amachi.app.core.common.dto.PageResponseDto;
import com.amachi.app.vitalia.medical.employee.dto.EmployeeDto;
import com.amachi.app.vitalia.medical.employee.dto.search.EmployeeSearchDto;
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
 * Interfaz de contrato para la gestion del personal administrativo y soporte.
 */
@Tag(name = "Personal - Administrativos", description = "REST API para gestionar perfiles de empleados de soporte, cargos y states.")
public interface EmployeeApi extends GenericApi<EmployeeDto> {
    String NAME_API = "Empleado";

    @Operation(
            summary = "Obtener un " + NAME_API + " por ID",
            description = "Devuelve el perfil administrativo integral, incluyendo cargo, sueldo y contacto hospitalario.",
            responses = {
                    @ApiResponse(responseCode = "200", description = NAME_API + " encontrado con exito."),
                    @ApiResponse(responseCode = "400", description = "Solicitud invalida."),
                    @ApiResponse(responseCode = "404", description = NAME_API + " no localizado."),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor.")
            }
    )
    @GetMapping(value = ID, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<EmployeeDto> getEmployeeById(
            @Parameter(description = "Identificador unico interno del " + NAME_API, required = true, example = "4001")
            @PathVariable("id") Long id
    );

    @Operation(
            summary = "Registrar nuevo personal administrativo",
            description = "Crea un perfil de empleado con asignacion de cargo y state organizacional.",
            responses = {
                    @ApiResponse(responseCode = "201", description = NAME_API + " registrado en nomina con exito."),
                    @ApiResponse(responseCode = "400", description = "Error de validacion en datos de contrato."),
                    @ApiResponse(responseCode = "500", description = "Error al intentar crear el registro.")
            }
    )
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<EmployeeDto> createEmployee(
            @Parameter(description = "Detalles administrativos del " + NAME_API + " a registrar.", required = true)
            @Valid @RequestBody EmployeeDto dto
    );

    @Operation(
            summary = "Actualizar perfil de empleado administrativo por ID",
            description = "Permite modificar datos de contrato, cargo o informacion de contacto personal.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Perfil administrativo actualizado con exito."),
                    @ApiResponse(responseCode = "404", description = NAME_API + " no encontrado."),
                    @ApiResponse(responseCode = "500", description = "Error critico de actualizacion.")
            }
    )
    @PutMapping(value = ID, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<EmployeeDto> updateEmployee(
            @Parameter(description = "ID del " + NAME_API + " a actualizar.", required = true)
            @PathVariable("id") Long id,
            @Parameter(description = "Nuevos datos administrativos del " + NAME_API + ".", required = true)
            @Valid @RequestBody EmployeeDto dto
    );

    @Operation(
            summary = "Baja definitiva de personal administrativo por ID",
            description = "Elimina de forma permanente el registro del empleado administrativo del sistema.",
            responses = {
                    @ApiResponse(responseCode = "204", description = NAME_API + " dado de baja exitosamente."),
                    @ApiResponse(responseCode = "404", description = NAME_API + " no existe."),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor.")
            }
    )
    @DeleteMapping(value = ID, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> deleteEmployee(
            @Parameter(description = "ID del " + NAME_API + " a eliminar.", required = true)
            @PathVariable("id") Long id
    );

    @Operation(
            summary = "Buscador Paginado de Personal Administrativo",
            description = "Consulta con filtros avanzados de Cargo, Estado Laboral y Unidad Organizacional.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Listado administrativo recuperado."),
                    @ApiResponse(responseCode = "500", description = "Error interno de consulta.")
            }
    )
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<PageResponseDto<EmployeeDto>> getPaginatedEmployees(
            @Parameter(description = "Criterios de seleccion.") EmployeeSearchDto searchDto,
            @Parameter(description = "Indice de resultados.", example = "0") @RequestParam(value = "pageIndex", defaultValue = "0", required = false) Integer pageIndex,
            @Parameter(description = "Registros por bloque.", example = "10") @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize
    );
}

