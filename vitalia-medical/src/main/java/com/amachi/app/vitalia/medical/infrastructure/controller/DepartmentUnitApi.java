package com.amachi.app.vitalia.medical.infrastructure.controller;

import com.amachi.app.core.common.dto.PageResponseDto;
import com.amachi.app.vitalia.medical.infrastructure.dto.DepartmentUnitDto;
import com.amachi.app.vitalia.medical.infrastructure.dto.search.DepartmentUnitSearchDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Infraestructura - Unidades Hospitalarias", description = "Gestion operativa de pabellones, alas fisicas y servicios del hospital.")
public interface DepartmentUnitApi {
    String NAME_API = "Unidad Hospitalaria";

    @Operation(
            summary = "Obtener " + NAME_API + " por ID",
            description = "Devuelve los detalles de una unidad, incluyendo capacidad, piso y responsable.",
            responses = {
                    @ApiResponse(responseCode = "200", description = NAME_API + " encontrada con exito."),
                    @ApiResponse(responseCode = "404", description = NAME_API + " no existe en la infraestructura."),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor.")
            }
    )
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<DepartmentUnitDto> getUnitById(
            @Parameter(description = "ID unico de la " + NAME_API, required = true, example = "601")
            @PathVariable Long id
    );

    @Operation(
            summary = "Crear nueva " + NAME_API,
            description = "Registra una zona física (Pabellón/Servicio) vinculada a un tipo de especialidad.",
            responses = {
                    @ApiResponse(responseCode = "201", description = NAME_API + " creada con exito."),
                    @ApiResponse(responseCode = "400", description = "Error de validación en los datos de infraestructura."),
                    @ApiResponse(responseCode = "500", description = "Error del servidor.")
            }
    )
    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<DepartmentUnitDto> createUnit(
            @Parameter(description = "Detalles de la " + NAME_API + " a registrar.", required = true)
            @Valid @RequestBody DepartmentUnitDto dto
    );

    @Operation(
            summary = "Actualizar " + NAME_API + " por ID",
            description = "Permite modificar la capacidad, piso o descripcion de la unidad.",
            responses = {
                    @ApiResponse(responseCode = "200", description = NAME_API + " actualizada correctamente."),
                    @ApiResponse(responseCode = "404", description = NAME_API + " no encontrada."),
                    @ApiResponse(responseCode = "500", description = "Error critico de actualizacion.")
            }
    )
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<DepartmentUnitDto> updateUnit(
            @Parameter(description = "ID de la " + NAME_API + " a modificar.", required = true)
            @PathVariable Long id,
            @Parameter(description = "Nuevos datos de la " + NAME_API + ".", required = true)
            @Valid @RequestBody DepartmentUnitDto dto
    );

    @Operation(
            summary = "Eliminar " + NAME_API + " por ID",
            description = "Remueve una unidad de la infraestructura hospitalaria (Baja logica/fisica).",
            responses = {
                    @ApiResponse(responseCode = "204", description = NAME_API + " eliminada con exito."),
                    @ApiResponse(responseCode = "400", description = "Solicitud invalida (Ej: Unidad con habitaciones activas)."),
                    @ApiResponse(responseCode = "404", description = NAME_API + " no existe."),
                    @ApiResponse(responseCode = "500", description = "Error del sistema.")
            }
    )
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> deleteUnit(
            @Parameter(description = "ID de la " + NAME_API + " a eliminar.", required = true)
            @PathVariable Long id
    );

    @Operation(
            summary = "Listar todas las " + NAME_API + "s",
            description = "Devuelve el catalogo completo de secciones y servicios sin paginacion.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Catalogo de unidades recuperado.")
            }
    )
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<DepartmentUnitDto>> getAllUnits();

    @Operation(
            summary = "Buscador experto de " + NAME_API + "s",
            description = "Busqueda avanzada con filtros de Piso, Especialidad y Capacidad.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Listado de unidades paginado con exito."),
                    @ApiResponse(responseCode = "500", description = "Error interno de consulta.")
            }
    )
    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<PageResponseDto<DepartmentUnitDto>> getPaginatedUnits(
            @Parameter(description = "Criterios de seleccion.") DepartmentUnitSearchDto searchDto,
            @Parameter(description = "Indice de pagina.", example = "0") @RequestParam(defaultValue = "0") Integer pageIndex,
            @Parameter(description = "Resultados por pagina.", example = "10") @RequestParam(defaultValue = "10") Integer pageSize
    );
}
