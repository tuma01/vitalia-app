package com.amachi.app.vitalia.medical.history.repository;

import com.amachi.app.vitalia.medical.history.entity.Observation;
import com.amachi.app.core.common.repository.CommonRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ObservationRepository extends CommonRepository<Observation, Long> {
    List<Observation> findByEncounterId(Long encounterId);
}
