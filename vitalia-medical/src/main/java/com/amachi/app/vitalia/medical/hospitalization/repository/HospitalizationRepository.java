package com.amachi.app.vitalia.medical.hospitalization.repository;

import com.amachi.app.core.common.repository.CommonRepository;
import com.amachi.app.vitalia.medical.hospitalization.entity.Hospitalization;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HospitalizationRepository extends CommonRepository<Hospitalization, Long> {

    Optional<Hospitalization> findByEpisodeUuid(String episodeUuid);

    boolean existsByPatientIdAndStatus(Long patientId, com.amachi.app.vitalia.medical.common.enums.HospitalizationStatus status);
}
