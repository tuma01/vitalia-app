package com.amachi.app.vitalia.medicalcatalog.healthcareprovider.repository;

import com.amachi.app.core.common.repository.CommonRepository;
import com.amachi.app.vitalia.medicalcatalog.healthcareprovider.entity.HealthcareProvider;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HealthcareProviderRepository extends CommonRepository<HealthcareProvider, Long> {
    
    /**
     * ✅ GLOBAL: busca un proveedor de salud por su código único en toda la plataforma.
     */
    Optional<HealthcareProvider> findByCode(String code);

    /**
     * ✅ GLOBAL: busca un proveedor de salud por su número fiscal en toda la plataforma.
     */
    Optional<HealthcareProvider> findByTaxId(String taxId);

    /**
     * 🔥 GLOBAL: Verificador de existencia transversal por código.
     */
    boolean existsByCode(String code);

    /**
     * 🔥 GLOBAL: Verificador de existencia transversal por ID fiscal.
     */
    boolean existsByTaxId(String taxId);
}
