package com.amachi.app.vitalia.medical.doctor.controller;

import com.amachi.app.core.common.controller.GenericApi;
import com.amachi.app.core.common.dto.PageResponseDto;
import com.amachi.app.vitalia.medical.doctor.dto.DoctorDto;
import com.amachi.app.vitalia.medical.doctor.dto.search.DoctorSearchDto;
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
 * Interfaz de contrato para la gestion del personal medico facultativo.
 */
@Tag(name = "Personal - Medicos", description = "REST API para gestionar perfiles medicos, especialidades y matriculas.")
public interface DoctorApi extends GenericApi<DoctorDto> {
    String NAME_API = "Medico";

    @Operation(
            summary = "Obtener un " + NAME_API + " por ID",
            description = "Devuelve el perfil profesional completo del facultativo, incluyendo especialidad, matricula y precio de consulta.",
            responses = {
                    @ApiResponse(responseCode = "200", description = NAME_API + " encontrado con exito."),
                    @ApiResponse(responseCode = "400", description = "Solicitud invalida: ID nulo o fuera de rango."),
                    @ApiResponse(responseCode = "404", description = NAME_API + " no encontrado en el hospital."),
                    @ApiResponse(responseCode = "500", description = "Error interno - Contacte a soporte IT.")
            }
    )
    @GetMapping(value = ID, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<DoctorDto> getDoctorById(
            @Parameter(description = "Identificador unico interno del " + NAME_API, required = true, example = "2001")
            @PathVariable("id") Long id
    );

    @Operation(
            summary = "Registrar un " + NAME_API + " Facultativo",
            description = "Crea un registro maestro de personal medico sincronizado con RRHH y especialidades.",
            responses = {
                    @ApiResponse(responseCode = "201", description = NAME_API + " creado y matriculado con exito."),
                    @ApiResponse(responseCode = "400", description = "Error de validacion en los datos medicos."),
                    @ApiResponse(responseCode = "500", description = "Error del servidor al persistir el perfil.")
            }
    )
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<DoctorDto> createDoctor(
            @Parameter(description = "Detalles integrales del " + NAME_API + " a crear.", required = true)
            @Valid @RequestBody DoctorDto dto
    );

    @Operation(
            summary = "Actualizar perfil de " + NAME_API + " por ID",
            description = "Modifica la informacion profesional, precio de consulta o disponibilidad del medico.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Perfil medico actualizado con exito."),
                    @ApiResponse(responseCode = "400", description = "Solicitud invalida o datos corruptos."),
                    @ApiResponse(responseCode = "404", description = NAME_API + " no localizado."),
                    @ApiResponse(responseCode = "500", description = "Error critico del sistema.")
            }
    )
    @PutMapping(value = ID, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<DoctorDto> updateDoctor(
            @Parameter(description = "ID del " + NAME_API + " a actualizar.", required = true)
            @PathVariable("id") Long id,
            @Parameter(description = "Nuevos datos profesionales del " + NAME_API + ".", required = true)
            @Valid @RequestBody DoctorDto dto
    );

    @Operation(
            summary = "Baja definitiva de un " + NAME_API,
            description = "Elimina el registro profesional del medico del sistema hospitalario.",
            responses = {
                    @ApiResponse(responseCode = "204", description = NAME_API + " dado de baja con exito."),
                    @ApiResponse(responseCode = "400", description = "Solicitud invalida (Ej: Medico con pacientes activos)."),
                    @ApiResponse(responseCode = "404", description = NAME_API + " no existe."),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor.")
            }
    )
    @DeleteMapping(value = ID, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> deleteDoctor(
            @Parameter(description = "ID del " + NAME_API + " a eliminar.", required = true)
            @PathVariable("id") Long id
    );

    @Operation(
            summary = "Buscador Experto de " + NAME_API + "s",
            description = "Busqueda avanzada por Nombre, Especialidad, Matricula y Disponibilidad.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de " + NAME_API + "s filtrada con exito."),
                    @ApiResponse(responseCode = "500", description = "Error de busqueda interna.")
            }
    )
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<PageResponseDto<DoctorDto>> getPaginatedDoctors(
            DoctorSearchDto searchDto,
            @Parameter(description = "Indice de pagina.", example = "0") @RequestParam(value = "pageIndex", defaultValue = "0", required = false) Integer pageIndex,
            @Parameter(description = "Registros por pagina.", example = "10") @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize
    );
}
