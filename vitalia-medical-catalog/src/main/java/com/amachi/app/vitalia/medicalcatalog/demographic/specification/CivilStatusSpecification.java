package com.amachi.app.vitalia.medicalcatalog.demographic.specification;

import com.amachi.app.vitalia.medicalcatalog.demographic.dto.search.CivilStatusSearchDto;
import com.amachi.app.vitalia.medicalcatalog.demographic.entity.CivilStatus;
import org.springframework.data.jpa.domain.Specification;

public class CivilStatusSpecification {
    public static Specification<CivilStatus> withFilters(CivilStatusSearchDto filters) {
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
