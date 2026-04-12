package com.amachi.app.vitalia.medical.history.specification;

import com.amachi.app.core.common.specification.BaseSpecification;
import com.amachi.app.vitalia.medical.history.dto.search.ObservationSearchDto;
import com.amachi.app.vitalia.medical.history.entity.Observation;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.List;

/**
 * Motor de búsqueda avanzada para Observaciones Clínicas (SaaS Elite Tier).
 */
public class ObservationSpecification extends BaseSpecification<Observation> {

    private final ObservationSearchDto criteria;

    public ObservationSpecification(ObservationSearchDto criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<Observation> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        // 🛡️ Filtros Base Automáticos (TENANT_ID e IS_DELETED) via BaseSpecification helper
        predicates.addAll(buildBasePredicates(root, cb));

        if (criteria == null) return cb.and(predicates.toArray(new Predicate[0]));

        if (criteria.getPatientId() != null) {
            predicates.add(cb.equal(root.get("patient").get("id"), criteria.getPatientId()));
        }

        if (criteria.getEncounterId() != null) {
            predicates.add(cb.equal(root.get("encounter").get("id"), criteria.getEncounterId()));
        }

        if (criteria.getPractitionerId() != null) {
            predicates.add(cb.equal(root.get("practitioner").get("id"), criteria.getPractitionerId()));
        }

        if (criteria.getQuery() != null && !criteria.getQuery().isBlank()) {
            String q = "%" + criteria.getQuery().toLowerCase() + "%";
            predicates.add(cb.or(
                cb.like(cb.lower(root.get("name")), q),
                cb.like(cb.lower(root.get("notes")), q)
            ));
        }

        if (criteria.getCode() != null && !criteria.getCode().isBlank()) {
            predicates.add(cb.equal(root.get("code"), criteria.getCode()));
        }

        if (criteria.getStatus() != null) {
            predicates.add(cb.equal(root.get("status"), criteria.getStatus()));
        }

        if (criteria.getInterpretation() != null && !criteria.getInterpretation().isBlank()) {
            predicates.add(cb.equal(root.get("interpretation"), criteria.getInterpretation()));
        }

        if (criteria.getEffectiveDateFrom() != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("effectiveDateTime"), criteria.getEffectiveDateFrom()));
        }

        if (criteria.getEffectiveDateTo() != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("effectiveDateTime"), criteria.getEffectiveDateTo()));
        }

        return cb.and(predicates.toArray(new Predicate[0]));
    }
}
