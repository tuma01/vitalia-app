package com.amachi.app.vitalia.medical.infrastructure.controller;

import com.amachi.app.core.common.dto.PageResponseDto;
import com.amachi.app.vitalia.medical.infrastructure.dto.RoomDto;
import com.amachi.app.vitalia.medical.infrastructure.dto.search.RoomSearchDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Infraestructura - Habitaciones", description = "Gestion de espacios fisicos de atencion (Habitaciones, Boxes, Salas de Cirugia).")
public interface RoomApi {
    String NAME_API = "Habitacion/Box";

    @Operation(
            summary = "Obtener " + NAME_API + " por ID",
            description = "Devuelve los detalles físicos de la habitación, incluyendo número, tipo y estado de privacidad.",
            responses = {
                    @ApiResponse(responseCode = "200", description = NAME_API + " localizada con exito."),
                    @ApiResponse(responseCode = "404", description = NAME_API + " no existe en el sistema."),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor.")
            }
    )
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<RoomDto> getRoomById(
            @Parameter(description = "ID unico de la " + NAME_API, required = true, example = "701")
            @PathVariable Long id
    );

    @Operation(
            summary = "Crear nueva " + NAME_API,
            description = "Registra una nueva habitación o consultorio vinculado a una unidad departamental.",
            responses = {
                    @ApiResponse(responseCode = "201", description = NAME_API + " creada con exito."),
                    @ApiResponse(responseCode = "400", description = "Datos de entrada invalidos."),
                    @ApiResponse(responseCode = "500", description = "Error al intentar crear el espacio fisico.")
            }
    )
    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<RoomDto> createRoom(
            @Parameter(description = "Detalles tecnicos de la " + NAME_API + " a registrar.", required = true)
            @Valid @RequestBody RoomDto dto
    );

    @Operation(
            summary = "Actualizar " + NAME_API + " por ID",
            description = "Modifica las caracteristicas de la habitacion (Ej: Cambio de Box a Habitacion Privada).",
            responses = {
                    @ApiResponse(responseCode = "200", description = NAME_API + " actualizada con exito."),
                    @ApiResponse(responseCode = "404", description = NAME_API + " no encontrada."),
                    @ApiResponse(responseCode = "500", description = "Error critico de actualizacion.")
            }
    )
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<RoomDto> updateRoom(
            @Parameter(description = "ID de la " + NAME_API + " a modificar.", required = true)
            @PathVariable Long id,
            @Parameter(description = "Nuevos datos de la " + NAME_API + ".", required = true)
            @Valid @RequestBody RoomDto dto
    );

    @Operation(
            summary = "Eliminar " + NAME_API + " por ID",
            description = "Remueve una unidad de espacio fisico del inventario activo (Baja definitiva).",
            responses = {
                    @ApiResponse(responseCode = "204", description = NAME_API + " eliminada exitosamente."),
                    @ApiResponse(responseCode = "400", description = "Solicitud invalida (Ej: Habitacion con camas ocupadas)."),
                    @ApiResponse(responseCode = "404", description = NAME_API + " no existe."),
                    @ApiResponse(responseCode = "500", description = "Error del sistema.")
            }
    )
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> deleteRoom(
            @Parameter(description = "ID de la " + NAME_API + " a eliminar.", required = true)
            @PathVariable Long id
    );

    @Operation(
            summary = "Buscador de Habitaciones con Filtros Avanzados",
            description = "Consulta paginada por Numero, Unidad Superior y Nivel de Privacidad.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Listado de habitaciones recuperado."),
                    @ApiResponse(responseCode = "500", description = "Error interno de consulta.")
            }
    )
    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<PageResponseDto<RoomDto>> getPaginatedRooms(
            @Parameter(description = "Criterios de filtrado.") RoomSearchDto searchDto,
            @Parameter(description = "Indice de pagina.", example = "0") @RequestParam(defaultValue = "0") Integer pageIndex,
            @Parameter(description = "Resultados por pagina.", example = "10") @RequestParam(defaultValue = "10") Integer pageSize
    );
}
