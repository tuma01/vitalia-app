package com.amachi.app.vitalia.management.superadmin.specification;

import com.amachi.app.vitalia.management.superadmin.dto.search.SuperAdminSearchDto;
import com.amachi.app.vitalia.management.superadmin.entity.SuperAdmin;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class SuperAdminSpecification implements Specification<SuperAdmin> {

    private transient SuperAdminSearchDto provinciaSearchDto;

    @Override
    public Predicate toPredicate(Root<SuperAdmin> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<>();

        if (provinciaSearchDto.getId() != null) {
            predicates.add(cb.equal(root.get("id"), provinciaSearchDto.getId()));
        }

        if (provinciaSearchDto.getGlobalAccess() != null) {
            predicates.add(cb.equal(root.get("globalAccess"), provinciaSearchDto.getGlobalAccess()));
        }

        if (provinciaSearchDto.getUserId() != null) {
            predicates.add(cb.equal(root.get("userId"), provinciaSearchDto.getUserId()));
        }

        return cb.and(predicates.toArray(new Predicate[0]));
    }
}
