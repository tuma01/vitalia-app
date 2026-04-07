package com.amachi.app.core.management.tenantadmin.specification;

import com.amachi.app.core.common.specification.BaseSpecification;
import com.amachi.app.core.management.tenantadmin.dto.search.TenantAdminSearchDto;
import com.amachi.app.core.management.tenantadmin.entity.TenantAdmin;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class TenantAdminSpecification extends BaseSpecification<TenantAdmin> {
    private final TenantAdminSearchDto criteria;

    @Override
    public Predicate toPredicate(Root<TenantAdmin> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>(buildBasePredicates(root, cb)); // ✅ Multi-tenant + Soft delete

        if (criteria.getId() != null) {
            predicates.add(cb.equal(root.get("id"), criteria.getId()));
        }

        if (criteria.getEmail() != null && !criteria.getEmail().isBlank()) {
            predicates.add(cb.like(cb.lower(root.get("email")), 
                    "%" + criteria.getEmail().toLowerCase() + "%"));
        }

        if (criteria.getFirstName() != null && !criteria.getFirstName().isBlank()) {
            predicates.add(cb.like(cb.lower(root.get("firstName")), 
                    "%" + criteria.getFirstName().toLowerCase() + "%"));
        }

        if (criteria.getLastName() != null && !criteria.getLastName().isBlank()) {
            predicates.add(cb.like(cb.lower(root.get("lastName")), 
                    "%" + criteria.getLastName().toLowerCase() + "%"));
        }

        return cb.and(predicates.toArray(new Predicate[0]));
    }
}
