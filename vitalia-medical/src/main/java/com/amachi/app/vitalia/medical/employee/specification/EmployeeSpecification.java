package com.amachi.app.vitalia.medical.employee.specification;

import com.amachi.app.core.common.specification.BaseSpecification;
import com.amachi.app.vitalia.medical.employee.dto.search.EmployeeSearchDto;
import com.amachi.app.vitalia.medical.employee.entity.Employee;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.List;

/**
 * Motor de búsqueda avanzada para Staff Administrativo (SaaS Elite Tier).
 * Garantiza el aislamiento multitenant y filtrado por estado laboral.
 */
public class EmployeeSpecification extends BaseSpecification<Employee> {

    private final EmployeeSearchDto criteria;

    public EmployeeSpecification(EmployeeSearchDto criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<Employee> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        // 🛡️ Filtros Base Automáticos (TENANT_ID e IS_DELETED) via BaseSpecification
        predicates.addAll(buildBasePredicates(root, cb));

        if (criteria == null) return cb.and(predicates.toArray(new Predicate[0]));

        if (criteria.getQuery() != null && !criteria.getQuery().isBlank()) {
            String q = "%" + criteria.getQuery().toLowerCase() + "%";
            predicates.add(cb.or(
                cb.like(cb.lower(root.get("firstName")), q),
                cb.like(cb.lower(root.get("lastName")), q)
            ));
        }

        if (criteria.getEmployeeCode() != null && !criteria.getEmployeeCode().isBlank()) {
            predicates.add(cb.equal(root.get("employeeCode"), criteria.getEmployeeCode()));
        }

        if (criteria.getNationalId() != null && !criteria.getNationalId().isBlank()) {
            predicates.add(cb.equal(root.get("nationalId"), criteria.getNationalId()));
        }

        if (criteria.getEmployeeType() != null) {
            predicates.add(cb.equal(root.get("employeeType"), criteria.getEmployeeType()));
        }

        if (criteria.getEmployeeStatus() != null) {
            predicates.add(cb.equal(root.get("employeeStatus"), criteria.getEmployeeStatus()));
        }

        if (criteria.getDepartmentUnitId() != null) {
            predicates.add(cb.equal(root.get("departmentUnit").get("id"), criteria.getDepartmentUnitId()));
        }

        return cb.and(predicates.toArray(new Predicate[0]));
    }
}
