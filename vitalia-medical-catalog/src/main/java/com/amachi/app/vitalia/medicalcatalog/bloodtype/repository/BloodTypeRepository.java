package com.amachi.app.vitalia.medicalcatalog.bloodtype.repository;

import com.amachi.app.vitalia.medicalcatalog.bloodtype.entity.BloodType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BloodTypeRepository extends JpaRepository<BloodType, Long>, JpaSpecificationExecutor<BloodType> {
    Optional<BloodType> findByCode(String code);
}
