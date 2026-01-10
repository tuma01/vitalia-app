package com.amachi.app.vitalia.medicalcatalog.allergy.repository;

import com.amachi.app.vitalia.medicalcatalog.allergy.entity.Allergy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AllergyRepository extends JpaRepository<Allergy, Long>, JpaSpecificationExecutor<Allergy> {
}
