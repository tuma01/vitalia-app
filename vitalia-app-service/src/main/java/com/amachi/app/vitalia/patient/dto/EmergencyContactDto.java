package com.amachi.app.vitalia.patient.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class EmergencyContactDto {

    @NotBlank(message = "El nombre del contacto de emergencia es obligatorio")
    private String name;

    @NotBlank(message = "El teléfono del contacto de emergencia es obligatorio")
    private String phone;
}
