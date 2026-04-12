package com.amachi.app.vitalia.medical.profile.entity;

import com.amachi.app.core.common.entity.BaseTenantEntity;
import com.amachi.app.core.common.entity.Model;
import com.amachi.app.core.common.entity.SoftDeletable;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.envers.Audited;

import java.time.LocalDate;

/**
 * Representación de la formación académica del profesional (SaaS Elite Tier).
 */
@Entity
@Table(
    name = "MED_USER_EDUCATION",
    indexes = {
        @Index(name = "IDX_USER_EDU_TENANT", columnList = "TENANT_ID"),
        @Index(name = "IDX_USER_EDU_PROFILE", columnList = "FK_ID_USERPROFILE")
    }
)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Audited
@Schema(description = "Registro de formación académica curricular")
public class Education extends BaseTenantEntity implements Model, SoftDeletable {

    @Column(name = "IS_DELETED", nullable = false)
    @Builder.Default
    private Boolean isDeleted = false;

    @Column(name = "INSTITUTION", nullable = false, length = 150)
    private String institution;

    @Column(name = "DEGREE", nullable = false, length = 150)
    private String degree;

    @Column(name = "START_DATE", nullable = false)
    private LocalDate startDate;

    @Column(name = "END_DATE")
    private LocalDate endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ID_USERPROFILE", nullable = false, foreignKey = @ForeignKey(name = "FK_MED_EDUCATION_PROFILE"))
    private UserProfile profile;

    @Override
    public void delete() {
        this.isDeleted = true;
    }

    @PrePersist
    @PreUpdate
    private void normalize() {
        if (this.institution != null) this.institution = this.institution.trim().toUpperCase();
        if (this.degree != null) this.degree = this.degree.trim().toUpperCase();
    }
}
