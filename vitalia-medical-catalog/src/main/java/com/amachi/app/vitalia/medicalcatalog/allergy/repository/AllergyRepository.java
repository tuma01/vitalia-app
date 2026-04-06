package com.amachi.app.vitalia.medicalcatalog.allergy.repository;

import com.amachi.app.core.common.repository.CommonRepository;
import com.amachi.app.vitalia.medicalcatalog.allergy.entity.Allergy;
import org.springframework.stereotype.Repository;

@Repository
public interface AllergyRepository extends CommonRepository<Allergy, Long> {
    boolean existsByCode(String code);
}
