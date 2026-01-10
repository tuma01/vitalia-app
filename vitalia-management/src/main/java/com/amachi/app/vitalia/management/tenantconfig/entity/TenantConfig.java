package com.amachi.app.vitalia.management.tenantconfig.entity;

import com.amachi.app.core.common.entity.Auditable;
import com.amachi.app.core.common.entity.Model;
import com.amachi.app.core.domain.tenant.entity.Tenant;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "MGT_TENANT_CONFIG")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class TenantConfig extends Auditable<String> implements Model {

    // ID heredado de Auditable/BaseEntity

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TENANT_ID", nullable = false, foreignKey = @ForeignKey(name = "FK_TENANTCONFIG_TENANT"))
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
}
