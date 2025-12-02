package com.amachi.app.vitalia.person.entity;

// SuperAdmin.java
import com.amachi.app.vitalia.common.enums.SuperAdminLevel;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;


@Entity
@Table(name = "SUPER_ADMIN")
@PrimaryKeyJoinColumn(name = "ID")
@DiscriminatorValue("SUPER_ADMIN")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class SuperAdmin extends Person {

    @Enumerated(EnumType.STRING)
    @Column(name = "LEVEL", nullable = false, length = 30)
    private SuperAdminLevel level;

    @Column(name = "GLOBAL_ACCESS", nullable = false)
    private boolean globalAccess;
}
