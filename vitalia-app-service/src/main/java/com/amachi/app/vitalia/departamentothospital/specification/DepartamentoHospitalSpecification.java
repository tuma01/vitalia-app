package com.amachi.app.vitalia.departamentothospital.specification;

import com.amachi.app.vitalia.departamentothospital.dto.search.DepartamentoHospitalSearchDto;
import com.amachi.app.vitalia.departamentothospital.entity.DepartamentoHospital;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class DepartamentoHospitalSpecification implements Specification<DepartamentoHospital> {

    private transient DepartamentoHospitalSearchDto departamentoHospitalSearch;

    @Override
    public Predicate toPredicate(Root<DepartamentoHospital> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        List<Predicate> predicates = new ArrayList<>();

        if (departamentoHospitalSearch.getId() != null) {
            predicates.add(criteriaBuilder.equal(root.get("id"), departamentoHospitalSearch.getId()));
        }

        if (departamentoHospitalSearch.getName() != null) {
            predicates.add(criteriaBuilder.equal(root.get("name"), departamentoHospitalSearch.getName()));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
