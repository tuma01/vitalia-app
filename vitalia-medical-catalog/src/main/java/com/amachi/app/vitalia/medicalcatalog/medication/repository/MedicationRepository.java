package com.amachi.app.vitalia.medicalcatalog.medication.repository;

import com.amachi.app.vitalia.medicalcatalog.medication.entity.Medication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MedicationRepository extends JpaRepository<Medication, Long>, JpaSpecificationExecutor<Medication> {
    Optional<Medication> findByCode(String code);
}
