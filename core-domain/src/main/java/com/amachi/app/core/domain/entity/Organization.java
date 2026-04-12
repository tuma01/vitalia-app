package com.amachi.app.core.domain.entity;

import com.amachi.app.core.common.entity.Auditable;
import com.amachi.app.core.common.entity.Model;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.envers.Audited;

/**
 * Representa una organización externa o interna (Hospital, Clínica, Aseguradora).
 */
@Entity
@Table(name = "DMN_ORGANIZATION")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@SuperBuilder
@Audited
@Schema(description = "Entidad genérica para organizaciones corporativas")
public class Organization extends Auditable<String> implements Model {

    @Column(name = "NAME", nullable = false, length = 200)
    private String name;

    @Column(name = "LEGAL_IDENTIFIER", length = 50)
    private String legalIdentifier;

    @Column(name = "TYPE", length = 50)
    private String type; // e.g., Hospital, Clinic, Insurance

    @Column(name = "ADDRESS", length = 250)
    private String address;

    @Column(name = "CONTACT_PHONE", length = 50)
    private String contactPhone;

    @Column(name = "ACTIVE", nullable = false)
    @Builder.Default
    private Boolean active = true;
}
