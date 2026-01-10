package com.amachi.app.core.auth.controller;

import com.amachi.app.core.auth.dto.AssignRolesRequest;
import com.amachi.app.core.auth.dto.UserTenantRoleDto;
import com.amachi.app.core.auth.dto.search.UserTenantRoleSearchDto;
import com.amachi.app.core.common.controller.GenericApi;
import com.amachi.app.core.common.dto.PageResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.amachi.app.core.common.controller.BaseController.ALL;
import static com.amachi.app.core.common.controller.BaseController.ID;

@Tag(name = "UserTenantRole", description = "REST API para gestionar detalles de países: crear, actualizar, obtener y eliminar.")
public interface UserTenantRoleApi extends GenericApi<UserTenantRoleDto> {
        String NAME_API = "UserTenantRole";

        @Operation(summary = "Obtener un " + NAME_API + " por ID", description = "Devuelve un objeto " + NAME_API
                        + " por ID especificado.", responses = {
                                        @ApiResponse(responseCode = "200", description = NAME_API
                                                        + " encontrado con éxito."),
                                        @ApiResponse(responseCode = "400", description = "Solicitud inválida: ID nulo o datos incompletos."),
                                        @ApiResponse(responseCode = "404", description = NAME_API + " no encontrado."),
                                        @ApiResponse(responseCode = "500", description = "Error interno del servidor.")
                        })
        @GetMapping(value = ID, produces = MediaType.APPLICATION_JSON_VALUE)
        ResponseEntity<UserTenantRoleDto> getUserTenantRoleById(
                        @Parameter(description = "ID del " + NAME_API
                                        + " a recuperar", required = true) @PathVariable("id") Long id);

        @Operation(summary = "Crear un " + NAME_API, description = "Crea un nuevo " + NAME_API
                        + " usando los datos proporcionados en el cuerpo de la solicitud.", responses = {
                                        @ApiResponse(responseCode = "201", description = NAME_API
                                                        + " creado con éxito."),
                                        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos."),
                                        @ApiResponse(responseCode = "500", description = "Error del servidor.")
                        })
        @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
        ResponseEntity<UserTenantRoleDto> createUserTenantRole(
                        @Parameter(description = "Detalles del " + NAME_API
                                        + " a crear.", required = true) @Valid @RequestBody UserTenantRoleDto dto);

        @Operation(summary = "Actualizar un " + NAME_API + " por ID", description = "Actualiza un " + NAME_API
                        + " existente usando su ID y los datos proporcionados.", responses = {
                                        @ApiResponse(responseCode = "200", description = NAME_API
                                                        + " actualizado con éxito."),
                                        @ApiResponse(responseCode = "400", description = "Solicitud inválida: ID nulo o datos incompletos."),
                                        @ApiResponse(responseCode = "404", description = NAME_API + " no encontrado."),
                                        @ApiResponse(responseCode = "500", description = "Error interno del servidor.")
                        })
        @PutMapping(value = ID, produces = MediaType.APPLICATION_JSON_VALUE)
        ResponseEntity<UserTenantRoleDto> updateUserTenantRole(
                        @Parameter(description = "ID del " + NAME_API
                                        + " a actualizar.", required = true) @PathVariable("id") Long id,
                        @Parameter(description = "Nuevos detalles del " + NAME_API
                                        + ".", required = true) @Valid @RequestBody UserTenantRoleDto dto);

        @Operation(summary = NAME_API + " a eliminar por ID", description = "Elimina un " + NAME_API
                        + " existente usando su ID.", responses = {
                                        @ApiResponse(responseCode = "204", description = NAME_API
                                                        + " eliminado con éxito (sin contenido)."),
                                        @ApiResponse(responseCode = "400", description = "Solicitud inválida: ID nulo o no válido."),
                                        @ApiResponse(responseCode = "404", description = NAME_API + " no encontrado."),
                                        @ApiResponse(responseCode = "500", description = "Error interno del servidor.")
                        })
        @DeleteMapping(value = ID, produces = MediaType.APPLICATION_JSON_VALUE)
        ResponseEntity<Void> deleteUserTenantRole(
                        @Parameter(description = "ID del " + NAME_API
                                        + " a eliminar.", required = true) @PathVariable("id") Long id);

        @Operation(summary = "Obtiene todos los " + NAME_API, description = "Devuelve la lista completa de "
                        + NAME_API, responses = {
                                        @ApiResponse(responseCode = "200", description = "Lista de " + NAME_API
                                                        + " recuperada con éxito."),
                                        @ApiResponse(responseCode = "500", description = "Error interno del servidor.")
                        })
        @GetMapping(value = ALL, produces = MediaType.APPLICATION_JSON_VALUE)
        ResponseEntity<List<UserTenantRoleDto>> getAllUserTenantRoles();

        @Operation(summary = "Obtiene una lista paginada de "
                        + NAME_API, description = "Devuelve una lista paginada de " + NAME_API, responses = {
                                        @ApiResponse(responseCode = "200", description = "Lista de " + NAME_API
                                                        + " recuperada con éxito."),
                                        @ApiResponse(responseCode = "400", description = "Parámetros de paginación inválidos."),
                                        @ApiResponse(responseCode = "500", description = "Error interno del servidor.")
                        })
        @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
        ResponseEntity<PageResponseDto<UserTenantRoleDto>> getPaginatedUserTenantRoles(
                        UserTenantRoleSearchDto searchDto,
                        @Parameter(description = "Índice de la página a recuperar.", example = "0") @RequestParam(value = "pageIndex", defaultValue = "0", required = false) Integer pageIndex,
                        @Parameter(description = "Tamaño de la página.", example = "10") @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize);

        // ----- Bulk role assignment endpoints -----
        @Operation(summary = "Asignar roles a un usuario en un tenant", description = "Asigna una lista de roles a un usuario dentro de un tenant especificado.", responses = {
                        @ApiResponse(responseCode = "201", description = "Roles asignados con éxito."),
                        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos."),
                        @ApiResponse(responseCode = "500", description = "Error interno del servidor.")
        })
        @PostMapping(value = "/assign", consumes = MediaType.APPLICATION_JSON_VALUE)
        ResponseEntity<Void> assignRoles(@Valid @RequestBody AssignRolesRequest request);

        @Operation(summary = "Desasignar roles de un usuario en un tenant", description = "Elimina una lista de roles asignados a un usuario dentro de un tenant.", responses = {
                        @ApiResponse(responseCode = "204", description = "Roles desasignados con éxito."),
                        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos."),
                        @ApiResponse(responseCode = "500", description = "Error interno del servidor.")
        })
        @PostMapping(value = "/unassign", consumes = MediaType.APPLICATION_JSON_VALUE)
        ResponseEntity<Void> unassignRoles(@Valid @RequestBody AssignRolesRequest request);
}