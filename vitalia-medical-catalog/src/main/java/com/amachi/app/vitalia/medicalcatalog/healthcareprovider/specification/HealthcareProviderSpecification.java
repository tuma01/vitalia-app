package com.amachi.app.vitalia.medicalcatalog.healthcareprovider.specification;

import com.amachi.app.vitalia.medicalcatalog.healthcareprovider.dto.search.HealthcareProviderSearchDto;
import com.amachi.app.vitalia.medicalcatalog.healthcareprovider.entity.HealthcareProvider;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class HealthcareProviderSpecification implements Specification<HealthcareProvider> {

    private transient HealthcareProviderSearchDto criteria;

    @Override
    public Predicate toPredicate(Root<HealthcareProvider> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        // Filtrar por ID
        if (criteria.getId() != null) {
            predicates.add(cb.equal(root.get("id"), criteria.getId()));
        }

        // Filtrar por código (búsqueda parcial, insensible a mayúsculas)
        if (criteria.getCode() != null && !criteria.getCode().isBlank()) {
            predicates.add(cb.like(cb.lower(root.get("code")), "%" + criteria.getCode().toLowerCase() + "%"));
        }

        // Filtrar por nombre (búsqueda parcial, insensible a mayúsculas)
        if (criteria.getName() != null && !criteria.getName().isBlank()) {
            predicates.add(cb.like(cb.lower(root.get("name")), "%" + criteria.getName().toLowerCase() + "%"));
        }

        // Filtrar por NIT/RUT exacto
        if (criteria.getTaxId() != null && !criteria.getTaxId().isBlank()) {
            predicates.add(cb.equal(root.get("taxId"), criteria.getTaxId()));
        }

        // Filtrar por estado activo/inactivo
        if (criteria.getActive() != null) {
            predicates.add(cb.equal(root.get("active"), criteria.getActive()));
        }

        return cb.and(predicates.toArray(new Predicate[0]));
    }
}
