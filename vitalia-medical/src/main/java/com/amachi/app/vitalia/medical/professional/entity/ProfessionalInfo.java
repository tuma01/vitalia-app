package com.amachi.app.vitalia.medical.professional.entity;

import com.amachi.app.core.common.entity.BaseTenantEntity;
import com.amachi.app.core.common.entity.Model;
import com.amachi.app.core.common.entity.SoftDeletable;
import com.amachi.app.core.common.enums.ProfessionalRoleContext;
import com.amachi.app.core.domain.entity.Organization;
import com.amachi.app.core.domain.entity.Person;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.envers.Audited;

import java.time.LocalDate;

/**
 * Registro de trayectoria profesional y posición laboral (SaaS Elite Tier).
 * Rastrea periodos de actividad, roles específicos y métricas de desempeño laboral.
 */
@Entity
@Table(
    name = "MED_PROFESSIONAL_INFO",
    indexes = {
        @Index(name = "IDX_PROF_INFO_TENANT", columnList = "TENANT_ID"),
        @Index(name = "IDX_PROF_INFO_PERSON", columnList = "FK_ID_PERSON")
    }
)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Audited
@Schema(description = "Información de trayectoria profesional laboral (Clinical Tier)")
public class ProfessionalInfo extends BaseTenantEntity implements Model, SoftDeletable {

    @Column(name = "IS_DELETED", nullable = false)
    @Builder.Default
    private Boolean isDeleted = false;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "FK_ID_PERSON", nullable = false, foreignKey = @ForeignKey(name = "FK_MED_PROF_INFO_PERSON"))
    private Person person;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ID_ORGANIZATION", foreignKey = @ForeignKey(name = "FK_MED_PROF_INFO_ORG"))
    private Organization organization;

    @Column(name = "PERIOD_START_DATE", nullable = false)
    private LocalDate periodStartDate;

    @Column(name = "PERIOD_END_DATE")
    private LocalDate periodEndDate;

    @Column(name = "IS_CURRENT", nullable = false)
    @Builder.Default
    private Boolean isCurrent = true;

    @Enumerated(EnumType.STRING)
    @Column(name = "ROLE_CONTEXT", nullable = false, length = 50)
    private ProfessionalRoleContext professionalRoleContext;

    @Column(name = "POSITION", length = 100)
    private String position;

    @Column(name = "DEPARTMENT", length = 100)
    private String department;

    @Column(name = "EMPLOYEE_ID", length = 50)
    private String employeeId;

    @Column(name = "LICENSE_NUMBER", length = 100)
    private String licenseNumber;

    @Column(name = "EXPERIENCE_AT_START")
    private Integer yearsOfExperienceAtStart;

    @Column(name = "CONTRACT_TYPE", length = 50)
    private String contractType;

    @Column(name = "WORK_SCHEDULE", length = 100)
    private String workSchedule;

    @Column(name = "SALARY_GRADE", length = 50)
    private String salaryGrade;

    @Column(name = "SUPERVISOR", length = 100)
    private String supervisor;

    @Column(name = "PERFORMANCE_RATING", precision = 3, scale = 1)
    private Double performanceRating;

    @Column(name = "NOTES", columnDefinition = "TEXT")
    private String notes;

    @Override
    public void delete() {
        this.isDeleted = true;
    }

    @PrePersist
    @PreUpdate
    private void normalize() {
        if (this.position != null) this.position = this.position.trim().toUpperCase();
        if (this.department != null) this.department = this.department.trim().toUpperCase();
        if (this.licenseNumber != null) this.licenseNumber = this.licenseNumber.trim().toUpperCase();
        if (this.employeeId != null) this.employeeId = this.employeeId.trim().toUpperCase();
    }
}
