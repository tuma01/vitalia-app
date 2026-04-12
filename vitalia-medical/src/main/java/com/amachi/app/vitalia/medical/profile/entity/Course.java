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
 * Capacitación continua, cursos y diplomados (SaaS Elite Tier).
 */
@Entity
@Table(
    name = "MED_USER_COURSE",
    indexes = {
        @Index(name = "IDX_USER_CRS_TENANT", columnList = "TENANT_ID"),
        @Index(name = "IDX_USER_CRS_PROFILE", columnList = "FK_ID_USERPROFILE")
    }
)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Audited
@Schema(description = "Registro de cursos y capacitaciones continuas — SaaS Elite Tier")
public class Course extends BaseTenantEntity implements Model, SoftDeletable {

    @Column(name = "IS_DELETED", nullable = false)
    @Builder.Default
    private Boolean isDeleted = false;

    @Column(name = "TITLE", nullable = false, length = 150)
    private String title;

    @Column(name = "INSTITUTION", nullable = false, length = 150)
    private String institution;

    @Column(name = "DESCRIPTION", length = 500)
    private String description;

    @Column(name = "COURSE_DATE", nullable = false)
    private LocalDate date;

    @Column(name = "DURATION_HOURS", nullable = false)
    private int durationInHours;

    @Column(name = "CERTIFICATE_REF")
    private String certificate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ID_USERPROFILE", nullable = false, foreignKey = @ForeignKey(name = "FK_MED_COURSE_PROFILE"))
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
