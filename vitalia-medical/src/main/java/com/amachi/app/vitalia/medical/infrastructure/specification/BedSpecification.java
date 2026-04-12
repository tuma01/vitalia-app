package com.amachi.app.vitalia.medical.infrastructure.specification;

import com.amachi.app.core.common.specification.BaseSpecification;
import com.amachi.app.vitalia.medical.infrastructure.dto.search.BedSearchDto;
import com.amachi.app.vitalia.medical.infrastructure.entity.Bed;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.List;

/**
 * Motor de búsqueda ÉLITE para infraestructura de camas.
 */
public class BedSpecification extends BaseSpecification<Bed> {

    private final BedSearchDto criteria;

    public BedSpecification(BedSearchDto criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<Bed> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        // 🛡️ Filtros Base Automáticos (TENANT_ID e IS_DELETED) via BaseSpecification
        predicates.addAll(buildBasePredicates(root, cb));

        if (criteria == null) return cb.and(predicates.toArray(new Predicate[0]));

        if (criteria.getQuery() != null && !criteria.getQuery().isBlank()) {
            String q = "%" + criteria.getQuery().toLowerCase() + "%";
            predicates.add(cb.or(
                cb.like(cb.lower(root.get("bedCode")), q),
                cb.like(cb.lower(root.get("description")), q)
            ));
        }

        if (criteria.getRoomId() != null) {
            predicates.add(cb.equal(root.get("room").get("id"), criteria.getRoomId()));
        }

        if (criteria.getBedCode() != null && !criteria.getBedCode().isBlank()) {
            predicates.add(cb.equal(root.get("bedCode"), criteria.getBedCode()));
        }

        if (criteria.getStatus() != null) {
            predicates.add(cb.equal(root.get("status"), criteria.getStatus()));
        }

        if (criteria.getIsOccupied() != null) {
            predicates.add(cb.equal(root.get("isOccupied"), criteria.getIsOccupied()));
        }

        if (criteria.getHospitalizationId() != null) {
            predicates.add(cb.equal(root.get("hospitalization").get("id"), criteria.getHospitalizationId()));
        }

        return cb.and(predicates.toArray(new Predicate[0]));
    }
}
