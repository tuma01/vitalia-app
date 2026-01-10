package com.amachi.app.vitalia.management.tenantadmin.specification;

import com.amachi.app.vitalia.management.tenantadmin.dto.search.TenantAdminSearchDto;
import com.amachi.app.vitalia.management.tenantadmin.entity.TenantAdmin;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class TenantAdminSpecification implements Specification<TenantAdmin> {
    private transient TenantAdminSearchDto criteria;

    @Override
    public Predicate toPredicate(Root<TenantAdmin> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        if (criteria.getId() != null) {
            predicates.add(cb.equal(root.get("id"), criteria.getId()));
        }

        if (criteria.getTenantCode() != null && !criteria.getTenantCode().isBlank()) {
            predicates.add(cb.like(cb.lower(root.get("tenantCode")), "%" + criteria.getTenantCode().toLowerCase() + "%"));
        }

        if (criteria.getEmail() != null && !criteria.getEmail().isBlank()) {
            predicates.add(cb.like(cb.lower(root.get("email")), "%" + criteria.getEmail().toLowerCase() + "%"));
        }

        if (criteria.getEnabled() != null) {
            predicates.add(cb.equal(root.get("enabled"), criteria.getEnabled()));
        }

        return cb.and(predicates.toArray(new Predicate[0]));
    }
}
