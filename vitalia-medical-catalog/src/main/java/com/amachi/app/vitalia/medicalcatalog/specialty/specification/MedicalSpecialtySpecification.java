package com.amachi.app.vitalia.medicalcatalog.specialty.specification;

import com.amachi.app.core.common.specification.BaseSpecification;
import com.amachi.app.vitalia.medicalcatalog.specialty.dto.search.MedicalSpecialtySearchDto;
import com.amachi.app.vitalia.medicalcatalog.specialty.entity.MedicalSpecialty;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class MedicalSpecialtySpecification extends BaseSpecification<MedicalSpecialty> {
    private final MedicalSpecialtySearchDto criteria;

    @Override
    public Predicate toPredicate(@org.springframework.lang.NonNull Root<MedicalSpecialty> root, @org.springframework.lang.Nullable CriteriaQuery<?> query, @org.springframework.lang.NonNull CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>(buildBasePredicates(root, cb)); // ✅ Isolation

        // Filtrar por ID exacto
        if (criteria.getId() != null) {
            predicates.add(cb.equal(root.get("id"), criteria.getId()));
        }

        // Filtrar por código (LIKE)
        if (criteria.getCode() != null && !criteria.getCode().isBlank()) {
            predicates.add(cb.like(cb.lower(root.get("code")), 
                    "%" + criteria.getCode().toLowerCase() + "%"));
        }

        // Filtrar por nombre de la especialidad (LIKE)
        if (criteria.getName() != null && !criteria.getName().isBlank()) {
            predicates.add(cb.like(cb.lower(root.get("name")), 
                    "%" + criteria.getName().toLowerCase() + "%"));
        }

        // Filtrar por profesión objetivo (exacto)
        if (criteria.getTargetProfession() != null && !criteria.getTargetProfession().isBlank()) {
            predicates.add(cb.equal(root.get("targetProfession"), criteria.getTargetProfession()));
        }

        // Filtrar por estado activo/inactivo
        if (criteria.getActive() != null) {
            predicates.add(cb.equal(root.get("active"), criteria.getActive()));
        }

        return cb.and(predicates.toArray(new Predicate[0]));
    }
}
