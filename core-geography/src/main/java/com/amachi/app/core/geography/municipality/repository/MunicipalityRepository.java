package com.amachi.app.core.geography.municipality.repository;

import com.amachi.app.core.common.repository.CommonRepository;
import com.amachi.app.core.geography.municipality.entity.Municipality;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MunicipalityRepository extends CommonRepository<Municipality, Long> {
    Optional<Municipality> findByName(String name);
}
