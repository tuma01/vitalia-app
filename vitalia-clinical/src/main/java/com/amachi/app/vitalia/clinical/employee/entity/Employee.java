package com.amachi.app.vitalia.clinical.employee.entity;

import com.amachi.app.core.auth.entity.User;
import com.amachi.app.core.domain.tenant.entity.Tenant;
import com.amachi.app.core.common.enums.EmployeeStatus;
import com.amachi.app.core.common.enums.EmployeeType;
import com.amachi.app.core.common.enums.RelationStatus;
import com.amachi.app.core.domain.entity.Person;
import com.amachi.app.core.domain.entity.PersonTenant;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

@PrimaryKeyJoinColumn(name = "ID")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "MGT_EMPLOYEE")
public class Employee extends Person {

    // -------------------------------------------------------
    // ≡ƒö╣ TIPO DE EMPLEADO (Administrativo, M├⌐dico, EnfermeroΓÇª)
    // -------------------------------------------------------
    @Enumerated(EnumType.STRING)
    @Column(name = "EMPLOYEE_TYPE", nullable = false, length = 40)
    private EmployeeType employeeType;

    // -------------------------------------------------------
    // ≡ƒö╣ ESTATUS LABORAL DENTRO DEL SISTEMA (Activo, SuspendidoΓÇª)
    // -------------------------------------------------------
    @Enumerated(EnumType.STRING)
    @Column(name = "EMPLOYEE_STATUS", nullable = false, length = 30)
    private EmployeeStatus employeeStatus;

    // -------------------------------------------------------
    // ≡ƒö╣ RELACI├ôN CON EL USUARIO DEL SISTEMA
    // -------------------------------------------------------
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ID_USER",
            foreignKey = @ForeignKey(name = "FK_EMPLOYEE_USER"))
    private User user;

    // -------------------------------------------------------
    // ≡ƒö╣ DATOS LABORALES
    // -------------------------------------------------------
    @Size(max = 150)
    @Column(name = "PROFESION")
    private String profesion; // Ej: M├⌐dico General, Cirujano

    @Size(max = 120)
    @Column(name = "PUESTO")
    private String puesto; // Ej: Jefe de Emergencias

    @Size(max = 120)
    @Column(name = "CARGO")
    private String cargo; // Ej: Responsable de Unidad

    @Size(max = 50)
    @Column(name = "NRO_COLEGIO_MEDICO")
    private String nroColegioMedico; // Opcional, solo para m├⌐dicos

    // -------------------------------------------------------
    // ≡ƒö╣ FECHAS LABORALES
    // -------------------------------------------------------
    @Column(name = "FECHA_INGRESO")
    private LocalDate fechaIngreso;

    @Column(name = "FECHA_SALIDA")
    private LocalDate fechaSalida;

    // -------------------------------------------------------
    // ≡ƒö╣ M├ëTODOS DE UTILIDAD
    // -------------------------------------------------------

    /**
     * Obtiene todos los tenants donde trabaja el empleado.
     * (Hereda personTenants de Person)
     */
    @Transient
    public Set<Tenant> getTenants() {
        return getPersonTenants().stream()
                .map(PersonTenant::getTenant)
                .collect(Collectors.toSet());
    }

    @Transient
    public boolean belongsToTenant(Long tenantId) {
        return getPersonTenants() != null && getPersonTenants().stream()
                .anyMatch(pt ->
                        pt.getTenant() != null &&
                                pt.getTenant().getId() != null &&
                                pt.getTenant().getId().equals(tenantId) &&
                                pt.getRelationStatus() == RelationStatus.ACTIVE
                );
    }
}
