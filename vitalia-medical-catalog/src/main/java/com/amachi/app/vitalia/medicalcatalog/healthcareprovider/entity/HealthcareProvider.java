package com.amachi.app.vitalia.medicalcatalog.healthcareprovider.entity;

import com.amachi.app.core.common.entity.Auditable;
import com.amachi.app.core.common.entity.Model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * Represents a Healthcare Provider (Insurance Company/Payer) catalog entry.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "CAT_HEALTHCARE_PROVIDER", uniqueConstraints = {
        @UniqueConstraint(name = "UK_PROVIDER_CODE", columnNames = { "CODE" }),
        @UniqueConstraint(name = "UK_PROVIDER_TAX_ID", columnNames = { "TAX_ID" })
})
public class HealthcareProvider extends Auditable<String> implements Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", updatable = false, nullable = false)
    private Long id;

    @NotBlank(message = "Provider Code cannot be empty")
    @Column(name = "CODE", nullable = false, unique = true, length = 20)
    private String code;

    @NotBlank(message = "Provider Name cannot be empty")
    @Column(name = "NAME", nullable = false, length = 250)
    private String name;

    @NotBlank(message = "Tax ID cannot be empty")
    @Column(name = "TAX_ID", nullable = false, unique = true, length = 50)
    private String taxId;

    @Column(name = "EMAIL", length = 100)
    private String email;

    @Column(name = "PHONE", length = 50)
    private String phone;

    @Column(name = "ACTIVE", nullable = false)
    @Builder.Default
    private Boolean active = true;
}
