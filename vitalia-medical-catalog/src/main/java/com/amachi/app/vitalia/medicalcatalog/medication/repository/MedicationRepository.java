package com.amachi.app.vitalia.medicalcatalog.medication.repository;

import com.amachi.app.core.common.repository.CommonRepository;
import com.amachi.app.vitalia.medicalcatalog.medication.entity.Medication;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MedicationRepository extends CommonRepository<Medication, Long> {
    
    /**
     * ✅ GLOBAL: busca un medicamento por su código único en toda la plataforma.
     */
    Optional<Medication> findByCode(String code);

    /**
     * 🔥 GLOBAL: Verificador de existencia transversal.
     */
    boolean existsByCode(String code);
}
