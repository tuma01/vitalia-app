package com.amachi.app.vitalia.medicalcatalog.allergy.specification;

import com.amachi.app.vitalia.medicalcatalog.allergy.dto.search.AllergySearchDto;
import com.amachi.app.vitalia.medicalcatalog.allergy.entity.Allergy;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class AllergySpecification implements Specification<Allergy> {
    private transient AllergySearchDto criteria;

    @Override
    public Predicate toPredicate(@NonNull Root<Allergy> root, @Nullable CriteriaQuery<?> query,
            @NonNull CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();
        if (criteria.getId() != null)
            predicates.add(cb.equal(root.get("id"), criteria.getId()));
        if (criteria.getCode() != null && !criteria.getCode().isBlank())
            predicates.add(cb.like(cb.lower(root.get("code")), "%" + criteria.getCode().toLowerCase() + "%"));
        if (criteria.getName() != null && !criteria.getName().isBlank())
            predicates.add(cb.like(cb.lower(root.get("name")), "%" + criteria.getName().toLowerCase() + "%"));
        if (criteria.getType() != null && !criteria.getType().isBlank())
            predicates.add(cb.equal(root.get("type"), criteria.getType()));
        if (criteria.getActive() != null)
            predicates.add(cb.equal(root.get("active"), criteria.getActive()));
        return cb.and(predicates.toArray(new Predicate[0]));
    }
}
