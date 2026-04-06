package com.amachi.app.vitalia.medicalcatalog.procedure.repository;

import com.amachi.app.core.common.repository.CommonRepository;
import com.amachi.app.vitalia.medicalcatalog.procedure.entity.MedicalProcedure;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MedicalProcedureRepository extends CommonRepository<MedicalProcedure, Long> {
    
    /**
     * ✅ GLOBAL: busca un procedimiento por su código único en toda la plataforma.
     */
    Optional<MedicalProcedure> findByCode(String code);

    /**
     * 🔥 GLOBAL: Verificador de existencia transversal.
     */
    boolean existsByCode(String code);
}
