package com.amachi.app.vitalia.doctor.repository;

import com.amachi.app.vitalia.doctor.entity.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long>, JpaSpecificationExecutor<Doctor> {

    Page<Doctor> findByNombreLike(String nombre, Pageable pageable);

    /*
     * Busca un doctor por su número de licencia.
     * @param licenseNumber el número de licencia del doctor
     * @return un Optional que contiene el doctor si se encuentra, o vacío si no se encuentra
     */
    Optional<Doctor> findByLicenseNumber(String licenseNumber);
}