package com.amachi.app.vitalia.geography.address.specification;

import com.amachi.app.vitalia.geography.address.dto.search.AddressSearchDto;
import com.amachi.app.vitalia.geography.address.entity.Address;
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

    private transient AddressSearchDto addressSearchDto;

    @Override
    public Predicate toPredicate(Root<Address> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        // Filtro por ID del país
        if (addressSearchDto.getId() != null) {
            predicates.add(cb.equal(root.get("id"), addressSearchDto.getId()));
        }

        // Filtro por nombre del país
        if (addressSearchDto.getDireccion() != null) {
            predicates.add(cb.like(cb.lower(root.get("direccion")), "%" + addressSearchDto.getDireccion() + "%"));
        }

        if (addressSearchDto.getCiudad() != null) {
            predicates.add(cb.like(cb.lower(root.get("ciudad")), "%" + addressSearchDto.getCiudad() + "%"));
        }

        if (addressSearchDto.getCasillaPostal() != null) {
            predicates.add(cb.like(cb.lower(root.get("casillaPostal")), "%" + addressSearchDto.getCasillaPostal() + "%"));
        }

        // Retornar todas las condiciones combinadas
        return cb.and(predicates.toArray(new Predicate[0]));
    }
}