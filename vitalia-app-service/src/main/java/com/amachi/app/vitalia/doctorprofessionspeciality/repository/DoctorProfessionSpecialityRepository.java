package com.amachi.app.vitalia.doctorprofessionspeciality.repository;

import com.amachi.app.vitalia.doctorprofessionspeciality.entity.DoctorProfessionSpeciality;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorProfessionSpecialityRepository extends JpaRepository<DoctorProfessionSpeciality, Long>, JpaSpecificationExecutor<DoctorProfessionSpeciality> {

    Page<DoctorProfessionSpeciality> findByNameLike(String name, Pageable pageable);
}
