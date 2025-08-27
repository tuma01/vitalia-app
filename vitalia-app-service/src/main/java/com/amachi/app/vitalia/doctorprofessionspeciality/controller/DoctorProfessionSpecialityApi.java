package com.amachi.app.vitalia.doctorprofessionspeciality.controller;

import com.amachi.app.vitalia.common.controller.GenericApi;
import com.amachi.app.vitalia.common.dto.PageResponseDto;
import com.amachi.app.vitalia.doctorprofessionspeciality.dto.DoctorProfessionSpecialityDto;
import com.amachi.app.vitalia.doctorprofessionspeciality.dto.search.DoctorProfessionSpecialitySearchDto;
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

@Tag(name = "DoctorProfessionSpeciality", description = "Rest API SIIM APP to CREATE, UPDATE, FETCH and DELETE DoctorProfessionSpeciality details")
public interface DoctorProfessionSpecialityApi extends GenericApi<DoctorProfessionSpecialityDto> {

    String NAME_API = "DoctorProfessionSpeciality";

    @Operation(
            summary = "Obtener un doctorProfessionSpeciality por ID",
            description = "Devuelve un objeto DoctorProfessionSpeciality por ID especificado.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "DoctorProfessionSpeciality encontrado con éxito."),
                    @ApiResponse(responseCode = "404", description = "DoctorProfessionSpeciality no encontrado."),
                    @ApiResponse(responseCode = "500", description = "Error del servidor.")
            }
    )
    @GetMapping(value = ID, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<DoctorProfessionSpecialityDto> getDoctorProfessionSpecialityById(
            @Parameter(description = "ID del doctorProfessionSpeciality a recuperar.", required = true)
            @PathVariable("id") Long id
    );

    @Operation(
            summary = "Crear un doctorProfessionSpeciality",
            description = "Crea un nuevo doctorProfessionSpeciality usando los datos proporcionados en el cuerpo de la solicitud.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "DoctorProfessionSpeciality creado con éxito."),
                    @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos."),
                    @ApiResponse(responseCode = "500", description = "Error del servidor.")
            }
    )
    @PostMapping( produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<DoctorProfessionSpecialityDto> createDoctorProfessionSpeciality(
            @Parameter(description = "Detalles del doctorProfessionSpeciality a crear.", required = true)
            @Valid @RequestBody DoctorProfessionSpecialityDto dto
    );

    @Operation(
            summary = "Actualizar un doctorProfessionSpeciality por ID",
            description = "Actualiza un doctorProfessionSpeciality existente usando su ID y los datos proporcionados.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "DoctorProfessionSpeciality actualizado con éxito."),
                    @ApiResponse(responseCode = "404", description = "DoctorProfessionSpeciality no encontrado."),
                    @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos."),
                    @ApiResponse(responseCode = "500", description = "Error del servidor.")
            }
    )
    @PutMapping(value = ID, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<DoctorProfessionSpecialityDto> updateDoctorProfessionSpeciality(
            @Parameter(description = "ID del doctorProfessionSpeciality a actualizar.", required = true)
            @PathVariable("id") Long id,
            @Parameter(description = "Nuevos detalles del doctorProfessionSpeciality.", required = true)
            @Valid @RequestBody DoctorProfessionSpecialityDto dto
    );

    @Operation(
            summary = "Eliminar un doctorProfessionSpeciality por ID",
            description = "Elimina un doctorProfessionSpeciality existente usando su ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "DoctorProfessionSpeciality eliminado con éxito."),
                    @ApiResponse(responseCode = "404", description = "DoctorProfessionSpeciality no encontrado."),
                    @ApiResponse(responseCode = "500", description = "Error del servidor.")
            }
    )
    @DeleteMapping(value = ID, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> deleteDoctorProfessionSpeciality(
            @Parameter(description = "ID del doctorProfessionSpeciality a eliminar.", required = true)
            @PathVariable("id") Long id
    );

    @Operation(
            summary = "Obtener todos los doctorProfessionSpecialities",
            description = "Devuelve la lista completa de doctorProfessionSpecialities.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de doctorProfessionSpecialities recuperada con éxito."),
                    @ApiResponse(responseCode = "500", description = "Error del servidor.")
            }
    )
    @GetMapping(value = ALL, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<DoctorProfessionSpecialityDto>> getAllDoctorProfessionSpecialities();

    @Operation(
            summary = "Obtener doctorProfessionSpecialities con paginación",
            description = "Devuelve una lista de doctorProfessionSpecialities paginada.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de doctorProfessionSpecialitys recuperada con éxito."),
                    @ApiResponse(responseCode = "400", description = "Parámetros de paginación inválidos."),
                    @ApiResponse(responseCode = "500", description = "Error del servidor.")
            }
    )
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<PageResponseDto<DoctorProfessionSpecialityDto>> getPaginatedDoctorProfessionSpecialities(DoctorProfessionSpecialitySearchDto searchDto,
                                                                                                            @Parameter(description = "Índice de la página a recuperar.", example = "0") @RequestParam(value = "pageIndex", defaultValue = "0", required = false)final Integer pageIndex,
                                                                                                            @Parameter(description = "Tamaño de la página.", example = "10") @RequestParam(value = "pageSize", defaultValue = "10", required = false)final Integer pageSize
    );
}