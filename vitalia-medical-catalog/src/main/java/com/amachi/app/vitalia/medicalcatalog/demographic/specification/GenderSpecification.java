package com.amachi.app.vitalia.medicalcatalog.demographic.specification;

import com.amachi.app.vitalia.medicalcatalog.demographic.dto.search.GenderSearchDto;
import com.amachi.app.vitalia.medicalcatalog.demographic.entity.Gender;
import org.springframework.data.jpa.domain.Specification;

public class GenderSpecification {
    public static Specification<Gender> withFilters(GenderSearchDto filters) {
        return (root, query, cb) -> {
            var predicates = cb.conjunction();
            if (filters.getCode() != null) {
                predicates = cb.and(predicates, cb.equal(root.get("code"), filters.getCode()));
            }
            if (filters.getName() != null) {
                predicates = cb.and(predicates,
                        cb.like(cb.lower(root.get("name")), "%" + filters.getName().toLowerCase() + "%"));
            }
            if (filters.getActive() != null) {
                predicates = cb.and(predicates, cb.equal(root.get("active"), filters.getActive()));
            }
            return predicates;
        };
    }
}
