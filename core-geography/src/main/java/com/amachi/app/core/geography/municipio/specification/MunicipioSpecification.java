package com.amachi.app.core.geography.municipio.specification;

import com.amachi.app.core.geography.municipio.dto.search.MunicipioSearchDto;
import com.amachi.app.core.geography.municipio.entity.Municipio;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class MunicipioSpecification implements Specification<Municipio> {

    private transient MunicipioSearchDto criteria;

    @Override
    public Predicate toPredicate(Root<Municipio> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<>();

        if (criteria.getId() != null) {
            predicates.add(cb.equal(root.get("id"), criteria.getId()));
        }

        if (criteria.getNombre() != null && !criteria.getNombre().isBlank()) {
            predicates.add(cb.like(cb.lower(root.get("nombre ")), "%" + criteria.getNombre() + "%"));
        }

        if (criteria.getCodigoMunicipio() != null) {
            predicates.add(cb.equal(root.get("codigoMunicipio"), criteria.getCodigoMunicipio()));
        }

        return cb.and(predicates.toArray(new Predicate[0]));
    }
}
