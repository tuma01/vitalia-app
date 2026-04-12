package com.amachi.app.vitalia.medical.history.specification;

import com.amachi.app.core.common.specification.BaseSpecification;
import com.amachi.app.vitalia.medical.history.dto.search.ConditionSearchDto;
import com.amachi.app.vitalia.medical.history.entity.Condition;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.List;

/**
 * Motor de búsqueda avanzada para condiciones clínicas y diagnósticos (SaaS Elite Tier).
 */
public class ConditionSpecification extends BaseSpecification<Condition> {

    private final ConditionSearchDto criteria;

    public ConditionSpecification(ConditionSearchDto criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<Condition> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        // 🛡️ Filtros Base Automáticos (TENANT_ID e IS_DELETED) via BaseSpecification helper
        predicates.addAll(buildBasePredicates(root, cb));

        if (criteria == null) return cb.and(predicates.toArray(new Predicate[0]));

        if (criteria.getPatientId() != null) {
            predicates.add(cb.equal(root.get("patient").get("id"), criteria.getPatientId()));
        }

        if (criteria.getMedicalHistoryId() != null) {
            predicates.add(cb.equal(root.get("medicalHistory").get("id"), criteria.getMedicalHistoryId()));
        }

        if (criteria.getEncounterId() != null) {
            predicates.add(cb.equal(root.get("encounter").get("id"), criteria.getEncounterId()));
        }

        if (criteria.getPractitionerId() != null) {
            predicates.add(cb.equal(root.get("practitioner").get("id"), criteria.getPractitionerId()));
        }

        if (criteria.getEpisodeOfCareId() != null) {
            predicates.add(cb.equal(root.get("episodeOfCare").get("id"), criteria.getEpisodeOfCareId()));
        }

        if (criteria.getIcd10Id() != null) {
            predicates.add(cb.equal(root.get("icd10").get("id"), criteria.getIcd10Id()));
        }

        if (criteria.getClinicalStatus() != null) {
            predicates.add(cb.equal(root.get("clinicalStatus"), criteria.getClinicalStatus()));
        }

        if (criteria.getConditionType() != null) {
            predicates.add(cb.equal(root.get("conditionType"), criteria.getConditionType()));
        }

        if (criteria.getSeverity() != null) {
            predicates.add(cb.equal(root.get("severity"), criteria.getSeverity()));
        }

        if (criteria.getDiagnosisDateFrom() != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("diagnosisDate"), criteria.getDiagnosisDateFrom()));
        }

        if (criteria.getDiagnosisDateTo() != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("diagnosisDate"), criteria.getDiagnosisDateTo()));
        }

        return cb.and(predicates.toArray(new Predicate[0]));
    }
}
