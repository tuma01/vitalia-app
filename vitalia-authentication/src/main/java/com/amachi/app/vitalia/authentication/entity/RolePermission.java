package com.amachi.app.vitalia.authentication.entity;

import com.amachi.app.vitalia.common.entity.Model;
import jakarta.persistence.*;
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
@Table(name = "ROLE_PERMISSION", uniqueConstraints = {
        @UniqueConstraint(name = "UK_ROLE_PERMISSION", columnNames = {"ROLE_ID", "PERMISSION_ID"})
})
public class RolePermission implements Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ROLE_ID", nullable = false, foreignKey = @ForeignKey(name = "FK_ROLE_PERMISSION_ROLE"))
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PERMISSION_ID", nullable = false, foreignKey = @ForeignKey(name = "FK_ROLE_PERMISSION_PERMISSION"))
    private Permission permission;
}
