package com.amachi.app.vitalia.medicalcatalog.identity.repository;

import com.amachi.app.core.common.repository.CommonRepository;
import com.amachi.app.vitalia.medicalcatalog.identity.entity.IdentificationType;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IdentificationTypeRepository extends CommonRepository<IdentificationType, Long> {
    
    /**
     * ✅ GLOBAL: busca un tipo de identificación por su código único en toda la plataforma.
     */
    Optional<IdentificationType> findByCode(String code);

    /**
     * 🔥 GLOBAL: Verificador de existencia transversal.
     */
    boolean existsByCode(String code);
}
