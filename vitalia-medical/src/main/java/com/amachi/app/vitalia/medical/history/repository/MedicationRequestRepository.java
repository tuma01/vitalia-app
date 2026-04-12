package com.amachi.app.vitalia.medical.history.repository;

import com.amachi.app.vitalia.medical.history.entity.MedicationRequest;
import com.amachi.app.core.common.repository.CommonRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicationRequestRepository extends CommonRepository<MedicationRequest, Long> {
    List<MedicationRequest> findByEncounterId(Long encounterId);
}
