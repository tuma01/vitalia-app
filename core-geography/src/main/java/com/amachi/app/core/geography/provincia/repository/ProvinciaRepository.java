package com.amachi.app.core.geography.provincia.repository;

import com.amachi.app.core.common.repository.CommonRepository;
import com.amachi.app.core.geography.provincia.entity.Provincia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface ProvinciaRepository extends CommonRepository<Provincia, Long> {

    Page<Provincia> findByNombreLike(String nombre, Pageable pageable);
}
