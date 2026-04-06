package com.amachi.app.core.geography.municipio.repository;

import com.amachi.app.core.common.repository.CommonRepository;
import com.amachi.app.core.geography.municipio.entity.Municipio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface MunicipioRepository extends CommonRepository<Municipio, Long> {

    Page<Municipio> findByNombreLike(String nombre, Pageable pageable);
}
