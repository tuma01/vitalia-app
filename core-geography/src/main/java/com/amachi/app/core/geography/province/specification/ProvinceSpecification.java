package com.amachi.app.core.geography.province.specification;

import com.amachi.app.core.geography.province.dto.search.ProvinceSearchDto;
import com.amachi.app.core.geography.province.entity.Province;
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
public class ProvinceSpecification implements Specification<Province> {

    private final ProvinceSearchDto criteria;

    @Override
    public Predicate toPredicate(@NonNull Root<Province> root, @NonNull CriteriaQuery<?> query, @NonNull CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        if (criteria.getName() != null && !criteria.getName().isEmpty()) {
            predicates.add(cb.like(cb.upper(root.get("name")), "%" + criteria.getName().toUpperCase() + "%"));
        }

        if (criteria.getId() != null) {
            predicates.add(cb.equal(root.get("id"), criteria.getId()));
        }

        if (criteria.getStateId() != null) {
            predicates.add(cb.equal(root.get("state").get("id"), criteria.getStateId()));
        }

        return cb.and(predicates.toArray(new Predicate[0]));
    }
}
