package com.amachi.app.core.geography.municipio.repository;

import com.amachi.app.core.geography.municipio.entity.Municipio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MunicipioRepository extends JpaRepository<Municipio, Long>, JpaSpecificationExecutor<Municipio> {

    Page<Municipio> findByNombreLike(String nombre, Pageable pageable);
}
