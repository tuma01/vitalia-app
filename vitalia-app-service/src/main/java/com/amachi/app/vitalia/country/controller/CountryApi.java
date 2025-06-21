package com.amachi.app.vitalia.country.controller;

import com.amachi.app.vitalia.common.controller.GenericApi;
import com.amachi.app.vitalia.common.dto.PageResponseDto;
import com.amachi.app.vitalia.country.dto.CountryDto;
import com.amachi.app.vitalia.country.dto.search.CountrySearchDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.amachi.app.vitalia.common.controller.BaseController.*;

@Tag(name = "Country", description = "REST API para gestionar detalles de países: crear, actualizar, obtener y eliminar.")
public interface CountryApi extends GenericApi<CountryDto> {
    String nameAPi = "Country";

    @Operation(
            summary = "Obtener un país por ID",
            description = "Devuelve un objeto Country por ID especificado.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "País encontrado con éxito."),
                    @ApiResponse(responseCode = "404", description = "País no encontrado."),
                    @ApiResponse(responseCode = "500", description = "Error del servidor.")
            }
    )
    @GetMapping(value = ID, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CountryDto> getCountryById(
            @Parameter(description = "ID del país a recuperar.", required = true)
            @PathVariable("id") Long id
    );

    @Operation(
            summary = "Crear un país",
            description = "Crea un nuevo país usando los datos proporcionados en el cuerpo de la solicitud.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "País creado con éxito."),
                    @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos."),
                    @ApiResponse(responseCode = "500", description = "Error del servidor.")
            }
    )
    @PostMapping( produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CountryDto> createCountry(
            @Parameter(description = "Detalles del país a crear.", required = true)
            @Valid @RequestBody CountryDto dto
    );

    @Operation(
            summary = "Actualizar un país por ID",
            description = "Actualiza un país existente usando su ID y los datos proporcionados.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "País actualizado con éxito."),
                    @ApiResponse(responseCode = "404", description = "País no encontrado."),
                    @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos."),
                    @ApiResponse(responseCode = "500", description = "Error del servidor.")
            }
    )
    @PutMapping(value = ID, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CountryDto> updateCountry(
            @Parameter(description = "ID del país a actualizar.", required = true)
            @PathVariable("id") Long id,
            @Parameter(description = "Nuevos detalles del país.", required = true)
            @Valid @RequestBody CountryDto dto
    );

    @Operation(
            summary = "Eliminar un país por ID",
            description = "Elimina un país existente usando su ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "País eliminado con éxito."),
                    @ApiResponse(responseCode = "404", description = "País no encontrado."),
                    @ApiResponse(responseCode = "500", description = "Error del servidor.")
            }
    )
    @DeleteMapping(value = ID, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> deleteCountry(
            @Parameter(description = "ID del país a eliminar.", required = true)
            @PathVariable("id") Long id
    );

    @Operation(
            summary = "Obtener todos los países",
            description = "Devuelve la lista completa de países.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de países recuperada con éxito."),
                    @ApiResponse(responseCode = "500", description = "Error del servidor.")
            }
    )
    @GetMapping(value = ALL, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<CountryDto>> getAllCountries();

    @Operation(
            summary = "Obtener países con paginación",
            description = "Devuelve una lista de países paginada.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de países recuperada con éxito."),
                    @ApiResponse(responseCode = "400", description = "Parámetros de paginación inválidos."),
                    @ApiResponse(responseCode = "500", description = "Error del servidor.")
            }
    )
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<PageResponseDto<CountryDto>> getPaginatedCountries(CountrySearchDto searchDto,
                                                       @Parameter(description = "Índice de la página a recuperar.", example = "0") @RequestParam(value = "pageIndex", defaultValue = "0", required = false)final Integer pageIndex,
                                                       @Parameter(description = "Tamaño de la página.", example = "10") @RequestParam(value = "pageSize", defaultValue = "10", required = false)final Integer pageSize
    );
}