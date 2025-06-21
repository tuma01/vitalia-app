package com.amachi.app.vitalia.dto;

import com.amachi.app.vitalia.address.dto.AddressDto;
import com.amachi.app.vitalia.common.utils.EstadoCivilEnum;
import com.amachi.app.vitalia.common.utils.GeneroEnum;
import com.amachi.app.vitalia.common.utils.PersonType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(name = "Person", description = "Schema to hold Person information")
public class PersonDto {

    @Valid
    @JsonProperty
    @Schema(
            description = "Id de la Persona", example = "1"
    )
    private Long id;

    private PersonType personType = PersonType.USER;

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
    @Schema(
            description = "Nombre Completo de la Persona", example = "Jose Larico"
    )
    private String nombreCompleto;

    @Valid
    @JsonProperty
    @Schema(
            description = "El dia de nacimiento de la Persona", example = "24"
    )
    private Integer diaNacimiento;

    @Valid
    @JsonProperty
    @Schema(
            description = "El mes de nacimiento de la Persona", example = "06"
    )
    private Integer mesNacimiento;

    @Valid
    @JsonProperty
    @Schema(
            description = "El ano de nacimiento de la Persona", example = "2000"
    )
    private Integer anoNacimiento;

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

}
