package com.amachi.app.vitalia.patient.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmergencyContact {

    @Column(name = "EMERGENCY_CONTACT_NAME", length = 100)
    private String name;

    @Column(name = "EMERGENCY_CONTACT_PHONE", length = 20)
    private String phone;
}
