package com.amachi.app.core.geography.provincia.repository;

import com.amachi.app.core.geography.provincia.entity.Provincia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProvinciaRepository extends JpaRepository<Provincia, Long>, JpaSpecificationExecutor<Provincia> {

    Page<Provincia> findByNombreLike(String nombre, Pageable pageable);
}
