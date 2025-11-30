package com.amachi.app.vitalia.employee.entity;

import com.amachi.app.vitalia.authentication.entity.User;
import com.amachi.app.vitalia.common.entity.Tenant;
import com.amachi.app.vitalia.common.enums.EmployeeStatus;
import com.amachi.app.vitalia.common.enums.EmployeeType;
import com.amachi.app.vitalia.common.enums.RelationStatus;
import com.amachi.app.vitalia.person.entity.Person;
import com.amachi.app.vitalia.person.entity.PersonTenant;
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
@Table(name = "EMPLOYEE")
public class Employee extends Person {

    // -------------------------------------------------------
    // 🔹 TIPO DE EMPLEADO (Administrativo, Médico, Enfermero…)
    // -------------------------------------------------------
    @Enumerated(EnumType.STRING)
    @Column(name = "EMPLOYEE_TYPE", nullable = false, length = 40)
    private EmployeeType employeeType;

    // -------------------------------------------------------
    // 🔹 ESTATUS LABORAL DENTRO DEL SISTEMA (Activo, Suspendido…)
    // -------------------------------------------------------
    @Enumerated(EnumType.STRING)
    @Column(name = "EMPLOYEE_STATUS", nullable = false, length = 30)
    private EmployeeStatus employeeStatus;

    // -------------------------------------------------------
    // 🔹 RELACIÓN CON EL USUARIO DEL SISTEMA
    // -------------------------------------------------------
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ID_USER",
            foreignKey = @ForeignKey(name = "FK_EMPLOYEE_USER"))
    private User user;

    // -------------------------------------------------------
    // 🔹 DATOS LABORALES
    // -------------------------------------------------------
    @Size(max = 150)
    @Column(name = "PROFESION")
    private String profesion; // Ej: Médico General, Cirujano

    @Size(max = 120)
    @Column(name = "PUESTO")
    private String puesto; // Ej: Jefe de Emergencias

    @Size(max = 120)
    @Column(name = "CARGO")
    private String cargo; // Ej: Responsable de Unidad

    @Size(max = 50)
    @Column(name = "NRO_COLEGIO_MEDICO")
    private String nroColegioMedico; // Opcional, solo para médicos

    // -------------------------------------------------------
    // 🔹 FECHAS LABORALES
    // -------------------------------------------------------
    @Column(name = "FECHA_INGRESO")
    private LocalDate fechaIngreso;

    @Column(name = "FECHA_SALIDA")
    private LocalDate fechaSalida;

    // -------------------------------------------------------
    // 🔹 MÉTODOS DE UTILIDAD
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
