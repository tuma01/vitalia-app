package com.amachi.app.vitalia.medical.nurse.specification;

import com.amachi.app.core.common.specification.BaseSpecification;
import com.amachi.app.vitalia.medical.nurse.dto.search.NurseSearchDto;
import com.amachi.app.vitalia.medical.nurse.entity.Nurse;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.List;

/**
 * Motor de búsqueda ÉLITE para personal de enfermería staff.
 */
public class NurseSpecification extends BaseSpecification<Nurse> {

    private final NurseSearchDto criteria;

    public NurseSpecification(NurseSearchDto criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<Nurse> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        // 🛡️ Filtros Base Automáticos (TENANT_ID e IS_DELETED) via BaseSpecification
        predicates.addAll(buildBasePredicates(root, cb));

        if (criteria == null) return cb.and(predicates.toArray(new Predicate[0]));

        // Filtro textual
        if (criteria.getQuery() != null && !criteria.getQuery().isBlank()) {
            String q = "%" + criteria.getQuery().toLowerCase() + "%";
            predicates.add(cb.or(
                cb.like(cb.lower(root.get("firstName")), q),
                cb.like(cb.lower(root.get("lastName")), q)
            ));
        }

        if (criteria.getLicenseNumber() != null && !criteria.getLicenseNumber().isBlank()) {
            predicates.add(cb.equal(root.get("licenseNumber"), criteria.getLicenseNumber()));
        }

        if (criteria.getDepartmentUnitId() != null) {
            predicates.add(cb.equal(root.get("departmentUnit").get("id"), criteria.getDepartmentUnitId()));
        }

        if (criteria.getWorkShift() != null) {
            predicates.add(cb.equal(root.get("workShift"), criteria.getWorkShift()));
        }

        if (criteria.getActive() != null) {
            predicates.add(cb.equal(root.get("active"), criteria.getActive()));
        }

        return cb.and(predicates.toArray(new Predicate[0]));
    }
}
