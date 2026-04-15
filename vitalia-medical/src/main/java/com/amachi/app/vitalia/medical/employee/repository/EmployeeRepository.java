package com.amachi.app.vitalia.medical.employee.repository;

import com.amachi.app.core.domain.entity.Person;
import com.amachi.app.core.domain.tenant.entity.Tenant;
import com.amachi.app.vitalia.medical.employee.entity.Employee;
import com.amachi.app.core.common.repository.CommonRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio para la gestion de empleados administrativos y generales.
 */
@Repository
public interface EmployeeRepository extends CommonRepository<Employee, Long> {

    /**
     * Busca un empleado por su código interno hospitalario.
     *
     * @param codigoEmpleado Código de empleado.
     * @return El empleado encontrado o vacío.
     */
    Optional<Employee> findByCodigoEmpleado(String codigoEmpleado);

    /**
     * Busca un empleado por su cédula o documento de identidad.
     *
     * @param idCard Cédula/DNI.
     * @return El empleado encontrado o vacío.
     */
    Optional<Employee> findByIdCard(String idCard);

    /**
     * Busca un empleado por su correo electrónico.
     *
     * @param email Correo electrónico.
     * @return El empleado encontrado o vacío.
     */
    Optional<Employee> findByEmail(String email);
    
    boolean existsByPersonAndTenant(Person person, Tenant tenant);
    Optional<Employee> findByPersonAndTenant(Person person, Tenant tenant);
}
