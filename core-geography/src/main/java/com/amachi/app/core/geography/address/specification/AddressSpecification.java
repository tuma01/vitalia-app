package com.amachi.app.core.geography.address.specification;

import com.amachi.app.core.geography.address.dto.search.AddressSearchDto;
import com.amachi.app.core.geography.address.entity.Address;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class AddressSpecification implements Specification<Address> {

    private final AddressSearchDto criteria;

    @Override
    public Predicate toPredicate(@NonNull Root<Address> root, @NonNull CriteriaQuery<?> query, @NonNull CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        if (criteria.getAddressLine() != null && !criteria.getAddressLine().isEmpty()) {
            predicates.add(cb.like(cb.upper(root.get("streetName")), "%" + criteria.getAddressLine().toUpperCase() + "%"));
        }

        if (criteria.getCity() != null && !criteria.getCity().isEmpty()) {
            predicates.add(cb.like(cb.upper(root.get("city")), "%" + criteria.getCity().toUpperCase() + "%"));
        }

        if (criteria.getZipCode() != null && !criteria.getZipCode().isEmpty()) {
            predicates.add(cb.equal(root.get("zipCode"), criteria.getZipCode()));
        }

        if (criteria.getCountryId() != null) {
            predicates.add(cb.equal(root.get("country").get("id"), criteria.getCountryId()));
        }

        if (criteria.getStateId() != null) {
            predicates.add(cb.equal(root.get("state").get("id"), criteria.getStateId()));
        }

        if (criteria.getProvinceId() != null) {
            predicates.add(cb.equal(root.get("province").get("id"), criteria.getProvinceId()));
        }

        if (criteria.getMunicipalityId() != null) {
            predicates.add(cb.equal(root.get("municipality").get("id"), criteria.getMunicipalityId()));
        }

        return cb.and(predicates.toArray(new Predicate[0]));
    }
}
