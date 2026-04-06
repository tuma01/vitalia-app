package com.amachi.app.vitalia.medicalcatalog.specialty.repository;

import com.amachi.app.core.common.repository.CommonRepository;
import com.amachi.app.vitalia.medicalcatalog.specialty.entity.MedicalSpecialty;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MedicalSpecialtyRepository extends CommonRepository<MedicalSpecialty, Long> {
    
    /**
     * ✅ GLOBAL: busca una especialidad por su código único en toda la plataforma.
     */
    Optional<MedicalSpecialty> findByCode(String code);

    /**
     * 🔥 GLOBAL: Verificador de existencia transversal.
     */
    boolean existsByCode(String code);
}
