package com.amachi.app.vitalia.superadmin.entity;


import com.amachi.app.vitalia.common.entity.Model;
import com.amachi.app.vitalia.common.entity.Tenant;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "TENANT_CONFIG")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TenantConfig implements Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TENANT_ID", nullable = false, unique = true)
    private Tenant tenant;

    @Column(name = "FALLBACK_HEADER", length = 100)
    private String fallbackHeader;

    @Column(name = "ALLOW_LOCAL")
    private Boolean allowLocal;

    @Column(name = "DEFAULT_DOMAIN", length = 200)
    private String defaultDomain;

    @Column(name = "LOCALE", length = 20)
    private String locale;

    @Column(name = "TIMEZONE", length = 100)
    private String timezone;

    @Column(name = "MAX_USERS")
    private Integer maxUsers;

    @Column(name = "STORAGE_QUOTA_BYTES")
    private Long storageQuotaBytes;

    @Column(name = "REQUIRE_EMAIL_VERIFICATION")
    private Boolean requireEmailVerification;

    @Column(name = "PASSWORD_POLICY_JSON", columnDefinition = "TEXT")
    private String passwordPolicyJson;

    @Column(name = "EXTRA_JSON", columnDefinition = "TEXT")
    private String extraJson;

    // --- NUEVOS CAMPOS DE THEME ---
    @Column(name = "LOGO_URL", length = 1000)
    private String logoUrl;

    @Column(name = "PRIMARY_COLOR", length = 20)
    private String primaryColor;

    @Column(name = "ACCENT_COLOR", length = 20)
    private String accentColor;

    @Column(name = "TEXT_COLOR", length = 20)
    private String textColor;

    @Column(name = "BACKGROUND_COLOR", length = 20)
    private String backgroundColor;

    @Column(name = "LINK_COLOR", length = 20)
    private String linkColor;

    @Column(name = "BUTTON_TEXT_COLOR", length = 20)
    private String buttonTextColor;

    @Column(name = "THEME_MODE", length = 10)
    private String themeMode; // LIGHT | DARK | AUTO

    @Column(name = "CUSTOM_CSS", columnDefinition = "TEXT")
    private String customCss;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
