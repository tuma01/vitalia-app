package com.amachi.app.core.domain.repository;

import com.amachi.app.core.domain.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long>, JpaSpecificationExecutor<Person> {

    Optional<Person> findByNationalId(String nationalId); // nationalId = CI / Passport
    Optional<Person> findByNationalHealthId(String nationalHealthId);
}
