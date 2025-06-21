package com.amachi.app.vitalia.nurse.specification;

import com.amachi.app.vitalia.nurse.dto.search.NurseSearchDto;
import com.amachi.app.vitalia.nurse.entity.Nurse;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class NurseSpecification implements Specification<Nurse> {

    private transient NurseSearchDto nurseSearchDto;

    @Override
    public Predicate toPredicate(Root<Nurse> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        List<Predicate> predicates = new ArrayList<>();

        if (nurseSearchDto.getIdCard() != null) {
            predicates.add(criteriaBuilder.equal(root.get("idCard"), nurseSearchDto.getIdCard()));
        }
        if (nurseSearchDto.getNombre() != null) {
            predicates.add(criteriaBuilder.equal(root.get("nombre"), nurseSearchDto.getNombre()));
        }

        if (nurseSearchDto.getApellidoPaterno() != null) {
            predicates.add(criteriaBuilder.equal(root.get("apellidoPaterno"), nurseSearchDto.getApellidoPaterno()));
        }

        if (nurseSearchDto.getApellidoMaterno() != null) {
            predicates.add(criteriaBuilder.equal(root.get("apellidoMaterno"), nurseSearchDto.getApellidoMaterno()));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
