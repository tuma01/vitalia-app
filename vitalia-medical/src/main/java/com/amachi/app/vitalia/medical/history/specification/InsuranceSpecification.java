package com.amachi.app.vitalia.medical.history.specification;

import com.amachi.app.core.common.specification.BaseSpecification;
import com.amachi.app.vitalia.medical.history.dto.search.InsuranceSearchDto;
import com.amachi.app.vitalia.medical.history.entity.Insurance;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.List;

/**
 * Motor de búsqueda avanzada para Seguros Médicos (SaaS Elite Tier).
 */
public class InsuranceSpecification extends BaseSpecification<Insurance> {

    private final InsuranceSearchDto criteria;

    public InsuranceSpecification(InsuranceSearchDto criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<Insurance> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        // 🛡️ Filtros Base Automáticos (TENANT_ID e IS_DELETED) via BaseSpecification helper
        predicates.addAll(buildBasePredicates(root, cb));

        if (criteria == null) return cb.and(predicates.toArray(new Predicate[0]));

        if (criteria.getMedicalHistoryId() != null) {
            predicates.add(cb.equal(root.get("medicalHistory").get("id"), criteria.getMedicalHistoryId()));
        }

        if (criteria.getProviderId() != null) {
            predicates.add(cb.equal(root.get("provider").get("id"), criteria.getProviderId()));
        }

        if (criteria.getPolicyNumber() != null && !criteria.getPolicyNumber().isBlank()) {
            predicates.add(cb.equal(root.get("policyNumber"), criteria.getPolicyNumber()));
        }

        if (criteria.getIsCurrent() != null) {
            predicates.add(cb.equal(root.get("isCurrent"), criteria.getIsCurrent()));
        }

        return cb.and(predicates.toArray(new Predicate[0]));
    }
}
