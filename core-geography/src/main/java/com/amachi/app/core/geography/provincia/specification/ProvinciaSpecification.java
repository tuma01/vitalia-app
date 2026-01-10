package com.amachi.app.core.geography.provincia.specification;

import com.amachi.app.core.geography.provincia.dto.search.ProvinciaSearchDto;
import com.amachi.app.core.geography.provincia.entity.Provincia;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class ProvinciaSpecification implements Specification<Provincia> {

    private transient ProvinciaSearchDto criteria;

    @Override
    public Predicate toPredicate(Root<Provincia> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<>();

        if (criteria.getId() != null) {
            predicates.add(cb.equal(root.get("id"), criteria.getId()));
        }

        if (criteria.getNombre() != null && !criteria.getNombre().isBlank()) {
            predicates.add(cb.like(cb.lower(root.get("nombre")), "%" + criteria.getNombre() + "%"));
        }

        return cb.and(predicates.toArray(new Predicate[0]));
    }
}
