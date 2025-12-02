package com.amachi.app.vitalia.authentication.entity;

import com.amachi.app.vitalia.common.entity.Auditable;
import com.amachi.app.vitalia.common.entity.Model;
import com.amachi.app.vitalia.common.entity.Tenant;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "USER_ACCOUNT")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class UserAccount extends Auditable<String> implements Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;   // 🔹 credenciales

    @Column(name = "PERSON_ID", nullable = false)
    private Long personId;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "TENANT_ID", nullable = false)
    private Tenant tenant;

}
