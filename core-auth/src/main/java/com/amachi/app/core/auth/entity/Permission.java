package com.amachi.app.core.auth.entity;

import com.amachi.app.core.common.entity.Auditable;
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
public class Permission extends Auditable<String> implements Model {

    // ID heredado de Auditable/BaseEntity

    @NotBlank(message = "Permission name cannot be empty")
    @Column(name = "NAME", nullable = false, length = 100)
    private String name;

    @Column(name = "DESCRIPTION", length = 255)
    private String description;
}
