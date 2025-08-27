package com.amachi.app.vitalia.nurse.repository;

import com.amachi.app.vitalia.nurse.entity.Nurse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface NurseRepository extends JpaRepository<Nurse, Long>, JpaSpecificationExecutor<Nurse> {

    Page<Nurse> findByNombreLike(String nombre, Pageable pageable);
}
