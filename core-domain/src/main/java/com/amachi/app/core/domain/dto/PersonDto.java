package com.amachi.app.core.domain.dto;

import com.amachi.app.core.common.enums.RoleContext;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

/**
 * Data Transfer Object for Person entity.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "PersonDto", description = "Información detallada de una persona")
public class PersonDto implements Serializable {

        private Long id;

        @Schema(description = "Nombre principal", example = "Juan")
        private String firstName;

        @Schema(description = "Segundo nombre", example = "Carlos")
        private String middleName;

        @Schema(description = "Apellido paterno", example = "Pérez")
        private String lastName;

        @Schema(description = "Apellido materno", example = "García")
        private String secondLastName;

        @Schema(description = "Correo electrónico personal", example = "juan.perez@example.com")
        private String email;

        @Schema(description = "Número de identificación nacional (DNI, Pasaporte)", example = "12345678")
        private String nationalId;

        @Schema(description = "ID de salud nacional", example = "NHS-999")
        private String nationalHealthId;

        @Schema(description = "Contextos de rol activos para esta persona")
        private Set<RoleContext> activeRoleContexts;

        public String getFullName() {
                StringBuilder sb = new StringBuilder();
                if (firstName != null) sb.append(firstName);
                if (middleName != null && !middleName.isBlank()) sb.append(" ").append(middleName);
                if (lastName != null) sb.append(" ").append(lastName);
                if (secondLastName != null && !secondLastName.isBlank()) sb.append(" ").append(secondLastName);
                return sb.toString().trim();
        }
}
