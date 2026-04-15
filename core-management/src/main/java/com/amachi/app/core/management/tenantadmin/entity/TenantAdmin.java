package com.amachi.app.core.management.tenantadmin.entity;

import com.amachi.app.core.auth.entity.User;
import com.amachi.app.core.common.entity.BaseTenantEntity;
import com.amachi.app.core.common.enums.TenantAdminLevel;
import com.amachi.app.core.domain.entity.Person;
import com.amachi.app.core.domain.tenant.entity.Tenant;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * Entidad de Gestión de Tenant (SaaS Elite Tier).
 * Usa composición para vincularse a la identidad global (Person).
 */
@Entity
@Table(name = "MGT_TENANT_ADMIN")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class TenantAdmin extends BaseTenantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "ADMIN_LEVEL", nullable = false, length = 50)
    private TenantAdminLevel adminLevel;

    /**
     * Objeto tenant completo (para lógica de negocio).
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TENANT_OBJECT_ID", referencedColumnName = "CODE", nullable = false)
    private Tenant tenant;

    /**
     * Vínculo a la identidad global (Composición pura).
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ID_PERSON", nullable = false, foreignKey = @ForeignKey(name = "FK_TENANT_ADMIN_PERSON"))
    private Person person;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ID_USER", foreignKey = @ForeignKey(name = "FK_TENANT_ADMIN_USER"))
    private User user;

    @PrePersist
    @PreUpdate
    private void normalizeAdmin() {
        if (this.tenant != null) {
            setTenantId(this.tenant.getCode());
        }
    }
}
