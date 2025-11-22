package com.amachi.app.vitalia.geography.provincia.specification;

import com.amachi.app.vitalia.geography.provincia.dto.search.ProvinciaSearchDto;
import com.amachi.app.vitalia.geography.provincia.entity.Provincia;
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

    private transient ProvinciaSearchDto provinciaSearchDto;

    @Override
    public Predicate toPredicate(Root<Provincia> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<>();

        if (provinciaSearchDto.getId() != null) {
            predicates.add(cb.equal(root.get("id"), provinciaSearchDto.getId()));
        }

        if (provinciaSearchDto.getNombre() != null) {
            predicates.add(cb.like(cb.lower(root.get("nombre")), provinciaSearchDto.getNombre()));
        }

        return cb.and(predicates.toArray(new Predicate[0]));
    }
}
