package com.amachi.app.vitalia.medical.patient.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

/**
 * Contacto de emergencia del paciente (SaaS Elite Tier).
 */
@Embeddable
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class EmergencyContact {

    @Schema(description = "Nombre completo del contacto de emergencia", example = "Jane Doe")
    @Column(name = "EMERGENCY_NAME", length = 150)
    private String fullName;

    @Schema(description = "Parentesco con el paciente", example = "Brother")
    @Column(name = "EMERGENCY_RELATION", length = 50)
    private String relationship;

    @Schema(description = "Teléfono de emergencia", example = "+1-555-0199")
    @Column(name = "EMERGENCY_PHONE", length = 50)
    private String phoneNumber;

    @Schema(description = "Email de contacto de emergencia", example = "emergency@contact.com")
    @Column(name = "EMERGENCY_EMAIL", length = 100)
    private String email;

    @Schema(description = "Dirección del contacto de emergencia", example = "123 Main St")
    @Column(name = "EMERGENCY_ADDRESS", length = 250)
    private String address;
}
