package com.amachi.app.vitalia.management.tenantadmin.entity;

import com.amachi.app.core.domain.tenant.entity.Tenant;
import com.amachi.app.core.common.enums.TenantAdminLevel;
import com.amachi.app.core.domain.entity.Person;
import com.amachi.app.core.auth.entity.User;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "MGT_TENANT_ADMIN")
@PrimaryKeyJoinColumn(name = "ID")
@DiscriminatorValue("ADMIN")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class TenantAdmin extends Person {

    @Enumerated(EnumType.STRING)
    @Column(name = "ADMIN_LEVEL", nullable = false, length = 50)
    private TenantAdminLevel adminLevel;

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
    @JoinColumn(name = "TENANT_CODE", referencedColumnName = "CODE", nullable = false)
    private Tenant tenant;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_ID", nullable = false, unique = true)
    private User user;
}
