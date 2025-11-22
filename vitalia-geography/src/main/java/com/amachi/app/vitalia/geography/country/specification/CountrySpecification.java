package com.amachi.app.vitalia.geography.country.specification;

import com.amachi.app.vitalia.geography.country.dto.search.CountrySearchDto;
import com.amachi.app.vitalia.geography.country.entity.Country;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class CountrySpecification implements Specification<Country> {

    private transient CountrySearchDto countrySearchDto;

    @Override
    public Predicate toPredicate(Root<Country> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        // Filtro por ID del país
        if (countrySearchDto.getId() != null) {
            predicates.add(cb.equal(root.get("id"), countrySearchDto.getId()));
        }

        // Filtro por nombre del país
        if (countrySearchDto.getName() != null) {
            predicates.add(cb.like(cb.lower(root.get("name")), "%" + countrySearchDto.getName() + "%"));
        }

        // Filtro por código ISO del país
        if (countrySearchDto.getIso() != null) {
            predicates.add(cb.equal(root.get("iso"), countrySearchDto.getIso()));
        }

        // Retornar todas las condiciones combinadas
        return cb.and(predicates.toArray(new Predicate[0]));
    }
}