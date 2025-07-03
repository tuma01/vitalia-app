package com.amachi.app.vitalia.authentication.specification;


import com.amachi.app.vitalia.user.dto.search.UserSearchDto;
import com.amachi.app.vitalia.user.entity.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class UserSpecification implements Specification<User> {

    private transient UserSearchDto userSearchDto;

    @Override
    public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        List<Predicate> predicates = new ArrayList<>();

        if (userSearchDto.getId() != null) {
            predicates.add(criteriaBuilder.equal(root.get("id"), userSearchDto.getId()));
        }
        if (userSearchDto.getEmail() != null) {
            predicates.add(criteriaBuilder.equal(root.get("email"), userSearchDto.getEmail()));
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
