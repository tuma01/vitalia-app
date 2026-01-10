package com.amachi.app.vitalia.medicalcatalog.diagnosis.repository;

import com.amachi.app.vitalia.medicalcatalog.diagnosis.entity.Icd10;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface Icd10Repository extends JpaRepository<Icd10, Long>, JpaSpecificationExecutor<Icd10> {
    Optional<Icd10> findByCode(String code);
}
