package com.amachi.app.vitalia.medical.infrastructure.repository;

import com.amachi.app.vitalia.medical.infrastructure.entity.DepartmentUnit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.amachi.app.core.common.repository.CommonRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio para la gestion de unidades fisicas del hospital.
 */
@Repository
public interface DepartmentUnitRepository extends CommonRepository<DepartmentUnit, Long> {

    /**
     * Busca unidades de un piso específico.
     *
     * @param floor Número o nombre del piso.
     * @param pageable Paginación.
     * @return Página de unidades filtradas por piso.
     */
    Page<DepartmentUnit> findByFloorContainingIgnoreCase(String floor, Pageable pageable);

    /**
     * Busca una unidad por su código funcional único.
     *
     * @param code Código de la unidad.
     * @return La unidad encontrada o vacío.
     */
    Optional<DepartmentUnit> findByCode(String code);

    /**
     * Busca la unidad a cargo de un empleado específico.
     *
     * @param headOfEmployeeId ID del empleado jefe.
     * @return La unidad encontrada o vacío.
     */
    Optional<DepartmentUnit> findByHeadOfEmployeeId(Long headOfEmployeeId);
}
