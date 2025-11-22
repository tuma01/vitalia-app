package com.amachi.app.vitalia.geography.departamento.specification;

import com.amachi.app.vitalia.geography.departamento.dto.search.DepartamentoSearchDto;
import com.amachi.app.vitalia.geography.departamento.entity.Departamento;
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

    private transient DepartamentoSearchDto departamentoSearchDto;

    @Override
    public Predicate toPredicate(Root<Departamento> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        // Filtro por ID del país
        if (departamentoSearchDto.getId() != null) {
            predicates.add(cb.equal(root.get("id"), departamentoSearchDto.getId()));
        }

        // Filtro por nombre del país
        if (departamentoSearchDto.getNombre() != null) {
            predicates.add(cb.like(cb.lower(root.get("nombre")), "%" + departamentoSearchDto.getNombre() + "%"));
        }

        // Retornar todas las condiciones combinadas
        return cb.and(predicates.toArray(new Predicate[0]));
    }
}