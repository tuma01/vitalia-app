package com.amachi.app.vitalia.medical.nurse.dto;

import com.amachi.app.core.common.enums.ShiftEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.util.Set;

/**
 * Esquema Profesional Elite para el personal de enfermería y cuidados asistenciales.
 * Estándar GOLD de Amachi Platform (Grado HIS Internacional).
 */
@Validated
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder(toBuilder = true)
@Schema(name = "Nurse", description = "Perfil integral de Personal de Enfermería: Identidad, Categoría y Nómina")
public class NurseDto {

    @Schema(description = "Identificador único interno (PK)", example = "3001", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Identificador Global del Personal (FHIR/HID)", example = "NRS-445-UUID", accessMode = Schema.AccessMode.READ_ONLY)
    private String externalId;

    // --- Identidad (Mapping a Person) ---
    @NotBlank(message = "First Name {err.mandatory}")
    @Schema(description = "Nombres oficiales de enfermería", example = "MARIA")
    private String firstName;

    @Schema(description = "Segundo nombre del personal", example = "DEL CARMEN")
    private String middleName;

    @NotBlank(message = "Last Name {err.mandatory}")
    @Schema(description = "Primer apellido (Paterno)", example = "SOSA")
    private String lastName;

    @Schema(description = "Segundo apellido (Materno)", example = "MENDEZ")
    private String secondLastName;

    @NotBlank(message = "ID Card {err.mandatory}")
    @Schema(description = "Documento de Identificación local", example = "40506070-LP")
    private String idCard;

    @Schema(description = "Nombre completo de enfermería (Solo lectura)", example = "LIC. MARIA DEL CARMEN SOSA")
    private String fullName;

    // --- Profesional & Operativo ---
    @Schema(description = "Número de licencia profesional activa", example = "ENF-BOL-456-N")
    private String licenseNumber;

    @Schema(description = "Rango o categoría operativa (LICENCIADA, AUXILIAR, JEFE)", example = "LICENCIADA")
    private String rank;

    @Schema(description = "Turno de trabajo actual asignado", example = "MORNING")
    private ShiftEnum workShift;

    // --- Organización & Nómina ---
    @Schema(description = "ID de la Unidad Departamental de adscripción", example = "101")
    private Long departmentUnitId;

    @Schema(description = "ID de la ficha de relación laboral (RRHH)", example = "5002")
    private Long employeeId;

    @Schema(description = "Fecha de ingreso formal a la institución", example = "2022-06-01")
    private LocalDate hireDate;

    // --- Especialidad & Contacto ---
    @Schema(description = "Catálogo de habilidades especializadas (ej: UCI, Neonatología)")
    private Set<String> specializedSkills;

    @NotBlank(message = "Email {err.mandatory}")
    @Email(message = "Email {err.format}")
    @Schema(description = "Correo electrónico institucional", example = "m.sosa@vitalia.com")
    private String email;

    @Schema(description = "Número de celular personal", example = "+591 60000001")
    private String celular;

    @Schema(description = "Estado activo en el sistema", example = "true")
    private Boolean active;
}
