package com.amachi.app.vitalia.medical.professional.service;

import com.amachi.app.core.common.service.GenericService;
import com.amachi.app.vitalia.medical.professional.dto.search.ProfessionalInfoSearchDto;
import com.amachi.app.vitalia.medical.professional.entity.ProfessionalInfo;

import java.util.List;
import java.util.Optional;

/**
 * Contrato de servicio para la gestión de trayectoria profesional.
 */
public interface ProfessionalInfoService extends GenericService<ProfessionalInfo, ProfessionalInfoSearchDto> {

    /**
     * Obtiene la trayectoria profesional de una persona.
     * @param personId ID de la persona.
     * @return Lista de trayectoria.
     */
    List<ProfessionalInfo> getByPersonId(Long personId);

    /**
     * Obtiene el cargo actual de una persona.
     * @param personId ID de la persona.
     * @return Cargo actual.
     */
    Optional<ProfessionalInfo> getCurrentPosition(Long personId);
}
