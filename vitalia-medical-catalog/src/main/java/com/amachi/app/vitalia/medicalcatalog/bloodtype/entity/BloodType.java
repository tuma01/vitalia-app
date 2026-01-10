package com.amachi.app.vitalia.medicalcatalog.bloodtype.entity;

import com.amachi.app.core.common.entity.Auditable;
import com.amachi.app.core.common.entity.Model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * Represents a Blood Type catalog entry (Master Data).
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "CAT_BLOOD_TYPE", uniqueConstraints = {
        @UniqueConstraint(name = "UK_BLOOD_TYPE_CODE", columnNames = { "CODE" })
})
public class BloodType extends Auditable<String> implements Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", updatable = false, nullable = false)
    private Long id;

    @NotBlank(message = "Blood Type Code cannot be empty")
    @Column(name = "CODE", nullable = false, unique = true, length = 20)
    private String code; // e.g., A+, O-, B+

    @NotBlank(message = "Blood Type Name cannot be empty")
    @Column(name = "NAME", nullable = false, length = 50)
    private String name; // e.g., A Positivo, O Negativo

    @Column(name = "ACTIVE", nullable = false)
    @Builder.Default
    private Boolean active = true;
}
