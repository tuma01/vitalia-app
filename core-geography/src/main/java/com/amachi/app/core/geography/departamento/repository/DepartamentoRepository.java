package com.amachi.app.core.geography.departamento.repository;

import com.amachi.app.core.geography.departamento.entity.Departamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartamentoRepository extends JpaRepository<Departamento, Long>, JpaSpecificationExecutor<Departamento> {

    /**
     * Busca países por nombre utilizando una búsqueda por patrón.
     *
     * @param nombre del país a buscar.
     * @param pageable Paginación.
     * @return Página con los resultados filtrados por nombre.
     */
    Page<Departamento> findByNombreContainingIgnoreCase(String nombre, Pageable pageable);

}
