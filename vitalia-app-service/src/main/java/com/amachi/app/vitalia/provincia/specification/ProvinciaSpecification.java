package com.amachi.app.vitalia.provincia.specification;

import com.amachi.app.vitalia.provincia.dto.search.ProvinciaSearchDto;
import com.amachi.app.vitalia.provincia.entity.Provincia;
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
    public Predicate toPredicate(Root<Provincia> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        List<Predicate> predicates = new ArrayList<>();

        if (provinciaSearchDto.getId() != null) {
            predicates.add(criteriaBuilder.equal(root.get("id"), provinciaSearchDto.getId()));
        }

        if (provinciaSearchDto.getNombre() != null) {
            predicates.add(criteriaBuilder.equal(root.get("nombre"), provinciaSearchDto.getNombre()));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
