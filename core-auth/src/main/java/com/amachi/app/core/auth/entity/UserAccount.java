package com.amachi.app.core.auth.entity;

import com.amachi.app.core.common.entity.BaseTenantEntity;
import com.amachi.app.core.common.entity.Model;
import com.amachi.app.core.domain.entity.Person;
import com.amachi.app.core.domain.tenant.entity.Tenant;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * Entidad UserAccount (SaaS Elite Tier).
 * Vincula una identidad global (User) con un contexto específico (Tenant).
 */
@Entity
@Table(
    name = "AUT_USER_ACCOUNT",
    uniqueConstraints = {
        @UniqueConstraint(name = "UK_USER_TENANT_ACCOUNT", columnNames = {"USER_ID", "TENANT_ID"})
    },
    indexes = {
        @Index(name = "IDX_USER_ACCOUNT_TENANT", columnList = "TENANT_ID"),
        @Index(name = "IDX_USER_ACCOUNT_PERSON", columnList = "PERSON_ID")
    }
)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Schema(description = "Link between global user identity and specific tenant context")
public class UserAccount extends BaseTenantEntity implements Model {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false, foreignKey = @ForeignKey(name = "FK_USER_ACCOUNT_USER"))
    private User user;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "PERSON_ID", nullable = false, foreignKey = @ForeignKey(name = "FK_USER_ACCOUNT_PERSON"))
    private Person person;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(
        name = "TENANT_ID", 
        referencedColumnName = "CODE", 
        nullable = false, 
        insertable = false, 
        updatable = false,
        foreignKey = @ForeignKey(name = "FK_USER_ACCOUNT_TENANT")
    )
    private Tenant tenant; // Reference to the actual Tenant entity for business logic

    @PrePersist
    @PreUpdate
    private void normalizeAccount() {
        if (this.tenant != null && getTenantId() == null) {
            // Geographic standard: propaga el código del tenant al campo de aislamiento sistémico
            setTenantId(this.tenant.getCode());
        }
    }

    public Long getUserId() {
        return user != null ? user.getId() : null;
    }

    public Long getPersonId() {
        return person != null ? person.getId() : null;
    }

    public Long getTenantPk() {
        return tenant != null ? tenant.getId() : null;
    }
}
