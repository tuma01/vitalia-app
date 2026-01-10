package com.amachi.app.vitalia.medicalcatalog.healthcareprovider.repository;

import com.amachi.app.vitalia.medicalcatalog.healthcareprovider.entity.HealthcareProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HealthcareProviderRepository extends JpaRepository<HealthcareProvider, Long>, JpaSpecificationExecutor<HealthcareProvider> {
    Optional<HealthcareProvider> findByCode(String code);
    Optional<HealthcareProvider> findByTaxId(String taxId);
}
