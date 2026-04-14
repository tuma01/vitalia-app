package com.amachi.app.core.geography.state.specification;

import com.amachi.app.core.geography.state.dto.search.StateSearchDto;
import com.amachi.app.core.geography.state.entity.State;
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
public class StateSpecification implements Specification<State> {

    private final StateSearchDto criteria;

    @Override
    public Predicate toPredicate(@NonNull Root<State> root, @NonNull CriteriaQuery<?> query, @NonNull CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        if (criteria.getName() != null && !criteria.getName().isEmpty()) {
            predicates.add(cb.like(cb.upper(root.get("name")), "%" + criteria.getName().toUpperCase() + "%"));
        }

        if (criteria.getId() != null) {
            predicates.add(cb.equal(root.get("id"), criteria.getId()));
        }

        if (criteria.getCountryId() != null) {
            predicates.add(cb.equal(root.get("country").get("id"), criteria.getCountryId()));
        }

        return cb.and(predicates.toArray(new Predicate[0]));
    }
}
