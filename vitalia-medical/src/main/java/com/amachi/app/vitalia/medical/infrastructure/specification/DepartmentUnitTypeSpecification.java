package com.amachi.app.vitalia.medical.infrastructure.specification;

import com.amachi.app.core.common.specification.BaseSpecification;
import com.amachi.app.vitalia.medical.infrastructure.dto.search.DepartmentUnitTypeSearchDto;
import com.amachi.app.vitalia.medical.infrastructure.entity.DepartmentUnitType;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.List;

/**
 * Especificación para búsquedas dinámicas de tipos de unidad (SaaS Elite Tier).
 * Garantiza el aislamiento por inquilino y filtrado de borrados lógicos.
 */
public class DepartmentUnitTypeSpecification extends BaseSpecification<DepartmentUnitType> {

    private final DepartmentUnitTypeSearchDto criteria;

    public DepartmentUnitTypeSpecification(DepartmentUnitTypeSearchDto criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<DepartmentUnitType> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        // 🛡️ Filtros Base Automáticos (TENANT_ID e IS_DELETED) via BaseSpecification
        predicates.addAll(buildBasePredicates(root, cb));

        if (criteria == null) return cb.and(predicates.toArray(new Predicate[0]));

        if (criteria.getId() != null) {
            predicates.add(cb.equal(root.get("id"), criteria.getId()));
        }

        if (criteria.getQuery() != null && !criteria.getQuery().isBlank()) {
            predicates.add(cb.like(cb.lower(root.get("name")), "%" + criteria.getQuery().toLowerCase() + "%"));
        }

        return cb.and(predicates.toArray(new Predicate[0]));
    }
}
