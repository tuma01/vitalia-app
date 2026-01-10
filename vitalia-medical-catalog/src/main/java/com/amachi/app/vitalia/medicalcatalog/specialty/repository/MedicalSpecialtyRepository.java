package com.amachi.app.vitalia.medicalcatalog.specialty.repository;

import com.amachi.app.vitalia.medicalcatalog.specialty.entity.MedicalSpecialty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MedicalSpecialtyRepository extends JpaRepository<MedicalSpecialty, Long>, JpaSpecificationExecutor<MedicalSpecialty> {
    Optional<MedicalSpecialty> findByCode(String code);
}
