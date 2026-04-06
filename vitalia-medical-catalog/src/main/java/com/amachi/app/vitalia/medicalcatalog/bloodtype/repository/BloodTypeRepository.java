package com.amachi.app.vitalia.medicalcatalog.bloodtype.repository;

import com.amachi.app.core.common.repository.CommonRepository;
import com.amachi.app.vitalia.medicalcatalog.bloodtype.entity.BloodType;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BloodTypeRepository extends CommonRepository<BloodType, Long> {
    
    /**
     * ✅ GLOBAL: busca un grupo sanguíneo por su código único en toda la plataforma.
     */
    Optional<BloodType> findByCode(String code);

    /**
     * 🔥 GLOBAL: Verificador de existencia transversal.
     */
    boolean existsByCode(String code);
}
