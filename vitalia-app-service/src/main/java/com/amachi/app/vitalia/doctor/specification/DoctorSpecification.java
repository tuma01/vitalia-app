package com.amachi.app.vitalia.doctor.specification;

import com.amachi.app.vitalia.doctor.dto.search.DoctorSearchDto;
import com.amachi.app.vitalia.doctor.entity.Doctor;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class DoctorSpecification implements Specification<Doctor> {

    private transient DoctorSearchDto doctorSearchDto;

    @Override
    public Predicate toPredicate(Root<Doctor> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        List<Predicate> predicates = new ArrayList<>();

        if (doctorSearchDto.getNumeroId() != null) {
            predicates.add(criteriaBuilder.equal(root.get("numeroId"), doctorSearchDto.getNumeroId()));
        }
        if (doctorSearchDto.getNombre() != null) {
            predicates.add(criteriaBuilder.equal(root.get("nombre"), doctorSearchDto.getNombre()));
        }

        if (doctorSearchDto.getSegundoNombre() != null) {
            predicates.add(criteriaBuilder.equal(root.get("segundoNombre"), doctorSearchDto.getSegundoNombre()));
        }

        if (doctorSearchDto.getApellidoPaterno() != null) {
            predicates.add(criteriaBuilder.equal(root.get("apellidoPaterno"), doctorSearchDto.getApellidoPaterno()));
        }

        if (doctorSearchDto.getApellidoMaterno() != null) {
            predicates.add(criteriaBuilder.equal(root.get("apellidoMaterno"), doctorSearchDto.getApellidoMaterno()));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
