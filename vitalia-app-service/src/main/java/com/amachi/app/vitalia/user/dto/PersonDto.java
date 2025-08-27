package com.amachi.app.vitalia.user.dto;

import com.amachi.app.vitalia.address.dto.AddressDto;
import com.amachi.app.vitalia.common.utils.EstadoCivilEnum;
import com.amachi.app.vitalia.common.utils.GeneroEnum;
import com.amachi.app.vitalia.common.utils.PersonType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@SuperBuilder
@Schema(name = "Person", description = "Schema to hold Person information")
public abstract class PersonDto {

    @Valid
    @JsonProperty
    @Schema(
            description = "Id de la Persona", example = "1"
    )
    private Long id;

    private PersonType personType = PersonType.PATIENT;

    @Valid
    @JsonProperty
    @NotBlank(message = "Nombre {err.mandatory}")
    @Schema(
            description = "Nombre de la Persona", example = "Eric"
    )
    private String nombre;

    @Valid
    @JsonProperty
    @Schema(
            description = "Segundo nombre de la Persona", example = "Juan"
    )
    private String segundoNombre;

    @Valid
    @JsonProperty
    @NotBlank(message = "Apellido Paterno {err.mandatory}")
    @Schema(
            description = "Apellido Paterno de la Persona", example = "Bouchard"
    )
    private String apellidoPaterno;

    @Valid
    @JsonProperty
    @Schema(
            description = "Apellido Materno de la Persona", example = "Larico"
    )
    private String apellidoMaterno;

    @Valid
    @JsonProperty
    @JsonFormat(pattern = "dd-MM-yyyy")
    @Schema(
            description = "La fecha de nacimiento de la Persona", example = "24-06-2000"
    )
    private LocalDate fechaNacimiento;

    @Valid
    @JsonProperty
    private EstadoCivilEnum estadoCivil = EstadoCivilEnum.SOLTERO;

    @Valid
    @JsonProperty
    private GeneroEnum genero = GeneroEnum.FEMENINO;

    @Valid
    @JsonProperty
    @Schema(
            description = "La direccion de la Persona"
    )
    private AddressDto address;


    @JsonProperty
    @Schema(
            description = "Telefono de la Persona", example = "514 2367944"
    )
    @NotBlank(message = "Telephone {err.required}")
    private String telefono;

    @Valid
    @JsonProperty
    @Schema(
            description = "Celular de la Persona", example = "514 236 7944"
    )
    private String celular;

    @Schema(
            description = "Lista de hospitales asociados a la Persona"
    )
    @JsonProperty
    private Set<Long> hospitalesIds = new HashSet<>();

    @JsonProperty
    @Schema(
            description = "Hospital principal de la Persona"
    )
    private Long hospitalPrincipalId;

    @Valid
    @JsonProperty
    @Schema(
            description = "Usuario asociado a la Persona"
    )
    private UserDto user;

}
