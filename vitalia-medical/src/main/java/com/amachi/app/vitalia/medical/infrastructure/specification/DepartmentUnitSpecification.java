package com.amachi.app.vitalia.medical.infrastructure.specification;

import com.amachi.app.core.common.specification.BaseSpecification;
import com.amachi.app.vitalia.medical.infrastructure.dto.search.DepartmentUnitSearchDto;
import com.amachi.app.vitalia.medical.infrastructure.entity.DepartmentUnit;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.List;

/**
 * Especificación para búsquedas dinámicas de Unidades Departamentales (SaaS Elite Tier).
 * Garantiza el aislamiento por inquilino y filtrado de borrados lógicos.
 */
public class DepartmentUnitSpecification extends BaseSpecification<DepartmentUnit> {

    private final DepartmentUnitSearchDto criteria;

    public DepartmentUnitSpecification(DepartmentUnitSearchDto criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<DepartmentUnit> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        // 🛡️ Filtros Base Automáticos (TENANT_ID e IS_DELETED) via BaseSpecification helper
        predicates.addAll(buildBasePredicates(root, cb));

        if (criteria == null) return cb.and(predicates.toArray(new Predicate[0]));

        if (criteria.getQuery() != null && !criteria.getQuery().isBlank()) {
            String q = "%" + criteria.getQuery().toLowerCase() + "%";
            predicates.add(cb.or(
                cb.like(cb.lower(root.get("name")), q),
                cb.like(cb.lower(root.get("code")), q)
            ));
        }

        if (criteria.getUnitTypeId() != null) {
            predicates.add(cb.equal(root.get("unitType").get("id"), criteria.getUnitTypeId()));
        }

        if (criteria.getParentUnitId() != null) {
            predicates.add(cb.equal(root.get("parentUnit").get("id"), criteria.getParentUnitId()));
        }

        if (criteria.getActive() != null) {
            predicates.add(cb.equal(root.get("active"), criteria.getActive()));
        }

        return cb.and(predicates.toArray(new Predicate[0]));
    }
}
