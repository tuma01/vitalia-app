package com.amachi.app.core.geography.province.repository;

import com.amachi.app.core.common.repository.CommonRepository;
import com.amachi.app.core.geography.province.entity.Province;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProvinceRepository extends CommonRepository<Province, Long> {
    Optional<Province> findByName(String name);
}
