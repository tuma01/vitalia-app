package com.amachi.app.vitalia.medical.employee.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Esquema Profesional Elite para el personal administrativo y soporte hospitalario.
 * Estándar GOLD de Amachi Platform.
 */
@Validated
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder(toBuilder = true)
@Schema(name = "Employee", description = "Perfil integral de Empleado (Identidad + Contrato + Organización)")
public class EmployeeDto {

    @Schema(description = "Identificador interno (PK)", example = "4001", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Identificador Global del Empleado (UUID)", example = "EMP-9988-UUID", accessMode = Schema.AccessMode.READ_ONLY)
    private String externalId;

    // --- Identidad (Mapping a Person) ---
    @NotBlank(message = "First Name {err.mandatory}")
    @Schema(description = "Nombres oficiales del empleado", example = "CARLOS")
    private String firstName;

    @Schema(description = "Segundo nombre del empleado", example = "ALBERTO")
    private String middleName;

    @NotBlank(message = "Last Name {err.mandatory}")
    @Schema(description = "Primer apellido (Paterno)", example = "SOLARES")
    private String lastName;

    @Schema(description = "Segundo apellido (Materno)", example = "RIOS")
    private String secondLastName;

    @NotBlank(message = "ID Card {err.mandatory}")
    @Schema(description = "Documento de Identificación local", example = "50607080-CB")
    private String nationalId;

    @Schema(description = "Nombre completo del empleado (Solo lectura)", example = "CARLOS ALBERTO SOLARES RIOS")
    private String fullName;

    // --- Administrativo & Organización ---
    @Schema(description = "Código interno de empleado", example = "EMP-001")
    private String employeeCode;

    @NotBlank(message = "Cargo {err.mandatory}")
    @Schema(description = "Nombre del cargo o puesto operativo", example = "JEFE DE MANTENIMIENTO")
    private String jobPosition;

    @Schema(description = "ID del usuario vinculado", example = "1001")
    private Long userId;

    @Schema(description = "ID de la Unidad Departamental de adscripción", example = "101")
    private Long departmentUnitId;

    @Schema(description = "Nombre de la Unidad Departamental", example = "MANTENIMIENTO")
    private String departmentUnitName;

    @Schema(description = "Tipo de empleado (ADMIN, MEDICAL_SUPPORT, etc.)", example = "ADMIN")
    private String employeeType;

    @Schema(description = "Estado laboral del empleado", example = "ACTIVE")
    private String employeeStatus;

    // --- Contractual & Turnos ---
    @Schema(description = "Tipo de relación laboral", example = "FULL_TIME")
    private String employmentType;

    @Schema(description = "Sueldo base pactado", example = "2500.00")
    private BigDecimal salary;

    @Schema(description = "Turno de trabajo asignado", example = "MAÑANA")
    private String workShift;

    @Schema(description = "Fecha formal de contratación", example = "2024-01-15")
    private LocalDate hireDate;

    // --- Contacto & Emergencia ---
    @NotBlank(message = "Email {err.mandatory}")
    @Email(message = "Email {err.format}")
    @Schema(description = "Correo electrónico institucional", example = "c.solares@vitalia.com")
    private String email;

    @Schema(description = "Número de celular personal", example = "+591 70010101")
    private String mobileNumber;

    @Schema(description = "Teléfono o extensión interna", example = "INT-405")
    private String phoneNumber;

    @Schema(description = "Contacto de emergencia personal", example = "ESPOSA: 3340001")
    private String emergencyContact;

    @Schema(description = "Indica si el empleado está activo en nómina", example = "true")
    private Boolean active;
}
