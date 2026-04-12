package com.amachi.app.core.auth.entity;

import com.amachi.app.core.common.entity.BaseTenantEntity;
import com.amachi.app.core.common.entity.Model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "AUT_PERMISSION", uniqueConstraints = {
        @UniqueConstraint(name = "UK_NAME_PERMISSION", columnNames = { "NAME" })
})
public class Permission extends BaseTenantEntity implements Model {

    // ID heredado de BaseTenantEntity

    @NotBlank(message = "Permission name cannot be empty")
    @Column(name = "NAME", nullable = false, length = 100)
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @PrePersist
    @PreUpdate
    private void normalizePermission() {
        if (this.name != null) {
            this.name = this.name.toUpperCase().trim();
        }
        if (getTenantId() == null) {
            setTenantId("SYSTEM");
        }
    }
}
