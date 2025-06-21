package com.amachi.app.vitalia.municipio.specification;

import com.amachi.app.vitalia.municipio.dto.search.MunicipioSearchDto;
import com.amachi.app.vitalia.municipio.entity.Municipio;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class MunicipioSpecification implements Specification<Municipio> {

    private transient MunicipioSearchDto municipioSearchDto;

    @Override
    public Predicate toPredicate(Root<Municipio> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        List<Predicate> predicates = new ArrayList<>();

        if (municipioSearchDto.getId() != null) {
            predicates.add(criteriaBuilder.equal(root.get("id"), municipioSearchDto.getId()));
        }

        if (municipioSearchDto.getNombre() != null) {
            predicates.add(criteriaBuilder.equal(root.get("nombre "), municipioSearchDto.getNombre()));
        }

        if (municipioSearchDto.getCodigoMunicipio() != null) {
            predicates.add(criteriaBuilder.equal(root.get("codigoMunicipio"), municipioSearchDto.getCodigoMunicipio()));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
