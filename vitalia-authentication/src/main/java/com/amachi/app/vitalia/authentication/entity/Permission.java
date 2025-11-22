package com.amachi.app.vitalia.authentication.entity;

import com.amachi.app.vitalia.common.entity.Auditable;
import com.amachi.app.vitalia.common.entity.Model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "PERMISSION", uniqueConstraints = {
        @UniqueConstraint(name = "UK_NAME_PERMISSION", columnNames = {"NAME"})
})
public class Permission extends Auditable<String> implements Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Permission name cannot be empty")
    @Column(name = "NAME", nullable = false, length = 100)
    private String name;

    @Column(name = "DESCRIPTION", length = 255)
    private String description;
}
