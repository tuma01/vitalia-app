package com.amachi.app.vitalia.patient.repository;

import com.amachi.app.vitalia.nurse.entity.Nurse;
import com.amachi.app.vitalia.patient.entity.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long>, JpaSpecificationExecutor<Patient> {

    Optional<Patient> findByIdCard(String idCard); // ejemplo por documento de identidad

    Page<Nurse> findByApellidoPaternoLike(String apellidoPaterno, Pageable pageable);

}
