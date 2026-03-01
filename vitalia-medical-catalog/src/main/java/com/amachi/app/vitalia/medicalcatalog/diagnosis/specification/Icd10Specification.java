package com.amachi.app.vitalia.medicalcatalog.diagnosis.specification;

import com.amachi.app.vitalia.medicalcatalog.diagnosis.dto.search.Icd10SearchDto;
import com.amachi.app.vitalia.medicalcatalog.diagnosis.entity.Icd10;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class Icd10Specification implements Specification<Icd10> {
    private transient Icd10SearchDto criteria;

    @Override
    public Predicate toPredicate(Root<Icd10> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        // Filtrar por ID exacto
        if (criteria.getId() != null) {
            predicates.add(cb.equal(root.get("id"), criteria.getId()));
        }

        // Filtrar por código CIE-10 (LIKE)
        if (criteria.getCode() != null && !criteria.getCode().isBlank()) {
            predicates.add(cb.like(cb.lower(root.get("code")), "%" + criteria.getCode().toLowerCase() + "%"));
        }

        // Filtrar por descripción del diagnóstico (LIKE)
        if (criteria.getDescription() != null && !criteria.getDescription().isBlank()) {
            predicates.add(
                    cb.like(cb.lower(root.get("description")), "%" + criteria.getDescription().toLowerCase() + "%"));
        }

        // Filtrar por estado activo/inactivo
        if (criteria.getActive() != null) {
            predicates.add(cb.equal(root.get("active"), criteria.getActive()));
        }

        return cb.and(predicates.toArray(new Predicate[0]));
    }
}
