package com.amachi.app.core.domain.dto;

import com.amachi.app.core.common.enums.DocumentType;
import com.amachi.app.core.common.enums.EstadoCivilEnum;
import com.amachi.app.core.common.enums.GeneroEnum;
import com.amachi.app.core.common.enums.PersonType;
import com.amachi.app.core.geography.address.dto.AddressDto;
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
@Getter
@Setter
@SuperBuilder(toBuilder = true)
@Schema(name = "Person", description = "Schema to hold Person information")
public abstract class PersonDto {

        @Valid
        @JsonProperty
        @Schema(description = "Id de la Persona", example = "1")
        private Long id;

        @Schema(description = "Identificador Nacional", example = "A12345678")
        private String nationalId;

        @Schema(description = "Identificador Nacional de Salud", example = "123456789")
        private String nationalHealthId;

        @Schema(description = "Tipo de Persona", example = "PATIENT")
        private PersonType personType;

        @NotBlank(message = "Nombre {err.mandatory}")
        @Schema(description = "Nombre de la Persona", example = "Eric")
        private String nombre;

        @Valid
        @JsonProperty
        @Schema(description = "Segundo nombre de la Persona", example = "Juan")
        private String segundoNombre;

        @Valid
        @JsonProperty
        @NotBlank(message = "Apellido Paterno {err.mandatory}")
        @Schema(description = "Apellido Paterno de la Persona", example = "Bouchard")
        private String apellidoPaterno;

        @Valid
        @JsonProperty
        @Schema(description = "Apellido Materno de la Persona", example = "Larico")
        private String apellidoMaterno;

        @Valid
        @JsonProperty
//        @JsonFormat(pattern = "yyyy-MM-dd")
        @Schema(description = "La fecha de nacimiento de la Persona", example = "24-06-2000")
        private LocalDate fechaNacimiento;

        @Valid
        @Builder.Default
        @JsonProperty
        private EstadoCivilEnum estadoCivil = EstadoCivilEnum.SOLTERO;

        @Valid
        @Builder.Default
        @JsonProperty
        private GeneroEnum genero = GeneroEnum.FEMENINO;

        @Valid
        @JsonProperty
        @Schema(description = "Objeto Direcci├│n Completo (Para creaci├│n inline)")
        private AddressDto address;

        @JsonProperty
        @Schema(description = "Telefono de la Persona", example = "514 2367944")
        @NotBlank(message = "Telephone {err.required}")
        private String telefono;

        @Valid
        @JsonProperty
        @Schema(description = "Celular de la Persona", example = "514 236 7944")
        private String celular;

        @Builder.Default
        private Set<Long> personTenantsIds = new HashSet<>();

        @Schema(description = "Tipo de Documento de Identidad", example = "DNI, PASAPORTE, etc.")
        private DocumentType documentType;

        @Schema(description = "Correo electrónico personal", example = "eric@example.com")
        private String email;
}
