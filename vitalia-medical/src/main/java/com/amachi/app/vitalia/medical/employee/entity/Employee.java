import com.amachi.app.core.common.entity.DomainRole;
import com.amachi.app.core.common.entity.SoftDeletable;
import com.amachi.app.core.common.enums.EmployeeStatus;
import com.amachi.app.core.common.enums.EmployeeType;
import com.amachi.app.core.common.enums.ProfessionalRoleContext;
import com.amachi.app.core.domain.entity.Person;
import com.amachi.app.vitalia.medical.doctor.entity.Doctor;
import com.amachi.app.vitalia.medical.infrastructure.entity.DepartmentUnit;
import com.amachi.app.vitalia.medical.nurse.entity.Nurse;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.envers.Audited;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Gestión administrativa y laboral del personal hospitalario (SaaS Elite Tier).
 * Identidad heredada de Person (GLOBAL) + Aislamiento por Tenant (LOCAL).
 */
@Entity
@Table(name = "MED_EMPLOYEE",
    uniqueConstraints = {
        @UniqueConstraint(name = "UK_EMP_TENANT_CODE", columnNames = {"TENANT_ID", "EMPLOYEE_CODE", "IS_DELETED"}),
        @UniqueConstraint(name = "UK_EMP_IDENTITY_TENANT", columnNames = {"FK_ID_PERSON", "TENANT_ID", "IS_DELETED"})
    },
    indexes = {
        @Index(name = "IDX_EMP_TENANT", columnList = "TENANT_ID"),
        @Index(name = "IDX_EMP_PERSON", columnList = "FK_ID_PERSON"),
        @Index(name = "IDX_EMP_CODE", columnList = "EMPLOYEE_CODE")
    }
)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@SuperBuilder
@Audited
@Schema(description = "Perfil administrativo de personal hospitalario — SaaS Elite Tier")
@EqualsAndHashCode(callSuper = true)
public class Employee extends BaseTenantEntity implements SoftDeletable, DomainRole {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "FK_ID_PERSON", nullable = false, foreignKey = @ForeignKey(name = "FK_MED_EMP_PERSON"))
    private Person person;

    @Enumerated(EnumType.STRING)
    @Column(name = "PROFESSIONAL_ROLE_CONTEXT", length = 50)
    private ProfessionalRoleContext professionalRoleContext;

    @Column(name = "IS_DELETED", nullable = false)
    @Builder.Default
    private Boolean isDeleted = false;

    @Column(name = "EMPLOYEE_CODE", length = 50)
    private String employeeCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "EMPLOYEE_TYPE", nullable = false, length = 40)
    private EmployeeType employeeType;

    @Enumerated(EnumType.STRING)
    @Column(name = "EMPLOYEE_STATUS", nullable = false, length = 30)
    private EmployeeStatus employeeStatus;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ID_USER", foreignKey = @ForeignKey(name = "FK_MED_EMP_USER"))
    private User user;

    @Column(name = "JOB_POSITION", length = 120)
    private String jobPosition;

    @Column(name = "HIRE_DATE")
    private LocalDate hireDate;

    @Column(name = "IS_ACTIVE")
    @Builder.Default
    private Boolean active = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ID_DEPT_UNIT", foreignKey = @ForeignKey(name = "FK_MED_EMP_DEPT_UNIT"))
    private DepartmentUnit departmentUnit;

    @Column(name = "SALARY", precision = 12, scale = 2)
    private BigDecimal salary;

    @Column(name = "EMPLOYMENT_TYPE", length = 50)
    private String employmentType;

    @Column(name = "WORK_SHIFT", length = 50)
    private String workShift;

    @Column(name = "EMP_EMERGENCY_CONTACT", length = 200)
    private String emergencyContact;

    @Override
    public void delete() {
        this.isDeleted = true;
    }

    @PrePersist
    @PreUpdate
    private void normalizeEmployee() {
        if (this.employeeCode != null) this.employeeCode = this.employeeCode.trim().toUpperCase();
        if (this.jobPosition != null) this.jobPosition = this.jobPosition.trim();
    }
}
