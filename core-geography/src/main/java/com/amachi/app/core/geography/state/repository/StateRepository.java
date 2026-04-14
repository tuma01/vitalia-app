package com.amachi.app.core.geography.state.repository;

import com.amachi.app.core.common.repository.CommonRepository;
import com.amachi.app.core.geography.state.entity.State;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StateRepository extends CommonRepository<State, Long> {
    Optional<State> findByName(String name);
}
