package com.amachi.app.vitalia.medical.professional.repository;

import com.amachi.app.vitalia.medical.professional.entity.ProfessionalInfo;
import com.amachi.app.core.common.repository.CommonRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio profesional para la gestión de trayectoria laboral.
 */
@Repository
public interface ProfessionalInfoRepository extends CommonRepository<ProfessionalInfo, Long> {

    /**
     * Busca la trayectoria profesional vinculada a una persona.
     * @param personId ID de la persona.
     * @return Lista de trayectoria.
     */
    List<ProfessionalInfo> findByPersonId(Long personId);

    /**
     * Busca la posición laboral actual de una persona.
     * @param personId ID de la persona.
     * @return Posición actual.
     */
    java.util.Optional<ProfessionalInfo> findByPersonIdAndIsCurrentTrue(Long personId);
}
