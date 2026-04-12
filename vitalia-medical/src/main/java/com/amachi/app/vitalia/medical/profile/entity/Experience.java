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
 * Registro de trayectoria laboral previa del profesional (SaaS Elite Tier).
 */
@Entity
@Table(
    name = "MED_USER_EXPERIENCE",
    indexes = {
        @Index(name = "IDX_USER_EXP_TENANT", columnList = "TENANT_ID"),
        @Index(name = "IDX_USER_EXP_PROFILE", columnList = "FK_ID_USERPROFILE")
    }
)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Audited
@Schema(description = "Registro de experiencia profesional previa — SaaS Elite Tier")
public class Experience extends BaseTenantEntity implements Model, SoftDeletable {

    @Column(name = "IS_DELETED", nullable = false)
    @Builder.Default
    private Boolean isDeleted = false;

    @Column(name = "TITLE", nullable = false, length = 150)
    private String title;

    @Column(name = "INSTITUTION", nullable = false, length = 150)
    private String institution;

    @Column(name = "START_DATE", nullable = false)
    private LocalDate startDate;

    @Column(name = "END_DATE")
    private LocalDate endDate;

    @Column(name = "DESCRIPTION", length = 500)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ID_USERPROFILE", nullable = false, foreignKey = @ForeignKey(name = "FK_MED_EXPERIENCE_PROFILE"))
    private UserProfile profile;

    @Override
    public void delete() {
        this.isDeleted = true;
    }

    @PrePersist
    @PreUpdate
    private void normalize() {
        if (this.title != null) this.title = this.title.trim().toUpperCase();
        if (this.institution != null) this.institution = this.institution.trim().toUpperCase();
    }
}
