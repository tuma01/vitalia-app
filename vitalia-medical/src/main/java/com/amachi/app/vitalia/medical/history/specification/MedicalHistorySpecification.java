package com.amachi.app.vitalia.medical.history.specification;

import com.amachi.app.core.common.specification.BaseSpecification;
import com.amachi.app.vitalia.medical.history.dto.search.MedicalHistorySearchDto;
import com.amachi.app.vitalia.medical.history.entity.MedicalHistory;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.List;

/**
 * Motor de búsqueda especializada para el expediente clínico (EHR Elite).
 */
public class MedicalHistorySpecification extends BaseSpecification<MedicalHistory> {

    private final MedicalHistorySearchDto criteria;

    public MedicalHistorySpecification(MedicalHistorySearchDto criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<MedicalHistory> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        // 🛡️ Filtros Base Automáticos (TENANT_ID e IS_DELETED) via BaseSpecification helper
        predicates.addAll(buildBasePredicates(root, cb));

        if (criteria == null) return cb.and(predicates.toArray(new Predicate[0]));

        if (criteria.getPatientId() != null) {
            predicates.add(cb.equal(root.get("patient").get("id"), criteria.getPatientId()));
        }

        if (criteria.getHistoryNumber() != null && !criteria.getHistoryNumber().isBlank()) {
            predicates.add(cb.equal(root.get("historyNumber"), criteria.getHistoryNumber()));
        }

        return cb.and(predicates.toArray(new Predicate[0]));
    }
}
