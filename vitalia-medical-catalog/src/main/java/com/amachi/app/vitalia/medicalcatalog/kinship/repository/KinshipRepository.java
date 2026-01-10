package com.amachi.app.vitalia.medicalcatalog.kinship.repository;

import com.amachi.app.vitalia.medicalcatalog.kinship.entity.Kinship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KinshipRepository extends JpaRepository<Kinship, Long>, JpaSpecificationExecutor<Kinship> {
    Optional<Kinship> findByCode(String code);
}
