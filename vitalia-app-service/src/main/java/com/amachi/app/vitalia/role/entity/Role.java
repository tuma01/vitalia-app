package com.amachi.app.vitalia.role.entity;

import com.amachi.app.vitalia.common.entities.Auditable;
import com.amachi.app.vitalia.common.entities.Model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "ROLE")
@EqualsAndHashCode(callSuper=false)
public class Role extends Auditable<String> implements Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", updatable = false, nullable = false)
    private Long id;

    @NotBlank(message = "Name {err.required}")
    @Column(name = "NAME", unique = true, nullable = false)
    private String name;

    public Role(String name) {
        this.name = name;
    }
}
