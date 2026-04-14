package com.amachi.app.core.geography.municipality.specification;

import com.amachi.app.core.geography.municipality.dto.search.MunicipalitySearchDto;
import com.amachi.app.core.geography.municipality.entity.Municipality;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class MunicipalitySpecification implements Specification<Municipality> {

    private final MunicipalitySearchDto criteria;

    @Override
    public Predicate toPredicate(@NonNull Root<Municipality> root, @NonNull CriteriaQuery<?> query, @NonNull CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        if (criteria.getName() != null && !criteria.getName().isEmpty()) {
            predicates.add(cb.like(cb.upper(root.get("name")), "%" + criteria.getName().toUpperCase() + "%"));
        }

        if (criteria.getId() != null) {
            predicates.add(cb.equal(root.get("id"), criteria.getId()));
        }

        if (criteria.getProvinceId() != null) {
            predicates.add(cb.equal(root.get("province").get("id"), criteria.getProvinceId()));
        }

        if (criteria.getStateId() != null) {
            predicates.add(cb.equal(root.get("province").get("state").get("id"), criteria.getStateId()));
        }

        if (criteria.getCountryId() != null) {
            predicates.add(cb.equal(root.get("province").get("state").get("country").get("id"), criteria.getCountryId()));
        }

        return cb.and(predicates.toArray(new Predicate[0]));
    }
}
