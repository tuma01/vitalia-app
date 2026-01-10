package com.amachi.app.vitalia.management.superadmin.entity;

// SuperAdmin.java
import com.amachi.app.core.domain.entity.Person;
import com.amachi.app.core.auth.entity.User;
import com.amachi.app.core.common.enums.SuperAdminLevel;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "MGT_SUPER_ADMIN")
@PrimaryKeyJoinColumn(name = "ID")
@DiscriminatorValue("SUPER_ADMIN")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class SuperAdmin extends Person {

    @Enumerated(EnumType.STRING)
    @Column(name = "LEVEL", nullable = false, length = 30)
    private SuperAdminLevel level;

    @Column(name = "GLOBAL_ACCESS", nullable = false)
    private Boolean globalAccess;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_ID")
    private User user;
}
