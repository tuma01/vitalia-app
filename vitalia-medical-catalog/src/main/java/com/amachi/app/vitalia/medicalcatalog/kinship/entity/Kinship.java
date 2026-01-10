package com.amachi.app.vitalia.medicalcatalog.kinship.entity;

import com.amachi.app.core.common.entity.Auditable;
import com.amachi.app.core.common.entity.Model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * Represents a Kinship/Relationship catalog entry (Master Data) for emergency contacts.
 */
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "CAT_KINSHIP", uniqueConstraints = {
        @UniqueConstraint(name = "UK_KINSHIP_CODE", columnNames = {"CODE"})
})
public class Kinship extends Auditable<String> implements Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", updatable = false, nullable = false)
    private Long id;

    @NotBlank(message = "Kinship Code cannot be empty")
    @Column(name = "CODE", nullable = false, unique = true, length = 20)
    private String code; // e.g., FATHER, MOTHER, SPOUSE

    @NotBlank(message = "Kinship Name cannot be empty")
    @Column(name = "NAME", nullable = false, length = 100)
    private String name; // e.g., Padre, Madre, Cónyuge

    @Column(name = "ACTIVE", nullable = false)
    @Builder.Default
    private Boolean active = true;
}
