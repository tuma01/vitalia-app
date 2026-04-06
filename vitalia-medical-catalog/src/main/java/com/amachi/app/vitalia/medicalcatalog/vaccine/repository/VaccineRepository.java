package com.amachi.app.vitalia.medicalcatalog.vaccine.repository;

import com.amachi.app.core.common.repository.CommonRepository;
import com.amachi.app.vitalia.medicalcatalog.vaccine.entity.Vaccine;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VaccineRepository extends CommonRepository<Vaccine, Long> {
    
    /**
     * ✅ GLOBAL: busca una vacuna por su código único en toda la plataforma.
     */
    Optional<Vaccine> findByCode(String code);

    /**
     * 🔥 GLOBAL: Verificador de existencia transversal.
     */
    boolean existsByCode(String code);
}
