package com.amachi.app.core.domain.tenant.entity;

import com.amachi.app.core.domain.tenant.enums.TenantType;
import com.amachi.app.core.common.entity.Auditable;
import com.amachi.app.core.common.entity.Model;
import com.amachi.app.core.domain.theme.entity.Theme;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Table(name = "DMN_TENANT")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Inheritance(strategy = InheritanceType.JOINED) // <-- JOINED strategy
public class Tenant extends Auditable<String> implements Model {

    // ID heredado de BaseEntity

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
    private TenantType type;// Ej: HOSPITAL, CLINIC, LABORATORY, PHARMACY, GLOBAL

    @Column(name = "IS_ACTIVE", nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    @Builder.Default
    private Boolean isActive = true;

    @Column(name = "DESCRIPTION")
    private String description;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "THEME_ID", nullable = true)
    private Theme theme;

    @Column(name = "ADDRESS_ID")
    private Long addressId;
}
