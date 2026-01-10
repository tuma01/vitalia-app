package com.amachi.app.core.domain.entity;
import com.amachi.app.core.common.entity.Auditable;
import com.amachi.app.core.common.entity.Model;
import com.amachi.app.core.domain.tenant.entity.Tenant;
import com.amachi.app.core.common.enums.RelationStatus;
import com.amachi.app.core.common.enums.RoleContext;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@Table(name = "DMN_PERSON_TENANT",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "UK_PERSON_TENANT_ROLE_CONTEXT",
                        columnNames = {"FK_ID_PERSON", "FK_ID_TENANT", "ROLE_CONTEXT"})
        })
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class PersonTenant extends Auditable<String> implements Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ID_PERSON", nullable = false)
    private Person person;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ID_TENANT", nullable = false)
    private Tenant tenant;

    @Column(name = "NATIONAL_HEALTH_ID", length = 50)
    private String nationalHealthId;

    // ≡ƒö╣ Datos contextuales (ej: doctor en un hospital, paciente en otro)
    @Enumerated(EnumType.STRING)
    @Column(name = "ROLE_CONTEXT", nullable = false, length = 30)
    private RoleContext roleContext; // DOCTOR, PATIENT, etc.

    @Column(name = "DATE_REGISTERED", nullable = false)
    private LocalDateTime dateRegistered;

    @Column(name = "DATE_UNREGISTERED")
    private LocalDateTime  dateUnregistered;

    @Enumerated(EnumType.STRING)
    @Column(name = "RELATION_STATUS", nullable = false, length = 20)
    private RelationStatus relationStatus; // ACTIVO, INACTIVO, SUSPENDIDO, etc.

//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(name = "PERSON_TENANT_ROLE",
//            joinColumns = @JoinColumn(name = "PERSON_TENANT_ID"),
//            inverseJoinColumns = @JoinColumn(name = "ROLE_ID"))
//    private Set<Role> roles = new HashSet<>();
}
