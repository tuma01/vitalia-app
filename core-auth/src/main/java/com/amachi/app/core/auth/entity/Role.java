package com.amachi.app.core.auth.entity;

import com.amachi.app.core.common.entity.BaseTenantEntity;
import com.amachi.app.core.common.entity.Model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Entidad que representa un Rol de Seguridad (SaaS Elite Tier).
 */
@Entity
@Table(name = "AUT_ROLE", uniqueConstraints = {
    @UniqueConstraint(name = "UK_ROLE_NAME", columnNames = {"NAME"})
})
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@SuperBuilder
@Schema(description = "Security roles define system-wide or tenant-specific permissions")
public class Role extends BaseTenantEntity implements Model {

    @NotBlank(message = "Role name {err.required}")
    @Column(name = "NAME", nullable = false, length = 50, unique = true)
    private String name;

    @Column(name = "DESCRIPTION", length = 200)
    private String description;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "AUT_ROLE_PERMISSION_MAP",
        joinColumns = @JoinColumn(name = "ROLE_ID", referencedColumnName = "ID"),
        inverseJoinColumns = @JoinColumn(name = "PERMISSION_ID", referencedColumnName = "ID")
    )
    @Builder.Default
    private Set<Permission> permissions = new HashSet<>();

    public void addPermission(Permission permission) {
        if (this.permissions == null) {
            this.permissions = new HashSet<>();
        }
        this.permissions.add(permission);
    }

    public void removePermission(Permission permission) {
        if (this.permissions != null) {
            this.permissions.remove(permission);
        }
    }

    @PrePersist
    @PreUpdate
    private void normalizeRole() {
        if (this.name != null) {
            this.name = this.name.toUpperCase().trim();
        }
        if (getTenantId() == null) {
            setTenantId("SYSTEM");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Role role)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(name, role.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name);
    }
}
