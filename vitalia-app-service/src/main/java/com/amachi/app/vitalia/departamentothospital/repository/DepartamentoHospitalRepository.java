package com.amachi.app.vitalia.departamentothospital.repository;

import com.amachi.app.vitalia.departamentothospital.entity.DepartamentoHospital;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartamentoHospitalRepository extends JpaRepository<DepartamentoHospital, Long>, JpaSpecificationExecutor<DepartamentoHospital> {
    Page<DepartamentoHospital> findByNameLike(String name, Pageable pageable);
}
