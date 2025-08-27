package com.amachi.app.vitalia.nurseprofessionspeciality.repository;

import com.amachi.app.vitalia.nurseprofessionspeciality.entity.NurseProfessionSpeciality;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface NurseProfessionSpecialityRepository extends JpaRepository<NurseProfessionSpeciality, Long>, JpaSpecificationExecutor<NurseProfessionSpeciality> {

    Page<NurseProfessionSpeciality> findByNameLike(String name, Pageable pageable);
}
