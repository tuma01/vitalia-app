package com.amachi.app.vitalia.common.entity;

import com.amachi.app.vitalia.common.enums.TenantType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "TENANT")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Inheritance(strategy = InheritanceType.JOINED) // <-- JOINED strategy
public class Tenant extends Auditable<String> implements Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", updatable = false, nullable = false)
    private Long id;

    @NotBlank
    @Size(max = 100)
    @Column(name = "CODE", nullable = false, unique = true)
    private String code;

    @NotBlank
    @Size(max = 100)
    @Column(name = "NAME", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE", nullable = false, length = 20)
    private TenantType type;// Ej: HOSPITAL, CLINICA, LABORATORIO, PHARMACY

    @Column(name = "IS_ACTIVE", nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean isActive = true;

    @Column(name="DESCRIPTION")
    private String description;
}
