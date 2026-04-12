package com.amachi.app.vitalia.medical.history.repository;

import com.amachi.app.vitalia.medical.history.entity.Condition;
import com.amachi.app.core.common.repository.CommonRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConditionRepository extends CommonRepository<Condition, Long> {
    List<Condition> findByPatientId(Long patientId);
    List<Condition> findByEncounterId(Long encounterId);
}
