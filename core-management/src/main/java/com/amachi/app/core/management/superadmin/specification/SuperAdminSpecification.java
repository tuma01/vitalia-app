package com.amachi.app.core.management.superadmin.specification;

import com.amachi.app.core.management.superadmin.dto.search.SuperAdminSearchDto;
import com.amachi.app.core.management.superadmin.entity.SuperAdmin;
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

    private transient SuperAdminSearchDto provinceSearchDto;

    @Override
    public Predicate toPredicate(Root<SuperAdmin> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<>();

        if (provinceSearchDto.getId() != null) {
            predicates.add(cb.equal(root.get("id"), provinceSearchDto.getId()));
        }

        if (provinceSearchDto.getGlobalAccess() != null) {
            predicates.add(cb.equal(root.get("globalAccess"), provinceSearchDto.getGlobalAccess()));
        }

        if (provinceSearchDto.getUserId() != null) {
            predicates.add(cb.equal(root.get("userId"), provinceSearchDto.getUserId()));
        }

        return cb.and(predicates.toArray(new Predicate[0]));
    }
}

