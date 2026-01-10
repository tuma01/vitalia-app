package com.amachi.app.vitalia.medicalcatalog.demographic.repository;

import com.amachi.app.vitalia.medicalcatalog.demographic.entity.Gender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface GenderRepository extends JpaRepository<Gender, Long>, JpaSpecificationExecutor<Gender> {
}
