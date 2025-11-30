package com.amachi.app.vitalia.tenant.entity;
import com.amachi.app.vitalia.common.enums.TenantAdminLevel;
import com.amachi.app.vitalia.person.entity.Person;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "TENANT_ADMIN")
@PrimaryKeyJoinColumn(name = "ID")
@DiscriminatorValue("TENANT_ADMIN")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class TenantAdmin extends Person {

    @Enumerated(EnumType.STRING)
    @Column(name = "ADMIN_LEVEL", nullable = false, length = 50)
    private TenantAdminLevel adminLevel;

    @Column(name = "TENANT_CODE", nullable = false, length = 100)
    private String tenantCode;
}
