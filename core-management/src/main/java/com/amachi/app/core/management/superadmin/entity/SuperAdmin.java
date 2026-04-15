package com.amachi.app.core.management.superadmin.entity;

import com.amachi.app.core.auth.entity.User;
import com.amachi.app.core.common.entity.BaseTenantEntity;
import com.amachi.app.core.common.enums.SuperAdminLevel;
import com.amachi.app.core.domain.entity.Person;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * Entidad de Gestión de Plataforma (SaaS Elite Tier).
 * Usa composición para vincularse a la identidad global (Person).
 */
@Entity
@Table(name = "MGT_SUPER_ADMIN")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class SuperAdmin extends BaseTenantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "LEVEL", nullable = false, length = 30)
    private SuperAdminLevel level;

    @Column(name = "GLOBAL_ACCESS", nullable = false)
    private Boolean globalAccess;

    /**
     * Vínculo a la identidad global (Composición pura).
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ID_PERSON", nullable = false, foreignKey = @ForeignKey(name = "FK_SUPER_ADMIN_PERSON"))
    private Person person;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ID_USER", foreignKey = @ForeignKey(name = "FK_SUPER_ADMIN_USER"))
    private User user;
}
