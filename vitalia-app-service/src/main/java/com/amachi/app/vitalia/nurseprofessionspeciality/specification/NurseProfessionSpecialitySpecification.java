package com.amachi.app.vitalia.nurseprofessionspeciality.specification;

import com.amachi.app.vitalia.nurseprofessionspeciality.dto.search.NurseProfessionSpecialitySearchDto;
import com.amachi.app.vitalia.nurseprofessionspeciality.entity.NurseProfessionSpeciality;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class NurseProfessionSpecialitySpecification implements Specification<NurseProfessionSpeciality> {

    private transient NurseProfessionSpecialitySearchDto nurseProfessionSpecialitySearchDto;


    @Override
    public Predicate toPredicate(Root<NurseProfessionSpeciality> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        if (nurseProfessionSpecialitySearchDto.getId() != null) {
            predicates.add(criteriaBuilder.equal(root.get("id"), nurseProfessionSpecialitySearchDto.getId()));
        }
        if (nurseProfessionSpecialitySearchDto.getName() != null) {
            predicates.add(criteriaBuilder.equal(root.get("name"), nurseProfessionSpecialitySearchDto.getName()));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
