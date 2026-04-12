package com.amachi.app.vitalia.medical.history.repository;

import com.amachi.app.vitalia.medical.common.enums.EncounterStatus;
import com.amachi.app.vitalia.medical.history.entity.Encounter;
import com.amachi.app.core.common.repository.CommonRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EncounterRepository extends CommonRepository<Encounter, Long> {
    
    Optional<Encounter> findByAppointmentId(Long appointmentId);
    
    /**
     * Verifica si ya existe un encuentro en curso para una cita espec├¡fica.
     */
    boolean existsByAppointmentIdAndStatus(Long appointmentId, EncounterStatus status);
}
