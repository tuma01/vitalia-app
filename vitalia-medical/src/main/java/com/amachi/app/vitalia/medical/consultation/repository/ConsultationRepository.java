package com.amachi.app.vitalia.medical.consultation.repository;

import com.amachi.app.vitalia.medical.consultation.entity.Consultation;
import com.amachi.app.core.common.repository.CommonRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsultationRepository extends CommonRepository<Consultation, Long> {
    List<Consultation> findByMedicalHistoryId(Long medicalHistoryId);
}
