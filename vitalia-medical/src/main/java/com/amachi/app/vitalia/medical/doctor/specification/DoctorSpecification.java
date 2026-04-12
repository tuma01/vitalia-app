package com.amachi.app.vitalia.medical.doctor.specification;

import com.amachi.app.core.common.specification.BaseSpecification;
import com.amachi.app.vitalia.medical.doctor.dto.search.DoctorSearchDto;
import com.amachi.app.vitalia.medical.doctor.entity.Doctor;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.List;

/**
 * Especificación de búsqueda para Doctor (SaaS Elite Tier).
 * Garantiza aislamiento de inquilino y filtrado de borrados lógicos.
 */
public class DoctorSpecification extends BaseSpecification<Doctor> {

    private final DoctorSearchDto criteria;

    public DoctorSpecification(DoctorSearchDto criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<Doctor> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        // 🛡️ Filtros Base Automáticos (TENANT_ID e IS_DELETED) via BaseSpecification
        predicates.addAll(buildBasePredicates(root, cb));

        if (criteria == null) return cb.and(predicates.toArray(new Predicate[0]));

        // Filtro de búsqueda textual (Query)
        if (criteria.getQuery() != null && !criteria.getQuery().isBlank()) {
            String q = "%" + criteria.getQuery().toLowerCase() + "%";
            predicates.add(cb.or(
                cb.like(cb.lower(root.get("firstName")), q),
                cb.like(cb.lower(root.get("lastName")), q),
                cb.like(cb.lower(root.get("licenseNumber")), q)
            ));
        }

        // Filtro por Especialidad
        if (criteria.getSpecialty() != null && !criteria.getSpecialty().isBlank()) {
            predicates.add(cb.like(cb.lower(root.get("specialtiesSummary")), "%" + criteria.getSpecialty().toLowerCase() + "%"));
        }

        // Filtro por Documento (nationalId)
        if (criteria.getNationalId() != null && !criteria.getNationalId().isBlank()) {
            predicates.add(cb.equal(root.get("nationalId"), criteria.getNationalId()));
        }

        return cb.and(predicates.toArray(new Predicate[0]));
    }
}
