package com.amachi.app.vitalia.medical.patient.dto;

import com.amachi.app.core.common.enums.PatientStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Esquema Profesional Elite para la transferencia de información del paciente.
 * Sigue el estándar GOLD de Amachi Platform.
 */
@Validated
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder(toBuilder = true)
@Schema(name = "Patient", description = "Esquema integral de Identidad y Perfil Clínico HIS")
public class PatientDto {

    @Schema(description = "Identificador único interno (PK)", example = "5001", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Identificador Global Interinstitucional (UUID)", example = "PAT-789-UUID", accessMode = Schema.AccessMode.READ_ONLY)
    private String externalId;

    // --- Identidad (Mapping a Person) ---
    @NotBlank(message = "First Name {err.mandatory}")
    @Schema(description = "Nombres oficiales del paciente", example = "JUAN CARLOS")
    private String firstName;

    @Schema(description = "Segundo nombre del paciente", example = "DAVID")
    private String middleName;

    @NotBlank(message = "Last Name {err.mandatory}")
    @Schema(description = "Primer apellido (Paterno)", example = "PEREZ")
    private String lastName;

    @Schema(description = "Segundo apellido (Materno)", example = "GARCIA")
    private String secondLastName;

    @NotBlank(message = "ID Card {err.mandatory}")
    @Schema(description = "Documento de Identificación local", example = "10203040-SC")
    private String nationalId;

    @Schema(description = "Nombre completo del paciente (Solo lectura)", example = "JUAN CARLOS DAVID PEREZ GARCIA")
    private String fullName;

    @Schema(description = "Número de Historia Clínica Local", example = "NHC-2001")
    private String nhc;

    // --- Demográficos & Ubicación ---
    @NotNull(message = "Birth Date {err.mandatory}")
    @Schema(description = "Fecha de nacimiento cronológica", example = "1985-05-20")
    private LocalDate birthDate;

    @Schema(description = "Género biológico", example = "MASCULINO")
    private String gender;

    @Schema(description = "Nacionalidad de origen", example = "BOLIVIANA")
    private String nationality;

    @Schema(description = "Estado civil actual", example = "SOLTERO/A")
    private String maritalStatus;

    @Schema(description = "Nivel de instrucción", example = "LICENCIATURA")
    private String educationLevel;

    @Schema(description = "Profesión u ocupación actual", example = "MEDICO CIRUJANO")
    private String occupation;

    // --- Contacto ---
    @Schema(description = "Dirección de domicilio actual", example = "AV. BUSCH 123")
    private String address;

    @Schema(description = "Número de contacto celular", example = "+591 70010101")
    private String mobileNumber;

    @Schema(description = "Teléfono fijo de contacto", example = "33400011")
    private String phoneNumber;

    @Email(message = "Email {err.format}")
    @Schema(description = "Correo electrónico principal", example = "paciente@email.com")
    private String email;

    // --- Clínico & Operativo ---
    @Schema(description = "Idioma preferido para atención", example = "ES")
    private String preferredLanguage;

    @NotNull(message = "Estado {err.mandatory}")
    @Schema(description = "Estado operativo actual en el flujo clínico", example = "ACTIVE")
    private PatientStatus patientStatus;

    @Schema(description = "ID de la cama asignada (Atajo)", example = "101")
    private Long currentBedId;

    @Schema(description = "ID de la habitación asignada (Atajo)", example = "202")
    private Long roomId;

    @Schema(description = "Resumen de alergias (Legacy Mirror)", example = "MANI, POLEN")
    private String allergySummary;

    @Schema(description = "ID de la hospitalización vigente", example = "8001")
    private Long currentHospitalizationId;

    @Schema(description = "Comentarios o alertas administrativas", example = "Paciente requiere silla de ruedas")
    private String additionalRemarks;

    @Schema(description = "Foto del paciente (Base64/Binary)", example = "byte[] data")
    private byte[] photo;

    // --- Contacto de Emergencia ---
    @Schema(description = "Nombre de la persona de contacto en urgencias", example = "MARIA PEREZ")
    private String emergencyContactName;

    @Schema(description = "Parentesco con el contacto de emergencia", example = "MADRE")
    private String emergencyContactRelationship;

    @Schema(description = "Teléfono del contacto de emergencia", example = "+591 70000000")
    private String emergencyContactPhone;

    @Schema(description = "Email del contacto de emergencia", example = "m.perez@email.com")
    private String emergencyContactEmail;

    @Schema(description = "Indica si el paciente está activo en el sistema", example = "true")
    private Boolean active;

    // --- Biometría & Datos Clínicos (desde PatientDetails) ---
    @Schema(description = "ID del Grupo Sanguíneo (Catalog)", example = "1")
    private Long bloodTypeId;

    @Schema(description = "Nombre del Grupo Sanguíneo (Solo lectura)", example = "O POSITIVO")
    private String bloodTypeName;

    @Schema(description = "Peso en kilogramos", example = "75.5")
    private BigDecimal weight;

    @Schema(description = "Estatura en metros", example = "1.75")
    private BigDecimal height;

    @Schema(description = "Indicador de discapacidad", example = "false")
    private Boolean hasDisability;

    @Schema(description = "Detalles de la discapacidad", example = "Discapacidad motriz leve")
    private String disabilityDetails;

    @Schema(description = "Estado de embarazo", example = "false")
    private Boolean isPregnant;

    @Schema(description = "Semanas de embarazo", example = "12")
    private Integer gestationalWeeks;

    @Schema(description = "Número de hijos", example = "2")
    private Integer childrenCount;

    @Schema(description = "Grupo étnico de auto-identificación", example = "Hispano")
    private String ethnicGroup;
}
