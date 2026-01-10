package com.amachi.app.vitalia.medicalcatalog.demographic.entity;

import com.amachi.app.core.common.entity.Auditable;
import com.amachi.app.core.common.entity.Model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "CAT_GENDER", uniqueConstraints = {
        @UniqueConstraint(name = "UK_GENDER_CODE", columnNames = { "CODE" })
})
public class Gender extends Auditable<String> implements Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Gender Code cannot be empty")
    @Column(name = "CODE", nullable = false, unique = true, length = 20)
    private String code; // e.g., M, F, NB

    @NotBlank(message = "Gender Name cannot be empty")
    @Column(name = "NAME", nullable = false, length = 50)
    private String name; // e.g., Masculino, Femenino

    @Column(name = "ACTIVE", nullable = false)
    @Builder.Default
    private Boolean active = true;
}
