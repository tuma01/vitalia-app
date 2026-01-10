package com.amachi.app.vitalia.medicalcatalog.identity.entity;

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
@Table(name = "CAT_IDENTIFICATION_TYPE", uniqueConstraints = {
        @UniqueConstraint(name = "UK_ID_TYPE_CODE", columnNames = { "CODE" })
})
public class IdentificationType extends Auditable<String> implements Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Identification Code cannot be empty")
    @Column(name = "CODE", nullable = false, unique = true, length = 20)
    private String code; // e.g., CC, PA, TI

    @NotBlank(message = "Identification Name cannot be empty")
    @Column(name = "NAME", nullable = false, length = 100)
    private String name; // e.g., Cédula de Ciudadanía, Pasaporte

    @Column(name = "ACTIVE", nullable = false)
    @Builder.Default
    private Boolean active = true;
}
