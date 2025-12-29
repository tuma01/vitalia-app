package com.amachi.app.vitalia.tenantconfig.specification;

import com.amachi.app.vitalia.tenantconfig.dto.search.TenantConfigSearchDto;
import com.amachi.app.vitalia.tenantconfig.entity.TenantConfig;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class TenantConfigSpecification implements Specification<TenantConfig> {
    private transient TenantConfigSearchDto tenantConfigSearchDto;

    @Override
    public Predicate toPredicate(Root<TenantConfig> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        if (tenantConfigSearchDto.getId() != null) {
            predicates.add(cb.equal(root.get("id"), tenantConfigSearchDto.getId()));
        }

        if (tenantConfigSearchDto.getFallbackHeader() != null) {
            predicates.add(cb.like(cb.lower(root.get("fallbackHeader")), tenantConfigSearchDto.getFallbackHeader()));
        }

        if (tenantConfigSearchDto.getDefaultDomain() != null) {
            predicates.add(cb.like(cb.lower(root.get("defaultDomain")), tenantConfigSearchDto.getDefaultDomain()));
        }

        if (tenantConfigSearchDto.getTenantId() != null) {
            predicates.add(cb.equal(root.get("tenantId"), tenantConfigSearchDto.getTenantId()));
        }
        return cb.and(predicates.toArray(new Predicate[0]));
    }
}
