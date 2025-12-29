package com.amachi.app.vitalia.tenant.specification;

import com.amachi.app.vitalia.tenant.dto.search.TenantSearchDto;
import com.amachi.app.vitalia.common.entity.Tenant;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class TenantSpecification implements Specification<Tenant> {
    private transient TenantSearchDto tenantSearchDto;

    @Override
    public Predicate toPredicate(Root<Tenant> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        if (tenantSearchDto.getId() != null) {
            predicates.add(cb.equal(root.get("id"), tenantSearchDto.getId()));
        }

        if (tenantSearchDto.getCode() != null) {
            predicates.add(cb.like(cb.lower(root.get("code")), tenantSearchDto.getCode()));
        }

        if (tenantSearchDto.getName() != null) {
            predicates.add(cb.like(cb.lower(root.get("name")), tenantSearchDto.getName()));
        }

        if (tenantSearchDto.getType() != null) {
            predicates.add(cb.like(root.get("type"), tenantSearchDto.getType()));
        }

        if (tenantSearchDto.getIsActive() != null) {
            predicates.add(cb.equal(root.get("isActive"), tenantSearchDto.getIsActive()));
        }
        return cb.and(predicates.toArray(new Predicate[0]));
    }
}
