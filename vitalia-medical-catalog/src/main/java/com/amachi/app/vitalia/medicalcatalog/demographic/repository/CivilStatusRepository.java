package com.amachi.app.vitalia.medicalcatalog.demographic.repository;

import com.amachi.app.vitalia.medicalcatalog.demographic.entity.CivilStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CivilStatusRepository extends JpaRepository<CivilStatus, Long>, JpaSpecificationExecutor<CivilStatus> {
}
