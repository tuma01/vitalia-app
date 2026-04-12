package com.amachi.app.vitalia.medical.doctor.repository;

import com.amachi.app.vitalia.medical.doctor.entity.Doctor;
import com.amachi.app.core.common.repository.CommonRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio para la gestion del personal medico.
 */
@Repository
public interface DoctorRepository extends CommonRepository<Doctor, Long> {

    /**
     * Busca un médico por su número de licencia profesional.
     *
     * @param licenseNumber Número de licencia.
     * @return El médico encontrado o vacío.
     */
    Optional<Doctor> findByLicenseNumber(String licenseNumber);

    /**
     * Busca un médico por su número de proveedor RAMQ (Billing).
     *
     * @param ramqProviderNumber Número RAMQ.
     * @return El médico encontrado o vacío.
     */
    Optional<Doctor> findByRamqProviderNumber(String ramqProviderNumber);

    /**
     * Busca un médico por su registro de personal (Employee).
     *
     * @param employeeId ID del empleado vinculado.
     * @return El médico encontrado o vacío.
     */
    Optional<Doctor> findByEmployeeId(Long employeeId);
}
