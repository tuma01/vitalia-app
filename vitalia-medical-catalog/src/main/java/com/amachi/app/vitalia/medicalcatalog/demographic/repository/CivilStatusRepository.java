package com.amachi.app.vitalia.medicalcatalog.demographic.repository;

import com.amachi.app.core.common.repository.CommonRepository;
import com.amachi.app.vitalia.medicalcatalog.demographic.entity.CivilStatus;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CivilStatusRepository extends CommonRepository<CivilStatus, Long> {
    
    /**
     * ✅ GLOBAL: busca un estado civil por su código único en toda la plataforma.
     */
    Optional<CivilStatus> findByCode(String code);

    /**
     * 🔥 GLOBAL: Verificador de existencia transversal.
     */
    boolean existsByCode(String code);
}
