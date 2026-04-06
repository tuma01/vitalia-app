package com.amachi.app.vitalia.medicalcatalog.kinship.repository;

import com.amachi.app.core.common.repository.CommonRepository;
import com.amachi.app.vitalia.medicalcatalog.kinship.entity.Kinship;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KinshipRepository extends CommonRepository<Kinship, Long> {
    
    /**
     * ✅ GLOBAL: busca un parentesco por su código único en toda la plataforma.
     */
    Optional<Kinship> findByCode(String code);

    /**
     * 🔥 GLOBAL: Verificador de existencia transversal.
     */
    boolean existsByCode(String code);
}
