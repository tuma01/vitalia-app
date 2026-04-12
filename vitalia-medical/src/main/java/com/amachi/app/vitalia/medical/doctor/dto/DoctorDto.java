package com.amachi.app.vitalia.medical.doctor.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

/**
 * Esquema Profesional Elite para el personal médico facultativo.
 * Estándar GOLD de Amachi Platform.
 */
@Validated
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder(toBuilder = true)
@Schema(name = "Doctor", description = "Perfil integral de Facultativo (Identidad + Clínica + Nómina)")
public class DoctorDto {

    @Schema(description = "Identificador único interno (PK)", example = "2001", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Identificador Global del Facultativo (UUID)", example = "DOC-7788-UUID", accessMode = Schema.AccessMode.READ_ONLY)
    private String externalId;

    // --- Identidad (Mapping a Person) ---
    @NotBlank(message = "First Name {err.mandatory}")
    @Schema(description = "Nombres oficiales del médico", example = "MARIA ELENA")
    private String firstName;

    @Schema(description = "Segundo nombre del médico", example = "BEATRIZ")
    private String middleName;

    @NotBlank(message = "Last Name {err.mandatory}")
    @Schema(description = "Primer apellido (Paterno)", example = "RODRIGUEZ")
    private String lastName;

    @Schema(description = "Segundo apellido (Materno)", example = "SOLIZ")
    private String secondLastName;

    @NotBlank(message = "ID Card {err.mandatory}")
    @Schema(description = "Documento de Identificación local", example = "30405060-SC")
    private String nationalId;

    @Schema(description = "Fecha de nacimiento", example = "1980-05-15")
    private LocalDate birthDate;

    @Schema(description = "Género del médico", example = "MASCULINO")
    private String gender;

    @Schema(description = "Nombre completo del médico (Solo lectura)", example = "DR. MARÍA ELENA RODRÍGUEZ SOLIZ")
    private String fullName;

    // --- Credenciales y Facturación (Billing) ---
    @NotBlank(message = "Licencia {err.mandatory}")
    @Schema(description = "Número de matrícula profesional habilitante", example = "MED-BOL-789-V")
    private String licenseNumber;

    @Schema(description = "Número de proveedor RAMQ (Fidelidad Canadá/Billing)", example = "123456")
    private String providerNumber;

    @Schema(description = "Especialidad médica principal", example = "CARDIOLOGÍA INFANTIL")
    private String specialtiesSummary;

    @Schema(description = "Trayectoria resumida (Bio)", example = "15 años en cirugía cardiovascular.")
    private String bio;

    // --- Operativo & Nómina ---
    @Schema(description = "ID de la Unidad Departamental de adscripción", example = "101")
    private Long departmentUnitId;

    @Schema(description = "Nombre de la Unidad Departamental (Solo lectura)", example = "CARDIOLOGÍA - ALA NORTE")
    private String departmentUnitName;

    @Schema(description = "ID de la ficha de relación laboral (RRHH)", example = "5001")
    private Long employeeId;

    @Schema(description = "Precio base de la consulta externa", example = "50.00")
    private BigDecimal consultationPrice;

    @Schema(description = "Estado de disponibilidad actual", example = "AVAILABLE")
    private String availabilityStatus;

    @Schema(description = "Ruta digital de la firma para documentos electrónicos", example = "/signatures/dr_rodriguez.png")
    private String signatureDigitalPath;

    @Schema(description = "Fecha de expiración de la licencia médica", example = "2030-12-31")
    private LocalDate licenseExpiryDate;

    @Schema(description = "Fecha formal de contratación", example = "2020-01-15")
    private LocalDate hireDate;

    // --- Contacto ---
    @NotBlank(message = "Email {err.mandatory}")
    @Email(message = "Email {err.format}")
    @Schema(description = "Correo electrónico institucional", example = "m.rodriguez@vitalia.com")
    private String email;

    @Schema(description = "Número de contacto celular", example = "+591 78901234")
    private String mobileNumber;

    @Schema(description = "Teléfono fijo o de oficina", example = "3340001")
    private String phoneNumber;

    @Schema(description = "Extensión o box de consultorio", example = "BOX-01")
    private String officeNumber;

    // --- Métricas & Habilidades ---
    @Schema(description = "Años de experiencia clínica", example = "12")
    private Integer yearsOfExperience;

    @Schema(description = "Total de consultas atendidas históricamente", example = "1540")
    private Integer totalConsultations;

    @Schema(description = "Rating promedio de satisfacción del paciente (1-5)", example = "4.8")
    private Double rating;

    @Schema(description = "Catálogo de procedimientos quirúrgicos autorizados")
    private Set<String> clinicalProcedures;

    @Schema(description = "Indica si el facultativo está activo", example = "true")
    private Boolean active;
}
