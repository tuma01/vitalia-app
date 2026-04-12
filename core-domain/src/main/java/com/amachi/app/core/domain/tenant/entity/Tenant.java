package com.amachi.app.core.domain.tenant.entity;

import com.amachi.app.core.common.enums.TenantType;
import com.amachi.app.core.common.entity.BaseTenantEntity;
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
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "TYPE", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("GLOBAL")
public class Tenant extends BaseTenantEntity implements Model {

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
    @Column(name = "TYPE", nullable = false, length = 20, insertable = false, updatable = false)
    private TenantType type;// Ej: HOSPITAL, CLINIC, LABORATORY, PHARMACY, GLOBAL

    @Column(name = "IS_ACTIVE", nullable = false)
    @Builder.Default
    private Boolean isActive = true;

    @Column(name = "DESCRIPTION")
    private String description;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "THEME_ID", nullable = true)
    private Theme theme;

    @Column(name = "ADDRESS_ID")
    private Long addressId;

    @Column(name = "LOGO_URL")
    private String logoUrl;

    @Column(name = "FAVICON_URL")
    private String faviconUrl;

    // ==========================================
    // 🧠 Lógica de Normalización (Elite Standard)
    // ==========================================

    @PrePersist
    @PreUpdate
    private void normalize() {
        if (this.code != null) {
            this.code = this.code.trim().toUpperCase();
        }
        if (this.name != null) {
            this.name = this.name.trim();
        }
        // Force SYSTEM as default parent tenant for platform level tenants
        if (getTenantId() == null) {
            setTenantId("SYSTEM");
        }
    }
}
