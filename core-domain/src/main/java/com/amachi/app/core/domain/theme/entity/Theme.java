package com.amachi.app.core.domain.theme.entity;

import jakarta.persistence.*;
import com.amachi.app.core.common.enums.ThemeMode;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import com.amachi.app.core.domain.tenant.entity.Tenant;
import com.amachi.app.core.common.entity.Auditable;
import com.amachi.app.core.common.entity.Model;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "DMN_THEME")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Theme extends Auditable<String> implements Model {

    // ID heredado de Auditable/BaseEntity

    // ---------------------------------------------------------------------
    // Identity & Classification
    // ---------------------------------------------------------------------
    /**
     * Functional code for internal reference and migrations.
     * Examples: DEFAULT, CUSTOM, MIGRATED
     */
    @Column(name = "CODE", length = 50, nullable = false)
    private String code;

    @Column(name = "NAME", nullable = false, length = 100)
    private String name;

    // ---------------------------------------------------------------------
    // Branding
    // ---------------------------------------------------------------------
    @Column(name = "LOGO_URL", length = 1000)
    private String logoUrl;

    @Column(name = "FAVICON_URL", length = 1000)
    private String faviconUrl;

    // ---------------------------------------------------------------------
    // Colors (Angular Material / UI tokens)
    // ---------------------------------------------------------------------
    @Column(name = "PRIMARY_COLOR", length = 20)
    private String primaryColor; // Hex like #3f51b5

    @Column(name = "SECONDARY_COLOR", length = 20)
    private String secondaryColor;

    @Column(name = "BACKGROUND_COLOR", length = 20)
    private String backgroundColor;

    @Column(name = "TEXT_COLOR", length = 20)
    private String textColor;

    @Column(name = "ACCENT_COLOR", length = 20)
    private String accentColor; // Hex like #ff4081

    @Column(name = "WARN_COLOR", length = 20)
    private String warnColor; // Hex like #f44336

    @Column(name = "LINK_COLOR", length = 20)
    private String linkColor;

    @Column(name = "BUTTON_TEXT_COLOR", length = 20)
    private String buttonTextColor;

    // ---------------------------------------------------------------------
    // Typography & Mode
    // ---------------------------------------------------------------------
    @Column(name = "FONT_FAMILY", length = 100)
    private String fontFamily;

    @Enumerated(EnumType.STRING)
    @Column(name = "THEME_MODE", length = 10)
    private ThemeMode themeMode; // LIGHT | DARK | AUTO

    // ---------------------------------------------------------------------
    // Extensibility
    // ---------------------------------------------------------------------
    @Column(name = "PROPERTIES_JSON", columnDefinition = "TEXT")
    private String propertiesJson; // For extra custom properties

    @Column(name = "CUSTOM_CSS", columnDefinition = "TEXT")
    private String customCss;

    @Column(name = "ALLOW_CUSTOM_CSS", nullable = false)
    @Builder.Default
    private boolean allowCustomCss = false;

    // ---------------------------------------------------------------------
    // Lifecycle
    // ---------------------------------------------------------------------
    @Column(name = "ACTIVE", nullable = false)
    @Builder.Default
    private boolean active = true;

    // ---------------------------------------------------------------------
    // Relationship (inverse side)
    // ---------------------------------------------------------------------
    @OneToOne(mappedBy = "theme", fetch = FetchType.LAZY)
    @JsonIgnore // Prevent infinite recursion in JSON
    private Tenant tenant;
}
