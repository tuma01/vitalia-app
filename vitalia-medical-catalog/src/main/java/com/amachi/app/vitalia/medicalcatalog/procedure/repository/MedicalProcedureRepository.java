package com.amachi.app.vitalia.medicalcatalog.procedure.repository;

import com.amachi.app.vitalia.medicalcatalog.procedure.entity.MedicalProcedure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MedicalProcedureRepository extends JpaRepository<MedicalProcedure, Long>, JpaSpecificationExecutor<MedicalProcedure> {
    Optional<MedicalProcedure> findByCode(String code);
}
