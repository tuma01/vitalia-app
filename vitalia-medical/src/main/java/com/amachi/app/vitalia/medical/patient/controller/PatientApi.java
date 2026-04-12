package com.amachi.app.vitalia.medical.patient.controller;

import com.amachi.app.core.common.controller.GenericApi;
import com.amachi.app.core.common.dto.PageResponseDto;
import com.amachi.app.vitalia.medical.patient.dto.PatientDto;
import com.amachi.app.vitalia.medical.patient.dto.search.PatientSearchDto;
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
 * Interfaz de contrato para la gestion del expediente maestro del paciente.
 * Sigue el patron profesional y homogeneo de Vitalia.
 */
@Tag(name = "Paciente", description = "REST API para el ciclo de vida del paciente: Registro, Internacion y Seguimiento.")
public interface PatientApi extends GenericApi<PatientDto> {
    String NAME_API = "Paciente";

    @Operation(
            summary = "Obtener un " + NAME_API + " por ID",
            description = "Devuelve el perfil completo del " + NAME_API + " incluyendo NHC y estado clinico.",
            responses = {
                    @ApiResponse(responseCode = "200", description = NAME_API + " encontrado con exito."),
                    @ApiResponse(responseCode = "400", description = "Solicitud invalida: ID nulo o datos incompletos."),
                    @ApiResponse(responseCode = "404", description = NAME_API + " no encontrado."),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor.")
            }
    )
    @GetMapping(value = ID, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<PatientDto> getPatientById(
            @Parameter(description = "ID del " + NAME_API + " a recuperar", required = true)
            @PathVariable("id") Long id
    );

    @Operation(
            summary = "Registrar un nuevo " + NAME_API,
            description = "Crea un expediente maestro de " + NAME_API + " en el sistema.",
            responses = {
                    @ApiResponse(responseCode = "201", description = NAME_API + " registrado con exito."),
                    @ApiResponse(responseCode = "400", description = "Datos de entrada invalidos."),
                    @ApiResponse(responseCode = "500", description = "Error del servidor.")
            }
    )
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<PatientDto> createPatient(
            @Parameter(description = "Detalles del " + NAME_API + " a registrar.", required = true)
            @Valid @RequestBody PatientDto dto
    );

    @Operation(
            summary = "Actualizar perfil de " + NAME_API + " por ID",
            description = "Actualiza la informacion clinica y demografica de un " + NAME_API + " existente.",
            responses = {
                    @ApiResponse(responseCode = "200", description = NAME_API + " actualizado con exito."),
                    @ApiResponse(responseCode = "400", description = "Solicitud invalida."),
                    @ApiResponse(responseCode = "404", description = NAME_API + " no encontrado."),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor.")
            }
    )
    @PutMapping(value = ID, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<PatientDto> updatePatient(
            @Parameter(description = "ID del " + NAME_API + " a actualizar.", required = true)
            @PathVariable("id") Long id,
            @Parameter(description = "Nuevos datos del " + NAME_API + ".", required = true)
            @Valid @RequestBody PatientDto dto
    );

    @Operation(
            summary = "Eliminar expediente de " + NAME_API + " por ID",
            description = "Elimina un registro de " + NAME_API + " del sistema (Baja definitiva).",
            responses = {
                    @ApiResponse(responseCode = "204", description = NAME_API + " eliminado con exito."),
                    @ApiResponse(responseCode = "400", description = "Solicitud invalida."),
                    @ApiResponse(responseCode = "404", description = NAME_API + " no encontrado."),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor.")
            }
    )
    @DeleteMapping(value = ID, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> deletePatient(
            @Parameter(description = "ID del " + NAME_API + " a eliminar.", required = true)
            @PathVariable("id") Long id
    );

    @Operation(
            summary = "Buscador paginado de " + NAME_API + "s",
            description = "Busqueda avanzada por Nombre, NHC, ID Card y Estado.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de " + NAME_API + "s recuperada con exito."),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor.")
            }
    )
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<PageResponseDto<PatientDto>> getPaginatedPatients(
            PatientSearchDto searchDto,
            @Parameter(description = "Indice de la pagina.", example = "0") @RequestParam(value = "pageIndex", defaultValue = "0", required = false) Integer pageIndex,
            @Parameter(description = "Tamano de la pagina.", example = "10") @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize
    );
}
