package com.amachi.app.vitalia.hospital.repository;

import com.amachi.app.vitalia.hospital.entity.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HospitalRepository extends JpaRepository<Hospital, Long> {

    /**
     * Devuelve el hospital marcado como predeterminado.
     * Debe haber solo uno con isDefault = true.
     */
    Optional<Hospital> findByIsDefaultTrue();

    /**
     * Verifica si ya existe un hospital marcado como predeterminado.
     */
    boolean existsByIsDefaultTrue();

    /**
     * Verifica si existe un hospital con un código específico.
     */
    boolean existsByHospitalCode(String hospitalCode);

    /**
     * Busca un hospital por su código.
     */
    Optional<Hospital> findByHospitalCode(String hospitalCode);
}
