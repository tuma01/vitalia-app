package com.amachi.app.core.geography.departamento.specification;

import com.amachi.app.core.geography.departamento.dto.search.DepartamentoSearchDto;
import com.amachi.app.core.geography.departamento.entity.Departamento;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class DepartamentoSpecification implements Specification<Departamento> {

    private transient DepartamentoSearchDto criteria;

    @Override
    public Predicate toPredicate(Root<Departamento> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        // Filtro por ID del país
        if (criteria.getId() != null) {
            predicates.add(cb.equal(root.get("id"), criteria.getId()));
        }

        // Filtro por nombre del país
        if (criteria.getNombre() != null && !criteria.getNombre().isBlank()) {
            predicates.add(cb.like(cb.lower(root.get("nombre")), "%" + criteria.getNombre() + "%"));
        }

        // Retornar todas las condiciones combinadas
        return cb.and(predicates.toArray(new Predicate[0]));
    }
}