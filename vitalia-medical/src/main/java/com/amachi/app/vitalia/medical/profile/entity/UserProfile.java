package com.amachi.app.vitalia.medical.profile.entity;

import com.amachi.app.core.common.entity.BaseTenantEntity;
import com.amachi.app.core.common.entity.Model;
import com.amachi.app.core.common.entity.SoftDeletable;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.envers.Audited;

import java.util.ArrayList;
import java.util.List;

/**
 * Contenedor maestro del perfil extendido del profesional de salud (SaaS Elite Tier).
 * Incluye biografía, fotografía y trayectoria académica/profesional detallada.
 */
@Entity
@Table(
    name = "MED_USER_PROFILE",
    indexes = {
        @Index(name = "IDX_USER_PROFILE_TENANT", columnList = "TENANT_ID")
    }
)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true, exclude = {"educationList", "experienceList", "courseList", "conferenceList"})
@Audited
@Schema(description = "Perfil curricular extendido del profesional — SaaS Elite Tier")
public class UserProfile extends BaseTenantEntity implements Model, SoftDeletable {

    @Column(name = "IS_DELETED", nullable = false)
    @Builder.Default
    private Boolean isDeleted = false;

    @Column(name = "BIOGRAPHY", length = 2000)
    private String biography;

    @Lob
    @Column(name = "PHOTO")
    private byte[] photo;

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Education> educationList = new ArrayList<>();

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Experience> experienceList = new ArrayList<>();

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Course> courseList = new ArrayList<>();

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Conference> conferenceList = new ArrayList<>();

    @Override
    public void delete() {
        this.isDeleted = true;
    }

    @PrePersist
    @PreUpdate
    private void normalize() {
        if (this.biography != null) this.biography = this.biography.trim();
    }
}
