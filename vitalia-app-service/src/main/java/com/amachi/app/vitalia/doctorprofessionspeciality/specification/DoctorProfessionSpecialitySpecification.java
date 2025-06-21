package com.amachi.app.vitalia.doctorprofessionspeciality.specification;

import com.amachi.app.vitalia.doctorprofessionspeciality.dto.search.DoctorProfessionSpecialitySearchDto;
import com.amachi.app.vitalia.doctorprofessionspeciality.entity.DoctorProfessionSpeciality;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class DoctorProfessionSpecialitySpecification implements Specification<DoctorProfessionSpeciality> {
    private transient DoctorProfessionSpecialitySearchDto doctorProfessionSpecialitySearchDto;

    @Override
    public Predicate toPredicate(Root<DoctorProfessionSpeciality> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        List<Predicate> predicates = new ArrayList<>();

        if (doctorProfessionSpecialitySearchDto.getId() != null) {
            predicates.add(criteriaBuilder.equal(root.get("id"), doctorProfessionSpecialitySearchDto.getId()));
        }
        if (doctorProfessionSpecialitySearchDto.getName() != null) {
            predicates.add(criteriaBuilder.equal(root.get("name"), doctorProfessionSpecialitySearchDto.getName()));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
