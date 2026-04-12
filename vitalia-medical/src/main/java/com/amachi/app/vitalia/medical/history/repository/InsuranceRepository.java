package com.amachi.app.vitalia.medical.history.repository;

import com.amachi.app.vitalia.medical.history.entity.Insurance;
import com.amachi.app.core.common.repository.CommonRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio profesional para la gestión de Pólizas de Seguro.
 */
@Repository
public interface InsuranceRepository extends CommonRepository<Insurance, Long> {

    /**
     * Busca los seguros vinculados a una historia clínica.
     * @param medicalHistoryId ID de la historia clínica.
     * @return Lista de seguros.
     */
    List<Insurance> findByMedicalHistoryId(Long medicalHistoryId);

    /**
     * Busca el seguro actualmente activo para un paciente.
     * @param medicalHistoryId ID de la historia clínica.
     * @return Seguro activo.
     */
    java.util.Optional<Insurance> findByMedicalHistoryIdAndIsCurrentTrue(Long medicalHistoryId);
}
