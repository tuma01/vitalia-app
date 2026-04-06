package com.amachi.app.vitalia.medicalcatalog.demographic.repository;

import com.amachi.app.core.common.repository.CommonRepository;
import com.amachi.app.vitalia.medicalcatalog.demographic.entity.Gender;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GenderRepository extends CommonRepository<Gender, Long> {
    
    /**
     * ✅ GLOBAL: busca un género por su código único en toda la plataforma.
     */
    Optional<Gender> findByCode(String code);

    /**
     * 🔥 GLOBAL: Verificador de existencia transversal.
     */
    boolean existsByCode(String code);
}
