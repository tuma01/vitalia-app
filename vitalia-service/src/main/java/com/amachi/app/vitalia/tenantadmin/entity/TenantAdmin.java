package com.amachi.app.vitalia.tenantadmin.entity;

import com.amachi.app.vitalia.common.entity.Tenant;
import com.amachi.app.vitalia.common.enums.TenantAdminLevel;
import com.amachi.app.vitalia.person.entity.Person;
import com.amachi.app.vitalia.authentication.entity.User;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "TENANT_ADMIN")
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
