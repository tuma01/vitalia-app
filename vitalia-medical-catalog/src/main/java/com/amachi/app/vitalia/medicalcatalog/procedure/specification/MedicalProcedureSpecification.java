package com.amachi.app.vitalia.medicalcatalog.procedure.specification;

import com.amachi.app.vitalia.medicalcatalog.procedure.dto.search.MedicalProcedureSearchDto;
import com.amachi.app.vitalia.medicalcatalog.procedure.entity.MedicalProcedure;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class MedicalProcedureSpecification implements Specification<MedicalProcedure> {
    private transient MedicalProcedureSearchDto criteria;

    @Override
    public Predicate toPredicate(Root<MedicalProcedure> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        // Filtrar por ID exacto
        if (criteria.getId() != null) {
            predicates.add(cb.equal(root.get("id"), criteria.getId()));
        }

        // Filtrar por código (LIKE)
        if (criteria.getCode() != null && !criteria.getCode().isBlank()) {
            predicates.add(cb.like(cb.lower(root.get("code")), "%" + criteria.getCode().toLowerCase() + "%"));
        }

        // Filtrar por nombre del procedimiento (LIKE)
        if (criteria.getName() != null && !criteria.getName().isBlank()) {
            predicates.add(cb.like(cb.lower(root.get("name")), "%" + criteria.getName().toLowerCase() + "%"));
        }

        // Filtrar por tipo de procedimiento (exacto)
        if (criteria.getType() != null && !criteria.getType().isBlank()) {
            predicates.add(cb.equal(root.get("type"), criteria.getType()));
        }

        // Filtrar por estado activo/inactivo
        if (criteria.getActive() != null) {
            predicates.add(cb.equal(root.get("active"), criteria.getActive()));
        }

        return cb.and(predicates.toArray(new Predicate[0]));
    }
}
