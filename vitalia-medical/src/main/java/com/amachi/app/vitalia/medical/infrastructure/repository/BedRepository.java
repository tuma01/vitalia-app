package com.amachi.app.vitalia.medical.infrastructure.repository;

import com.amachi.app.core.common.enums.BedStatusEnum;
import com.amachi.app.vitalia.medical.infrastructure.entity.Bed;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.amachi.app.core.common.repository.CommonRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para la gestion de camas hospitalarias.
 */
@Repository
public interface BedRepository extends CommonRepository<Bed, Long> {

    /**
     * Busca camas por su codigo identificador unico.
     *
     * @param bedCode Codigo de la cama.
     * @param pageable Paginacion.
     * @return Pagina de camas.
     */
    Page<Bed> findByBedCodeContainingIgnoreCase(String bedCode, Pageable pageable);

    /**
     * Busca camas por su estado operativo.
     *
     * @param status Estado Operativo (AVAILABLE, OCCUPIED, etc.).
     * @param pageable Paginacion.
     * @return Pagina de camas filtradas por estado.
     */
    Page<Bed> findByStatus(BedStatusEnum status, Pageable pageable);
}
