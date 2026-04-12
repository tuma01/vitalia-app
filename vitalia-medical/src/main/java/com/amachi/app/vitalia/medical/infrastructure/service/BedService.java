package com.amachi.app.vitalia.medical.infrastructure.service;

import com.amachi.app.core.common.service.GenericService;
import com.amachi.app.vitalia.medical.infrastructure.dto.search.BedSearchDto;
import com.amachi.app.vitalia.medical.infrastructure.entity.Bed;

/**
 * Interfaz de servicio para la gestion de camas hospitalarias.
 */
public interface BedService extends GenericService<Bed, BedSearchDto> {

    /**
     * Actualiza el estado de una cama (Ej: AVAILABLE, OCCUPIED).
     */
    Bed updateBedStatus(Long id, com.amachi.app.core.common.enums.BedStatus status);
}
