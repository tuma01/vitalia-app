package com.amachi.app.vitalia.medical.appointment.controller;

import com.amachi.app.core.common.controller.GenericApi;
import com.amachi.app.core.common.dto.PageResponseDto;
import com.amachi.app.core.common.enums.AppointmentStatus;
import com.amachi.app.vitalia.medical.appointment.dto.AppointmentDto;
import com.amachi.app.vitalia.medical.appointment.dto.search.AppointmentSearchDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import static com.amachi.app.core.common.controller.BaseController.ID;

/**
 * Interfaz de contrato para la gestion de agendas y citas medicas.
 */
@Tag(name = "Operaciones - Citas Medicas", description = "REST API para el workflow de agendas: Programacion, Confirmacion y Atención Clinica.")
public interface AppointmentApi extends GenericApi<AppointmentDto> {
    String NAME_API = "Cita Medica";

    @Operation(
            summary = "Obtener detalle de la cita por ID",
            description = "Recupera la informacion agendada, incluyendo paciente, medico, especialidad y motivo de consulta.",
            responses = {
                    @ApiResponse(responseCode = "200", description = NAME_API + " localizada con exito."),
                    @ApiResponse(responseCode = "404", description = NAME_API + " no encontrada."),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor.")
            }
    )
    @GetMapping(value = ID, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<AppointmentDto> getAppointmentById(
            @Parameter(description = "ID unico de la " + NAME_API, required = true, example = "10001")
            @PathVariable("id") @NonNull Long id
    );

    @Operation(
            summary = "Programar una nueva Cita Medica",
            description = "Reserva un espacio en la agenda de un facultativo para una fecha y hora especifica.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Cita programada exitosamente."),
                    @ApiResponse(responseCode = "400", description = "Conflicto de agenda o datos invalidos."),
                    @ApiResponse(responseCode = "500", description = "Error del servidor al agendar.")
            }
    )
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<AppointmentDto> createAppointment(
            @Parameter(description = "Detalles de la cita a programar.", required = true)
            @Valid @RequestBody @NonNull AppointmentDto dto
    );

    @Operation(
            summary = "Actualizar datos de la cita agendada por ID",
            description = "Permite reprogramar la cita o modificar el motivo de consulta antes de la atencion.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Cita actualizada con exito."),
                    @ApiResponse(responseCode = "400", description = "Error en la solicitud de reprogramacion."),
                    @ApiResponse(responseCode = "404", description = "Cita no localizada."),
                    @ApiResponse(responseCode = "500", description = "Error critico de actualizacion.")
            }
    )
    @PutMapping(value = ID, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<AppointmentDto> updateAppointment(
            @Parameter(description = "ID de la cita a modificar.", required = true)
            @PathVariable("id") @NonNull Long id,
            @Parameter(description = "Nuevos parametros de la cita.", required = true)
            @Valid @RequestBody @NonNull AppointmentDto dto
    );

    @Operation(
            summary = "Buscador de Agendas y Citas",
            description = "Consulta paginada con filtros por Medico, Paciente, Rango de Fechas y Estado Operativo.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Listado de agenda recuperado correctamente."),
                    @ApiResponse(responseCode = "500", description = "Error interno de consulta.")
            }
    )
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<PageResponseDto<AppointmentDto>> getPaginatedAppointments(
            @Parameter(description = "Filtros de busqueda de agenda.") @NonNull AppointmentSearchDto searchDto,
            @Parameter(description = "Indice de resultados.", example = "0") @RequestParam(value = "pageIndex", defaultValue = "0", required = false) Integer pageIndex,
            @Parameter(description = "Resultados por bloque.", example = "10") @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize
    );

    @Operation(
            summary = "Actualizar estado operativo del Workflow de la Cita",
            description = "Cambia el estado de la cita (Ej: CONFIRMED, CANCELLED, NO_SHOW, ARRIVED).",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Workflow de la cita actualizado."),
                    @ApiResponse(responseCode = "400", description = "Transicion de estado no permitida."),
                    @ApiResponse(responseCode = "404", description = "Cita no encontrada."),
                    @ApiResponse(responseCode = "500", description = "Error del sistema.")
            }
    )
    @PatchMapping(value = ID + "/status", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<AppointmentDto> updateAppointmentStatus(
            @Parameter(description = "ID de la cita.", required = true) @PathVariable("id") @NonNull Long id,
            @Parameter(description = "Nuevo estado operativo de la cita.", required = true) @RequestParam("status") @NonNull AppointmentStatus status
    );
}
