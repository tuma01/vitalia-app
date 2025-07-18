package com.amachi.app.vitalia.municipio.repository;

import com.amachi.app.vitalia.municipio.entity.Municipio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MunicipioRepository extends JpaRepository<Municipio, Long>, JpaSpecificationExecutor<Municipio> {
//    default Page<Municipio> getMunicipios(MunicipioSearchDto provinciaSearchDto, Pageable pageable) {
//        return findAll(new MunicipioSpecification(provinciaSearchDto), pageable);
//    }

    Page<Municipio> findByNombreLike(String nombre, Pageable pageable);
}
