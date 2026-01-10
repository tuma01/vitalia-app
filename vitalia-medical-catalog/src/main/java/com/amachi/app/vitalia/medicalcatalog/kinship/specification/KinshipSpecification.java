package com.amachi.app.vitalia.medicalcatalog.kinship.specification;

import com.amachi.app.vitalia.medicalcatalog.kinship.dto.search.KinshipSearchDto;
import com.amachi.app.vitalia.medicalcatalog.kinship.entity.Kinship;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class KinshipSpecification implements Specification<Kinship> {
    private transient KinshipSearchDto criteria;

    @Override
    public Predicate toPredicate(Root<Kinship> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();
        if (criteria.getId() != null) predicates.add(cb.equal(root.get("id"), criteria.getId()));
        if (criteria.getCode() != null && !criteria.getCode().isBlank()) predicates.add(cb.like(cb.lower(root.get("code")), "%" + criteria.getCode().toLowerCase() + "%"));
        if (criteria.getName() != null && !criteria.getName().isBlank()) predicates.add(cb.like(cb.lower(root.get("name")), "%" + criteria.getName().toLowerCase() + "%"));
        if (criteria.getActive() != null) predicates.add(cb.equal(root.get("active"), criteria.getActive()));
        return cb.and(predicates.toArray(new Predicate[0]));
    }
}
