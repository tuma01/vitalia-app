package com.amachi.app.vitalia.medical.infrastructure.repository;

import com.amachi.app.vitalia.medical.infrastructure.entity.DepartmentUnitType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.amachi.app.core.common.repository.CommonRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para el catalogo de tipos de unidades hospitalarias.
 */
@Repository
public interface DepartmentUnitTypeRepository extends CommonRepository<DepartmentUnitType, Long> {

    /**
     * Busca tipos de unidad por nombre parcial e ignora mayusculas.
     *
     * @param name Nombre parcial a buscar.
     * @param pageable Paginacion.
     * @return Pagina de tipos de unidad.
     */
    Page<DepartmentUnitType> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
