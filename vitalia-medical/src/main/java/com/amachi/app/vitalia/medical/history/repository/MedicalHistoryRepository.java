package com.amachi.app.vitalia.medical.history.repository;

import com.amachi.app.vitalia.medical.history.entity.MedicalHistory;
import com.amachi.app.core.common.repository.CommonRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MedicalHistoryRepository extends CommonRepository<MedicalHistory, Long> {

    /**
     * ✅ Multi-tenant aware: encuentra el expediente del paciente en este Tenant.
     */
    Optional<MedicalHistory> findByPatientIdAndTenantId(Long patientId, String tenantId);

    /**
     * 🔥 Verificador de unicidad del Folio (History Number) por Tenant.
     */
    boolean existsByHistoryNumberAndTenantId(String historyNumber, String tenantId);

    /**
     * ✅ Optimized retrieval by UUID for external clinical integrations.
     */
    Optional<MedicalHistory> findByDocumentUuidAndTenantId(String documentUuid, String tenantId);
}
