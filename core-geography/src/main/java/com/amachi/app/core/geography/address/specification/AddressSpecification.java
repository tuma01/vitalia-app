package com.amachi.app.core.geography.address.specification;

import com.amachi.app.core.geography.address.dto.search.AddressSearchDto;
import com.amachi.app.core.geography.address.entity.Address;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class AddressSpecification implements Specification<Address> {

    private transient AddressSearchDto criteria;

    @Override
    public Predicate toPredicate(Root<Address> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        // Filtro por ID del país
        if (criteria.getId() != null) {
            predicates.add(cb.equal(root.get("id"), criteria.getId()));
        }

        if (criteria.getDireccion() != null && !criteria.getDireccion().isBlank()) {
            predicates.add(cb.like(cb.lower(root.get("direccion")), "%" + criteria.getDireccion() + "%"));
        }

        if (criteria.getCiudad() != null && !criteria.getCiudad().isBlank()) {
            predicates.add(cb.like(cb.lower(root.get("ciudad")), "%" + criteria.getCiudad() + "%"));
        }

        if (criteria.getCasillaPostal() != null && !criteria.getCasillaPostal().isBlank()) {
            predicates.add(cb.like(cb.lower(root.get("casillaPostal")), "%" + criteria.getCasillaPostal() + "%"));
        }

        // Retornar todas las condiciones combinadas
        return cb.and(predicates.toArray(new Predicate[0]));
    }
}