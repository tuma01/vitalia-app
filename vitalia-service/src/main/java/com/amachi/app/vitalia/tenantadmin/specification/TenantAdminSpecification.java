package com.amachi.app.vitalia.tenantadmin.specification;

import com.amachi.app.vitalia.tenantadmin.dto.search.TenantAdminSearchDto;
import com.amachi.app.vitalia.tenantadmin.entity.TenantAdmin;
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
    private transient TenantAdminSearchDto tenantAdminSearchDto;

    @Override
    public Predicate toPredicate(Root<TenantAdmin> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        if (tenantAdminSearchDto.getId() != null) {
            predicates.add(cb.equal(root.get("id"), tenantAdminSearchDto.getId()));
        }

        if (tenantAdminSearchDto.getTenantCode() != null) {
            predicates.add(cb.like(cb.lower(root.get("tenantCode")), tenantAdminSearchDto.getTenantCode()));
        }

        if (tenantAdminSearchDto.getEmail() != null) {
            predicates.add(cb.like(cb.lower(root.get("email")), tenantAdminSearchDto.getEmail()));
        }

        if (tenantAdminSearchDto.getEnabled() != null) {
            predicates.add(cb.equal(root.get("enabled"), tenantAdminSearchDto.getEnabled()));
        }

        return cb.and(predicates.toArray(new Predicate[0]));
    }
}
