package com.amachi.app.vitalia.common.entity;

import jakarta.persistence.*;
import com.amachi.app.vitalia.common.enums.ThemeMode;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Entity
@Table(name = "THEME")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Theme extends Auditable<String> implements Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME", nullable = false, length = 100)
    private String name;

    // --- Branding ---
    @Column(name = "LOGO_URL", length = 1000)
    private String logoUrl;

    @Column(name = "FAVICON_URL", length = 1000)
    private String faviconUrl;

    // --- Colors (Angular Material Palette) ---
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

    // --- Typography & Extra ---
    @Column(name = "FONT_FAMILY", length = 100)
    private String fontFamily;

    @Enumerated(EnumType.STRING)
    @Column(name = "THEME_MODE", length = 10)
    private ThemeMode themeMode; // LIGHT | DARK | AUTO

    @Column(name = "PROPERTIES_JSON", columnDefinition = "TEXT")
    private String propertiesJson; // For extra custom properties

    @Column(name = "LINK_COLOR", length = 20)
    private String linkColor;

    @Column(name = "BUTTON_TEXT_COLOR", length = 20)
    private String buttonTextColor;

    @Column(name = "CUSTOM_CSS", columnDefinition = "TEXT")
    private String customCss;

    @OneToOne(mappedBy = "theme", fetch = FetchType.LAZY)
    @JsonIgnore // Prevent infinite recursion in JSON
    private Tenant tenant;
}
