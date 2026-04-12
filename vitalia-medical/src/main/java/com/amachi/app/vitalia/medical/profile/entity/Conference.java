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
 * Participación en eventos científicos y conferencias médicas (SaaS Elite Tier).
 */
@Entity
@Table(
    name = "MED_USER_CONFERENCE",
    indexes = {
        @Index(name = "IDX_USER_CONF_TENANT", columnList = "TENANT_ID"),
        @Index(name = "IDX_USER_CONF_PROFILE", columnList = "FK_ID_USERPROFILE")
    }
)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Audited
@Schema(description = "Registro de conferencias y eventos científicos — SaaS Elite Tier")
public class Conference extends BaseTenantEntity implements Model, SoftDeletable {

    @Column(name = "IS_DELETED", nullable = false)
    @Builder.Default
    private Boolean isDeleted = false;

    @Column(name = "TOPIC", length = 150, nullable = false)
    private String topic;

    @Column(name = "DESCRIPTION", length = 500)
    private String description;

    @Column(name = "ORGANIZER", length = 150)
    private String organizer;

    @Column(name = "LOCATION")
    private String location;

    @Column(name = "IS_INTERNATIONAL", nullable = false)
    private boolean international;

    @Column(name = "CONFERENCE_DATE", nullable = false)
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ID_USERPROFILE", nullable = false, foreignKey = @ForeignKey(name = "FK_MED_CONFERENCE_PROFILE"))
    private UserProfile profile;

    @Override
    public void delete() {
        this.isDeleted = true;
    }

    @PrePersist
    @PreUpdate
    private void normalize() {
        if (this.topic != null) this.topic = this.topic.trim().toUpperCase();
        if (this.organizer != null) this.organizer = this.organizer.trim().toUpperCase();
    }
}
