package com.amachi.app.vitalia.medicalcatalog.identity.repository;

import com.amachi.app.vitalia.medicalcatalog.identity.entity.IdentificationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface IdentificationTypeRepository extends JpaRepository<IdentificationType, Long>, JpaSpecificationExecutor<IdentificationType> {
}
