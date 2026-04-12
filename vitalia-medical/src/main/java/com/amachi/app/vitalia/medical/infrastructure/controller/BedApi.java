package com.amachi.app.vitalia.medical.infrastructure.controller;

import com.amachi.app.core.common.dto.PageResponseDto;
import com.amachi.app.vitalia.medical.infrastructure.dto.BedDto;
import com.amachi.app.vitalia.medical.infrastructure.dto.search.BedSearchDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Infraestructura - Camas", description = "Gestion integral del activo critico de camas hospitalarias y disponibilidad en tiempo real.")
public interface BedApi {
    String NAME_API = "Cama Hospitalaria";

    @Operation(
            summary = "Obtener " + NAME_API + " por ID",
            description = "Devuelve el estado operativo de una cama y su vinculacion con habitacion y unidad.",
            responses = {
                    @ApiResponse(responseCode = "200", description = NAME_API + " localizada con exito."),
                    @ApiResponse(responseCode = "404", description = NAME_API + " no existe."),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor.")
            }
    )
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<BedDto> getBedById(
            @Parameter(description = "ID unico de la " + NAME_API, required = true, example = "801")
            @PathVariable Long id
    );

    @Operation(
            summary = "Alta de Nueva " + NAME_API,
            description = "Registra una cama fisica en el inventario hospitalario asignandola a una habitacion.",
            responses = {
                    @ApiResponse(responseCode = "201", description = NAME_API + " registrada con exito."),
                    @ApiResponse(responseCode = "400", description = "Solicitud invalida."),
                    @ApiResponse(responseCode = "500", description = "Error del servidor.")
            }
    )
    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<BedDto> createBed(
            @Parameter(description = "Detalles de la " + NAME_API + " a registrar.", required = true)
            @Valid @RequestBody BedDto dto
    );

    @Operation(
            summary = "Actualizar Estado/Datos de " + NAME_API,
            description = "Permite modificar el estado de disponibilidad o las caracteristicas de la cama.",
            responses = {
                    @ApiResponse(responseCode = "200", description = NAME_API + " actualizada correctamente."),
                    @ApiResponse(responseCode = "400", description = "Datos de entrada corruptos."),
                    @ApiResponse(responseCode = "404", description = NAME_API + " no encontrada."),
                    @ApiResponse(responseCode = "500", description = "Error critico de actualizacion.")
            }
    )
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<BedDto> updateBed(
            @Parameter(description = "ID de la " + NAME_API + " a modificar.", required = true)
            @PathVariable Long id,
            @Parameter(description = "Nuevos datos de la " + NAME_API + ".", required = true)
            @Valid @RequestBody BedDto dto
    );

    @Operation(
            summary = "Eliminar " + NAME_API + " por ID",
            description = "Remueve una cama del inventario hospitalario (Baja operativa).",
            responses = {
                    @ApiResponse(responseCode = "204", description = NAME_API + " eliminada exitosamente."),
                    @ApiResponse(responseCode = "404", description = NAME_API + " no existe."),
                    @ApiResponse(responseCode = "500", description = "Error del sistema.")
            }
    )
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> deleteBed(
            @Parameter(description = "ID de la " + NAME_API + " a eliminar.", required = true)
            @PathVariable Long id
    );

    @Operation(
            summary = "Censo de Camas en Tiempo Real",
            description = "Buscador avanzado con filtros por Estado (AVAILABLE, OCCUPIED, MAINTENANCE) y Habitacion.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Censo de camas recuperado con exito."),
                    @ApiResponse(responseCode = "500", description = "Error interno de consulta.")
            }
    )
    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<PageResponseDto<BedDto>> getPaginatedBeds(
            @Parameter(description = "Criterios de busqueda para el censo.") BedSearchDto searchDto,
            @Parameter(description = "Indice de pagina.", example = "0") @RequestParam(defaultValue = "0") Integer pageIndex,
            @Parameter(description = "Registros por pagina.", example = "10") @RequestParam(defaultValue = "10") Integer pageSize
    );
}
