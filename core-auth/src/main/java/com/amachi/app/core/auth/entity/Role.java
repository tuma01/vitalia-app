package com.amachi.app.core.auth.entity;

import com.amachi.app.core.common.entity.Auditable;
import com.amachi.app.core.common.entity.Model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@SuperBuilder
@Entity
@Table(name = "AUT_ROLE")
@EqualsAndHashCode(callSuper = false)
public class Role extends Auditable<String> implements Model {

    // ID heredado de Auditable/BaseEntity

    @NotBlank(message = "Name {err.required}")
    @Column(name = "NAME", unique = true, nullable = false)
    private String name;

    @Size(max = 255)
    @Column(name = "DESCRIPTION", length = 255)
    private String description;
}
