package com.amachi.app.vitalia.authentication.entity;

import com.amachi.app.vitalia.common.entity.Auditable;
import com.amachi.app.vitalia.common.entity.Model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@SuperBuilder
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

    @Size(max = 255)
    @Column(name = "DESCRIPTION", length = 255)
    private String description;
}
