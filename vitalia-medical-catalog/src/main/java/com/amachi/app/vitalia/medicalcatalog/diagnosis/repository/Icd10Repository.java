package com.amachi.app.vitalia.medicalcatalog.diagnosis.repository;

import com.amachi.app.core.common.repository.CommonRepository;
import com.amachi.app.vitalia.medicalcatalog.diagnosis.entity.Icd10;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface Icd10Repository extends CommonRepository<Icd10, Long> {

    /**
     * ✅ GLOBAL: busca un diagnóstico por su código CIE-10 en toda la plataforma.
     */
    Optional<Icd10> findByCode(String code);

    /**
     * 🔥 GLOBAL: Verificador de existencia transversal.
     */
    boolean existsByCode(String code);
}
